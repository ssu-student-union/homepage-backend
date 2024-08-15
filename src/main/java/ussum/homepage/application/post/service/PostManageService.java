package ussum.homepage.application.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ussum.homepage.application.post.service.dto.request.PostCreateRequest;
import ussum.homepage.application.post.service.dto.request.PostUpdateRequest;
import ussum.homepage.application.post.service.dto.request.PostUserRequest;
import ussum.homepage.application.post.service.dto.response.postDetail.*;
import ussum.homepage.application.post.service.dto.response.postList.*;
import ussum.homepage.application.post.service.dto.response.postSave.PostCreateResponse;
import ussum.homepage.application.post.service.dto.response.postSave.PostFileResponse;
import ussum.homepage.domain.post.Board;
import ussum.homepage.domain.post.Category;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.PostFile;
import ussum.homepage.domain.post.service.*;
import ussum.homepage.domain.post.service.formatter.PostDetailFunction;
import ussum.homepage.domain.postlike.service.PostReactionReader;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.service.UserReader;
import ussum.homepage.global.common.PageInfo;
import ussum.homepage.global.error.exception.GeneralException;
import ussum.homepage.global.error.status.ErrorStatus;
import ussum.homepage.infra.jpa.post.entity.OngoingStatus;
import ussum.homepage.infra.utils.S3utils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostManageService {
    private final BoardReader boardReader;
    private final PostReader postReader;
    private final PostReactionReader postReactionReader;
    private final CategoryReader categoryReader;
    private final UserReader userReader;
    private final PostFileReader postFileReader;
    private final PostAppender postAppender;
    private final PostFileAppender postFileAppender;
    private final PostModifier postModifier;
    private final PostStatusProcessor postStatusProcessor;
    private final S3utils s3utils;

    private final Map<String, BiFunction<Post, Integer, ? extends PostListResDto>> postResponseMap = Map.of(
            "공지사항게시판", (post, ignored) -> NoticePostResponse.of(post),
            "분실물게시판", (post, ignored) -> LostPostResponse.of(post),
            "제휴게시판", (post, ignored) -> PartnerPostResponse.of(post),
            "감사기구게시판", (post, ignored) -> AuditPostResponseDto.of(post),
            "청원게시판", PetitionPostResponse::of
    );

    private final Map<String, PostDetailFunction<Post, Boolean, String, Integer, String, String, String, ? extends PostDetailResDto>> postDetailResponseMap = Map.of(
            "공지사항게시판", (post, isAuthor, authorName, ignored, categoryName, imageList, fileList) -> NoticePostDetailResponse.of(post, isAuthor, authorName, categoryName, imageList, fileList),
            "분실물게시판", (post, isAuthor, authorName, ignored, categoryName, imageList, another_ignored) -> LostPostDetailResponse.of(post, isAuthor, authorName, categoryName, imageList),
            "제휴게시판", (post, isAuthor, authorName, ignored, categoryName, imageList, fileList) -> PartnerPostDetailResponse.of(post, isAuthor, authorName, categoryName, imageList, fileList),
            "감사기구게시판", (post, isAuthor, authorName, ignored, categoryName, imageList, fileList) -> AuditPostDetailResponse.of(post, isAuthor, authorName, categoryName, imageList, fileList),
            "청원게시판", (post, isAuthor, authorName, likeCount, onGoingStatus, imageList, ignored) -> PetitionPostDetailResponse.of(post, isAuthor, authorName, likeCount, onGoingStatus, imageList)
    );


    public PostListRes<?> getPostList(int page, int take, String boardCode) {
        Board board = boardReader.getBoardWithBoardCode(boardCode);
        Pageable pageable = PageInfo.of(page, take);
        Page<Post> postList = postReader.getPostListByBoardId(board.getId(), pageable);
        PageInfo pageInfo = PageInfo.of(postList);

        BiFunction<Post, Integer, ? extends PostListResDto> responseFunction = postResponseMap.get(board.getName());

        if (responseFunction == null) {
            throw new IllegalArgumentException("Unknown board type: " + board.getName());
        }

        List<? extends PostListResDto> responseList = postList.getContent().stream()
                .map(post -> {
                    if (board.getName().equals("청원게시판")) {
                        Integer likeCount = postReactionReader.countPostReactionsByType(post.getId(), "like");
                        return responseFunction.apply(post, likeCount);
                    } else {
                        return responseFunction.apply(post, null);
                    }
                })
                .toList();

        return PostListRes.of(responseList, pageInfo);
    }


    @Transactional
    public PostDetailRes<?> getPost(PostUserRequest postUserRequest, String boardCode, Long postId) {
        Board board = boardReader.getBoardWithBoardCode(boardCode);
        Post post = postReader.getPostWithBoardCodeAndPostId(boardCode, postId);
        Category category = categoryReader.getCategoryById(post.getCategoryId());
        User user = userReader.getUserWithId(post.getUserId());

        Long userId = (postUserRequest != null) ? postUserRequest.userId() : null;
        Boolean isAuthor = (userId != null && userId.equals(post.getUserId()));

        List<PostFile> postFileList = postFileReader.getPostFileListByPostId(post.getId());
        List<String> imageList = postFileReader.getPostImageListByFileType(postFileList);
        List<String> fileList = postFileReader.getPostFileListByFileType(postFileList);


        PostDetailFunction<Post, Boolean, String, Integer, String, String, String, ? extends PostDetailResDto> responseFunction = postDetailResponseMap.get(board.getName());

        if (responseFunction == null) {
            throw new GeneralException(ErrorStatus.INVALID_BOARDCODE);
        }

        PostDetailResDto response = null;
        if (board.getName().equals("청원게시판")) {
            Integer likeCount = postReactionReader.countPostReactionsByType(post.getId(), "like");
            String postOnGoingStatus = postStatusProcessor.processStatus(post);
            response = responseFunction.apply(post, isAuthor, user.getName(), likeCount, postOnGoingStatus, imageList, null);
        } else if (board.getName().equals("제휴게시판") || board.getName().equals("공지사항게시판") || board.getName().equals("감사기구게시판")) {
            response = responseFunction.apply(post, isAuthor, user.getName(), null, category.getName(), imageList, fileList);
        } else if (board.getName().equals("분실물게시판")) {
            response = responseFunction.apply(post, isAuthor, user.getName(), null, category.getName(), imageList, null); //분실물 게시판은 파일첨부 제외
        }

        return PostDetailRes.of(response);
    }

    @Transactional
    public PostCreateResponse createBoardPost(Long userId, String boardCode, PostCreateRequest postCreateRequest){
        Board board = boardReader.getBoardWithBoardCode(boardCode);
        Category category = categoryReader.getCategoryWithCode(postCreateRequest.categoryCode());
        User user = userReader.getUserWithId(userId);
        String onGoingStatus = Objects.equals(boardCode, "PETITION") ? "IN_PROGRESS" : null;

        Post post = postAppender.createPost(postCreateRequest.toDomain(board, user, category, onGoingStatus));
        postFileAppender.updatePostIdForIds(postCreateRequest.postFileList(), post.getId());
        return PostCreateResponse.of(post.getId(), boardCode);
    }

    @Transactional
    public List<PostFileResponse> createBoardPostFile(Long userId, String boardCode, MultipartFile[] files, String typeName){
        List<String> urlList = s3utils.uploadFileWithPath(userId, boardCode, files, typeName);
        List<PostFile> postFiles = convertUrlsToPostFiles(urlList, typeName);
        List<PostFile> afterSaveList = postFileAppender.saveAllPostFile(postFiles);

        return afterSaveList.stream()
                .map(postFile -> PostFileResponse.of(postFile.getId(), postFile.getUrl()))
                .collect(Collectors.toList());
    }

    private List<PostFile> convertUrlsToPostFiles(List<String> urlList, String typeName) {
        return urlList.stream()
                .map(url -> PostFile.of(null, typeName, url, null, null))
                .collect(Collectors.toList());
    }

    @Transactional
    public Long editBoardPost(String boardCode, Long postId, PostUpdateRequest postUpdateRequest){
        Post post = postModifier.updatePost(boardCode, postId, postUpdateRequest);
        return post.getId();
    }
}
//스위치 사용 로직
/*
package ussum.homepage.application.post.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ussum.homepage.application.post.service.dto.response.postList.*;
import ussum.homepage.domain.post.Board;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.service.BoardReader;
import ussum.homepage.domain.post.service.PostReader;
import ussum.homepage.global.common.PageInfo;

import java.util.List;

import static ussum.homepage.global.error.status.ErrorStatus.POST_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PostManageService {

    private final BoardReader boardReader;
    private final PostReader postReader;

    public PostListRes<?> getPostList(int page, int take, String boardCode) {
        Board board = boardReader.getBoardWithBoardCode(boardCode);
        Pageable pageable = PageInfo.of(page, take);
        Page<Post> postList = postReader.getPostListByBoardId(board.getId(), pageable);
        PageInfo pageInfo = PageInfo.of(postList);

        switch (board.getName()) {
            case "공지사항":
                return getNoticePostList(postList, pageInfo);
            case "분실물":
                return getLostPostList(postList, pageInfo);
            case "제휴":
                return getPartnerPostList(postList, pageInfo);
            case "감사기구":
                return getAuditPostList(postList, pageInfo);
            case "청원":
                // return getPetitionPostList(postList, pageInfo);
            default:
                throw new EntityNotFoundException(String.valueOf(POST_NOT_FOUND));
        }
    }

    private PostListRes<NoticePostResponse> getNoticePostList(Page<Post> postList, PageInfo pageInfo) {
        List<NoticePostResponse> responseList = postList.getContent().stream()
                .map(NoticePostResponse::of)
                .toList();
        return PostListRes.of(responseList, pageInfo);
    }

    private PostListRes<LostPostResponse> getLostPostList(Page<Post> postList, PageInfo pageInfo) {
        List<LostPostResponse> responseList = postList.getContent().stream()
                .map(LostPostResponse::of)
                .toList();
        return PostListRes.of(responseList, pageInfo);
    }

    private PostListRes<PartnerPostResponse> getPartnerPostList(Page<Post> postList, PageInfo pageInfo) {
        List<PartnerPostResponse> responseList = postList.getContent().stream()
                .map(PartnerPostResponse::of)
                .toList();
        return PostListRes.of(responseList, pageInfo);
    }

    private PostListRes<AuditPostResponseDto> getAuditPostList(Page<Post> postList, PageInfo pageInfo) {
        List<AuditPostResponseDto> responseList = postList.getContent().stream()
                .map(AuditPostResponseDto::of)
                .toList();
        return PostListRes.of(responseList, pageInfo);
    }
}

 */