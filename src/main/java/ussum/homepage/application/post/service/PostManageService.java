package ussum.homepage.application.post.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ussum.homepage.application.comment.service.dto.response.PostOfficialCommentResponse;
import ussum.homepage.application.post.service.dto.request.PostCreateRequest;
import ussum.homepage.application.post.service.dto.request.PostFileDeleteRequest;
import ussum.homepage.application.post.service.dto.request.PostUpdateRequest;
import ussum.homepage.application.post.service.dto.request.PostUserRequest;
import ussum.homepage.application.post.service.dto.response.DataPostResponse;
import ussum.homepage.application.post.service.dto.response.postDetail.*;
import ussum.homepage.application.post.service.dto.response.postList.*;

import ussum.homepage.application.post.service.dto.response.postSave.*;
import ussum.homepage.domain.comment.service.formatter.QuadFunction;

import ussum.homepage.domain.comment.PostComment;
import ussum.homepage.domain.comment.service.PostCommentReader;
import ussum.homepage.domain.comment.service.PostOfficialCommentFormatter;

import ussum.homepage.domain.member.Member;
import ussum.homepage.domain.member.service.MemberReader;
import ussum.homepage.domain.post.Board;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.PostFile;
import ussum.homepage.domain.post.service.*;
import ussum.homepage.domain.post.service.factory.BoardFactory;
import ussum.homepage.domain.post.service.factory.BoardImpl;
import ussum.homepage.domain.post.service.formatter.PostDetailFunction;
import ussum.homepage.domain.postlike.service.PostReactionReader;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.service.UserReader;
import ussum.homepage.global.common.PageInfo;
import ussum.homepage.global.error.exception.GeneralException;
import ussum.homepage.global.error.status.ErrorStatus;
import ussum.homepage.infra.jpa.group.entity.GroupCode;
import ussum.homepage.infra.jpa.member.entity.MemberCode;
import ussum.homepage.infra.jpa.post.entity.BoardCode;
import ussum.homepage.infra.jpa.post.entity.Category;
import ussum.homepage.infra.jpa.post.entity.FileCategory;
import ussum.homepage.infra.utils.S3utils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ussum.homepage.global.error.status.ErrorStatus.POST_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostManageService {
    private final BoardReader boardReader;
    private final PostReader postReader;
    private final PostReactionReader postReactionReader;
    private final UserReader userReader;
    private final MemberReader memberReader;
    private final PostCommentReader postCommentReader;
    private final PostFileReader postFileReader;
    private final PostAppender postAppender;
    private final PostFileAppender postFileAppender;
    private final PostModifier postModifier;
    private final PetitionPostProcessor petitionPostStatusProcessor;
    private final PostOfficialCommentFormatter postOfficialCommentFormatter;
    private final S3utils s3utils;

    private final Map<String, QuadFunction<Post, List<PostFile>, Integer, User, ? extends PostListResDto>> postResponseMap = Map.of(
            "공지사항게시판", (post, ignored1, ignored2, user) -> NoticePostResponse.of(post, user),
            "분실물게시판", (post, ignored1, ignored2, ignored3) -> LostPostResponse.of(post),
            "제휴게시판", (post, ignored1, ignored2, ignored3) -> PartnerPostResponse.of(post),
            "감사기구게시판", (post, ignored1, ignored2, ignored3) -> AuditPostResponseDto.of(post),
            "청원게시판", (post, ignored1, likeCount, ignored2) -> PetitionPostResponse.of(post, likeCount),
            "자료집게시판", (post, postFiles, ignored1, ignored2) -> DataPostResponse.of(post, postFiles)
    );

    private final Map<String, PostDetailFunction<Post, Boolean, User, Integer, String, String, String, PostOfficialCommentResponse, ? extends PostDetailResDto>> postDetailResponseMap = Map.of(
            "공지사항게시판", (post, isAuthor, user, ignored, categoryName, imageList, fileList, another_ignored) -> NoticePostDetailResponse.of(post, isAuthor, user, categoryName, imageList, fileList),
            "분실물게시판", (post, isAuthor, user, ignored, categoryName, imageList, another_ignored1, another_ignored2) -> LostPostDetailResponse.of(post, isAuthor, user, categoryName, imageList),
            "제휴게시판", (post, isAuthor, user, ignored, categoryName, imageList, fileList, another_ignored) -> PartnerPostDetailResponse.of(post, isAuthor, user, categoryName, imageList, fileList),
            "감사기구게시판", (post, isAuthor, user, ignored, categoryName, imageList, fileList, another_ignored) -> AuditPostDetailResponse.of(post, isAuthor, user, categoryName, imageList, fileList),
            "청원게시판", (post, isAuthor, user, likeCount, categoryName, imageList, ignored, postOfficialCommentResponseList) -> PetitionPostDetailResponse.of(post, isAuthor, user, likeCount, categoryName, imageList, postOfficialCommentResponseList)
    );

    public PostListRes<?> getPostList(int page, int take, String boardCode, String groupCode, String memberCode, String category) {
        Board board = boardReader.getBoardWithBoardCode(boardCode);
//        Pageable pageable = PageInfo.of(page, take);
//        Page<Post> postList = null;
//        if(boardCode.equals("공지사항게시판")){
//            postList = postReader.getPostListByBoardIdAndGroupCodeAndMemberCode(board.getId(), groupCode, memberCode, pageable);
//        }else {
//            postList = postReader.getPostListByBoardId(board.getId(), pageable);
//        }


        //factory 사용 로직
        BoardImpl boardImpl = BoardFactory.createBoard(boardCode, board.getId());
        Pageable pageable = PageInfo.of(page, take);

        GroupCode groupCodeEnum = StringUtils.hasText(groupCode) ? GroupCode.getEnumGroupCodeFromStringGroupCode(groupCode) : null;
        MemberCode memberCodeEnum = StringUtils.hasText(memberCode) ? MemberCode.getEnumMemberCodeFromStringMemberCode(memberCode) : null;
        Category categoryEnum = StringUtils.hasText(category) ? Category.getEnumCategoryCodeFromStringCategoryCode(category) : null;

        Page<Post> postList = boardImpl.getPostList(postReader, groupCodeEnum, memberCodeEnum, categoryEnum, pageable);

        PageInfo pageInfo = PageInfo.of(postList);

        QuadFunction<Post, List<PostFile>, Integer, User, ? extends PostListResDto> responseFunction = postResponseMap.get(board.getName());

        if (responseFunction == null) {
            throw new IllegalArgumentException("Unknown board type: " + board.getName());
        }

        List<? extends PostListResDto> responseList = postList.getContent().stream()
                .map(post -> {
                    User user = null;
                    Integer likeCount = null;
                    switch (board.getName()) {
                        case "공지사항게시판":
                            user = userReader.getUserWithId(post.getUserId());
                            return responseFunction.apply(post, null, null,user);
                        case "분실물게시판":
                        case "제휴게시판":
                        case "감사기구게시판":
                        case "자료집":
                            return responseFunction.apply(post, null, null, null);
                        case "청원게시판":
                            likeCount = postReactionReader.countPostReactionsByType(post.getId(), "like");
                            return responseFunction.apply(post, null, likeCount,null);
                        default:
                            throw new EntityNotFoundException(String.valueOf(POST_NOT_FOUND));
                    }
                })
                .toList();

        return PostListRes.of(responseList, pageInfo);
    }

    public PostListRes<?> getDataList(int page, int take, String majorCategory, String middleCategory, String subCategory){
        Pageable pageable = PageInfo.of(page, take);
        Page<Post> postList = postReader.getPostListByFileCategories(FileCategory.getFileCategoriesByCategories(majorCategory, middleCategory, subCategory), pageable);
        PageInfo pageInfo = PageInfo.of(postList);
        QuadFunction<Post, List<PostFile> , Integer, User, ? extends PostListResDto> responseFunction = postResponseMap.get("자료집게시판");
        List<? extends PostListResDto> responseList = postList.getContent().stream().map(post -> responseFunction.apply(post, postFileReader.getPostFileListByPostId(post.getId()), null, null)).toList();
        return PostListRes.of(responseList, pageInfo);
    }

    public PostDetailRes<?> getPost(Long userId, String boardCode, Long postId) {
        Board board = boardReader.getBoardWithBoardCode(boardCode);
        Post post = postReader.getPostWithBoardCodeAndPostId(boardCode, postId);
        User user = userReader.getUserWithId(post.getUserId());

        Boolean isAuthor = (userId != null && userId.equals(post.getUserId()));

        List<PostFile> postFileList = postFileReader.getPostFileListByPostId(post.getId());
        List<String> imageList = postFileReader.getPostImageListByFileType(postFileList);
        List<String> fileList = postFileReader.getPostFileListByFileType(postFileList);


        PostDetailFunction<Post, Boolean, User, Integer, String, String, String, PostOfficialCommentResponse, ? extends PostDetailResDto> responseFunction = postDetailResponseMap.get(board.getName());

        if (responseFunction == null) {
            throw new GeneralException(ErrorStatus.INVALID_BOARDCODE);
        }

        PostDetailResDto response = null;
        if (board.getName().equals("청원게시판")) {
            Integer likeCount = postReactionReader.countPostReactionsByType(post.getId(), "like");
            List<PostComment> officialPostComments = postCommentReader.getCommentListWithPostIdAndCommentType(userId, postId, "OFFICIAL");
            List<PostOfficialCommentResponse> postOfficialCommentResponses = officialPostComments.stream()
                    .map(postOfficialComment -> postOfficialCommentFormatter.format(postOfficialComment, userId))
                    .toList();
            response = responseFunction.apply(post, isAuthor, user, likeCount, post.getCategory(), imageList, null, postOfficialCommentResponses);
        } else if (board.getName().equals("제휴게시판") || board.getName().equals("공지사항게시판") || board.getName().equals("감사기구게시판")) {
            response = responseFunction.apply(post, isAuthor, user, null, post.getCategory(), imageList, fileList,null);
        } else if (board.getName().equals("분실물게시판")) {
            response = responseFunction.apply(post, isAuthor, user, null, post.getCategory(), imageList, null, null); //분실물 게시판은 파일첨부 제외
        }

        return PostDetailRes.of(response);
    }

    @Transactional
    public PostCreateResponse createBoardPost(Long userId, String boardCode, PostCreateRequest postCreateRequest){
        Board board = boardReader.getBoardWithBoardCode(boardCode);
        Member member = memberReader.getMemberWithUserId(userId);
        String noticeCategory = MemberCode.valueOf(member.getMemberCode()).getStringMemberCode();
        String category = Objects.equals(boardCode, BoardCode.NOTICE.getStringBoardCode()) ? noticeCategory : postCreateRequest.categoryCode();
        String onGoingStatus = Objects.equals(boardCode, BoardCode.PETITION.getStringBoardCode()) ? postCreateRequest.categoryCode() : postCreateRequest.isNotice() ? Category.EMERGENCY.getStringCategoryCode() : null;
        
//        Post post = postAppender.createPost(postCreateRequest.toDomain(board, userId, Category.getEnumCategoryCodeFromStringCategoryCode(postCreateRequest.categoryCode()), onGoingStatus));
        Post post = postAppender.createPost(postCreateRequest.toDomain(board, userId, Category.getEnumCategoryCodeFromStringCategoryCode(category)));
        postFileAppender.updatePostIdForIds(postCreateRequest.postFileList(), post.getId());
        return PostCreateResponse.of(post.getId(), boardCode);
    }

    @Transactional
    public PostCreateResponse createDataPost(Long userId, String fileCategory, String fileType, PostCreateRequest postCreateRequest){
        Board board = boardReader.getBoardWithBoardCode(BoardCode.DATA.getStringBoardCode());
        Post post = postAppender.createPost(postCreateRequest.toDomain(board.getId(), userId, Category.getEnumCategoryCodeFromStringCategoryCode(postCreateRequest.categoryCode())));
        postFileAppender.updatePostIdAndFileCategoryForIds(postCreateRequest.postFileList(), post.getId(), fileCategory, fileType);
        return PostCreateResponse.of(post.getId(), BoardCode.DATA.getStringBoardCode());
    }

    @Transactional
    public PostFileListResponse createBoardPostFile(Long userId, String boardCode, MultipartFile[] files, MultipartFile[] images){
        PostFileMediatorResponse response = s3utils.uploadFileWithPath(userId, boardCode, files, images);
        List<PostFile> postFiles = convertUrlsToPostFiles(response.urlList());
        List<PostFile> afterSaveList = postFileAppender.saveAllPostFile(postFiles);

        String thumbnailUrl = afterSaveList.stream()
                .filter(postFile -> postFile.getTypeName().equals("images"))
                .min(Comparator.comparing(PostFile::getId))
                .map(PostFile::getUrl)
                .orElse(null);

        AtomicInteger index = new AtomicInteger(0);
        List<PostFileResponse> postFileResponses = afterSaveList.stream()
                .map(postFile -> {
                    int currentIndex = index.getAndIncrement();
                    return PostFileResponse.of(postFile.getId(), postFile.getUrl(), response.originalFileNames().get(currentIndex));
                })
                .collect(Collectors.toList());

        return PostFileListResponse.of(thumbnailUrl, postFileResponses);
    }

    private List<PostFile> convertUrlsToPostFiles(List<Map<String, String>> urlList) {
        return urlList.stream()
                .flatMap(map -> map.entrySet().stream())
                .map(entry -> PostFile.of(null, entry.getKey(), null, entry.getValue(), null, null)) // key: 파일 타입, value: URL
                .collect(Collectors.toList());
    }

    @Transactional
    public PostFileDeleteResponse deleteBoardPostFile(Long userId, String boardCode, PostFileDeleteRequest request){
        int s3Count = s3utils.deleteFiles(request);
        Long postFileCount = postFileReader.getPostFileListByUrlAndDelete(request);
        return PostFileDeleteResponse.of(s3Count, postFileCount);
    }


    @Transactional
    public Long editBoardPost(String boardCode, Long postId, PostUpdateRequest postUpdateRequest){
        Post post = postModifier.updatePost(boardCode, postId, postUpdateRequest);
        return post.getId();
    }

    @Transactional
    public void deletePost(String boardCode, Long postId) {
        postModifier.deletePost(boardCode, postId);
    }

    public PostListRes<?> searchPost(int page, int take, String q, String boardCode, String groupCode, String memberCode, String category) {
        Board board = boardReader.getBoardWithBoardCode(boardCode);
        BoardImpl boardImpl = BoardFactory.createBoard(boardCode, board.getId());
        Pageable pageable = PageInfo.of(page, take);

        GroupCode groupCodeEnum = StringUtils.hasText(groupCode) ? GroupCode.getEnumGroupCodeFromStringGroupCode(groupCode) : null;
        MemberCode memberCodeEnum = StringUtils.hasText(memberCode) ? MemberCode.getEnumMemberCodeFromStringMemberCode(memberCode) : null;
        Category categoryEnum = StringUtils.hasText(category) ? Category.getEnumCategoryCodeFromStringCategoryCode(category) : null;

        Page<Post> postList = boardImpl.searchPostList(q, postReader, groupCodeEnum, memberCodeEnum, categoryEnum, pageable);

        PageInfo pageInfo = PageInfo.of(postList);

        QuadFunction<Post, List<PostFile>, Integer, User, ? extends PostListResDto> responseFunction = postResponseMap.get(board.getName());

        if (responseFunction == null) {
            throw new IllegalArgumentException("Unknown board type: " + board.getName());
        }

        List<? extends PostListResDto> responseList = postList.getContent().stream()
                .map(post -> {
                    User user = null;
                    Integer likeCount = null;
                    switch (board.getName()) {
                        case "공지사항게시판":
                            user = userReader.getUserWithId(post.getUserId());
                            return responseFunction.apply(post, null, null,user);
                        case "분실물게시판":
                        case "제휴게시판":
                        case "감사기구게시판":
                        case "자료집":
                            return responseFunction.apply(post, null, null, null);
                        case "청원게시판":
                            likeCount = postReactionReader.countPostReactionsByType(post.getId(), "like");
                            return responseFunction.apply(post, null, likeCount,null);
                        default:
                            throw new EntityNotFoundException(String.valueOf(POST_NOT_FOUND));
                    }
                })
                .toList();

        return PostListRes.of(responseList, pageInfo);












//
//        Page<Post> searchPost = postReader.getPostListBySearch(pageable, boardCode, q, categoryCode);
//        return PostListResponse.of(searchPost.getContent(), (int) searchPost.getTotalElements(), searchPost.getNumberOfElements(), postFormatter::format);
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