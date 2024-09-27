package ussum.homepage.application.post.service;

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
import ussum.homepage.application.post.service.dto.response.FileResponse;
import ussum.homepage.application.post.service.dto.response.SimplePostResponse;
import ussum.homepage.application.post.service.dto.response.TopLikedPostListResponse;
import ussum.homepage.application.post.service.dto.response.postDetail.*;
import ussum.homepage.application.post.service.dto.response.postList.*;

import ussum.homepage.application.post.service.dto.response.postSave.*;

import ussum.homepage.domain.comment.PostComment;
import ussum.homepage.domain.comment.service.PostCommentReader;
import ussum.homepage.domain.comment.service.PostOfficialCommentFormatter;

import ussum.homepage.domain.group.service.GroupReader;
import ussum.homepage.domain.member.service.MemberReader;
import ussum.homepage.domain.post.Board;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.PostFile;
import ussum.homepage.domain.post.service.*;
import ussum.homepage.domain.post.service.factory.BoardFactory;
import ussum.homepage.domain.post.service.factory.BoardImpl;
import ussum.homepage.domain.post.service.factory.postList.DataPostResponseFactory;
import ussum.homepage.domain.post.service.factory.postList.PostListResponseFactory;
import ussum.homepage.domain.post.service.factory.postList.PostResponseFactoryProvider;
import ussum.homepage.domain.post.service.formatter.PostDetailFunction;
import ussum.homepage.domain.postlike.service.PostReactionManager;
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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostManageService {
    private final BoardReader boardReader;
    private final PostReader postReader;
    private final PostReactionReader postReactionReader;
    private final PostReactionManager postReactionManager;
    private final UserReader userReader;
    private final MemberReader memberReader;
    private final PostCommentReader postCommentReader;
    private final PostFileReader postFileReader;
    private final PostAppender postAppender;
    private final PostFileAppender postFileAppender;
    private final PostModifier postModifier;
    private final GroupReader groupReader;
    private final PetitionPostProcessor petitionPostStatusProcessor;
    private final PostOfficialCommentFormatter postOfficialCommentFormatter;
    private final S3utils s3utils;


    private final Map<String, PostDetailFunction<Post, Boolean, Boolean, User, Integer, String, FileResponse, PostOfficialCommentResponse, ? extends PostDetailResDto>> postDetailResponseMap = Map.of(
            "공지사항게시판", (post, isAuthor, ignored, user, another_ignored1, categoryName, fileResponseList, another_ignored2) -> NoticePostDetailResponse.of(post, isAuthor, user, categoryName, fileResponseList),
            "분실물게시판", (post, isAuthor, ignored, user, another_ignored1, categoryName, fileResponseList, another_ignored3) -> LostPostDetailResponse.of(post, isAuthor, user, categoryName, fileResponseList),
            "제휴게시판", (post, isAuthor, ignored, user, another_ignored1, categoryName, fileResponseList, another_ignored2) -> PartnerPostDetailResponse.of(post, isAuthor, user, categoryName, fileResponseList),
            "감사기구게시판", (post, isAuthor, ignored, user, another_ignored1, categoryName, fileResponseList, another_ignored2) -> AuditPostDetailResponse.of(post, isAuthor, user, categoryName, fileResponseList),
            "청원게시판", (post, isAuthor, isLiked, user, likeCount, categoryName, fileResponseList, postOfficialCommentResponseList) -> PetitionPostDetailResponse.of(post, isAuthor, isLiked, user, likeCount, categoryName, fileResponseList, postOfficialCommentResponseList)
    );

    public PostListRes<?> getPostList(Long userId, String boardCode, int page, int take, String groupCode, String memberCode, String category) {
        Board board = boardReader.getBoardWithBoardCode(boardCode);

        //factory 사용 로직
        BoardImpl boardImpl = BoardFactory.createBoard(boardCode, board.getId());
        Pageable pageable = PageInfo.of(page, take);

        GroupCode groupCodeEnum = StringUtils.hasText(groupCode) ? GroupCode.getEnumGroupCodeFromStringGroupCode(groupCode) : null;
        MemberCode memberCodeEnum = StringUtils.hasText(memberCode) ? MemberCode.getEnumMemberCodeFromStringMemberCode(memberCode) : null;
        Category categoryEnum = StringUtils.hasText(category) ? Category.getEnumCategoryCodeFromStringCategoryCode(category) : null;

        Page<Post> postList = boardImpl.getPostList(postReader, groupCodeEnum, memberCodeEnum, categoryEnum, pageable);

        PageInfo pageInfo = PageInfo.of(postList);

        List<? extends PostListResDto> responseList = postList.getContent().stream()
                .map(post -> {
                    PostListResponseFactory factory = PostResponseFactoryProvider.getFactory(board.getName());
                    return factory.createResponse(post, postReader, postReactionReader, userReader);
                })
                .toList();

        return PostListRes.of(responseList, pageInfo);

    }

    public PostListRes<?> getDataList(Long userId, int page, int take, String majorCategory, String middleCategory, String subCategory) {
        Pageable pageable = PageInfo.of(page, take);
        Page<Post> postList = postReader.getPostListByFileCategories(
                FileCategory.getFileCategoriesByCategories(majorCategory, middleCategory, subCategory),
                pageable
        );
        PageInfo pageInfo = PageInfo.of(postList);

        PostListResponseFactory factory = PostResponseFactoryProvider.getFactory("자료집게시판");

        List<? extends PostListResDto> responseList = postList.getContent().stream()
                .map(post -> {
                    List<PostFile> postFiles = postFileReader.getPostFileListByPostId(post.getId());
                    return ((DataPostResponseFactory) factory).createDataResponse(post, postFiles);
                })
                .toList();

        return PostListRes.of(responseList, pageInfo);
    }

    public PostDetailRes<?> getPost(Long userId, String boardCode, Long postId) {
        Board board = boardReader.getBoardWithBoardCode(boardCode);
        Post post = postReader.getPostWithBoardCodeAndPostId(boardCode, postId);
        User user = userReader.getUserWithId(post.getUserId());

        Boolean isAuthor = (userId != null && userId.equals(post.getUserId()));

        List<PostFile> postFileList = postFileReader.getPostFileListByPostId(post.getId());
//        List<String> imageList = postFileReader.getPostImageListByFileType(postFileList);
//        List<String> fileList = postFileReader.getPostFileListByFileType(postFileList);
        List<FileResponse> fileResponseList = postFileList.stream().map(FileResponse::of).toList();

        PostDetailFunction<Post, Boolean, Boolean, User, Integer, String, FileResponse, PostOfficialCommentResponse, ? extends PostDetailResDto> responseFunction = postDetailResponseMap.get(board.getName());

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
            Boolean isLiked = (userId != null && postReactionManager.validatePostReactionByPostIdAndUserId(postId, userId, "like"));
            response = responseFunction.apply(post, isAuthor, isLiked, user, likeCount, post.getCategory(), fileResponseList, postOfficialCommentResponses);
        } else if (board.getName().equals("제휴게시판") || board.getName().equals("공지사항게시판") || board.getName().equals("감사기구게시판")) {
            response = responseFunction.apply(post, isAuthor, null, user, null, post.getCategory(), fileResponseList, null);
        } else if (board.getName().equals("분실물게시판")) {
            response = responseFunction.apply(post, isAuthor, null, user, null, post.getCategory(), fileResponseList, null); //분실물 게시판은 파일첨부 제외
        }

        return PostDetailRes.of(response);
    }

    @Transactional
    public PostCreateResponse createBoardPost(Long userId, String boardCode, PostCreateRequest postCreateRequest){
        Board board = boardReader.getBoardWithBoardCode(boardCode);
        Post post = postAppender.createPost(postCreateRequest.toDomain(board, userId));
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
        List<PostFile> postFiles = convertUrlsToPostFiles(response);
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

    private List<PostFile> convertUrlsToPostFiles(PostFileMediatorResponse response) {
        List<String> originalFileNames = response.originalFileNames();
        List<Map<String, String>> urlList = response.urlList();

        return IntStream.range(0, urlList.size())
                .mapToObj(i -> {
                    Map<String, String> urlMap = urlList.get(i);
                    String originalFileName = i < originalFileNames.size() ? originalFileNames.get(i) : null;

                    return urlMap.entrySet().stream()
                            .map(entry -> PostFile.of(
                                    null,
                                    originalFileName,
                                    entry.getKey(),
                                    null,
                                    entry.getValue(),
                                    null,
                                    null
                            ))
                            .collect(Collectors.toList());
                })
                .flatMap(List::stream)
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
        Post post = postReader.getPostWithId(postId);
        Board board = boardReader.getBoardWithBoardCode(boardCode);
        Post newPost = postModifier.updatePost(postUpdateRequest.toDomain(post, board));
        postFileAppender.updatePostIdForIds(postUpdateRequest.postFileList(), newPost.getId());
        return post.getId();
    }

    @Transactional
    public void deletePost(String boardCode, Long postId, PostFileDeleteRequest postFileDeleteRequest) {
        s3utils.deleteFiles(postFileDeleteRequest);
        postModifier.deletePost(boardCode, postId);
    }

    public PostListRes<?> searchPost(Long userId, int page, int take, String q, String boardCode, String groupCode, String memberCode, String category) {
        Board board = boardReader.getBoardWithBoardCode(boardCode);

        //factory 사용 로직
        BoardImpl boardImpl = BoardFactory.createBoard(boardCode, board.getId());
        Pageable pageable = PageInfo.of(page, take);

        GroupCode groupCodeEnum = StringUtils.hasText(groupCode) ? GroupCode.getEnumGroupCodeFromStringGroupCode(groupCode) : null;
        MemberCode memberCodeEnum = StringUtils.hasText(memberCode) ? MemberCode.getEnumMemberCodeFromStringMemberCode(memberCode) : null;
        Category categoryEnum = StringUtils.hasText(category) ? Category.getEnumCategoryCodeFromStringCategoryCode(category) : null;

        Page<Post> postList = boardImpl.searchPostList(q, postReader, groupCodeEnum, memberCodeEnum, categoryEnum, pageable);

        PageInfo pageInfo = PageInfo.of(postList);

        List<? extends PostListResDto> responseList = postList.getContent().stream()
                .map(post -> {
                    PostListResponseFactory factory = PostResponseFactoryProvider.getFactory(board.getName());
                    return factory.createResponse(post, postReader, postReactionReader, userReader);
                })
                .toList();

        return PostListRes.of(responseList, pageInfo);

    }

    public PostListRes<?> searchDataList(Long userId, int page, int take, String q, String majorCategory, String middleCategory, String subCategory) {
        Pageable pageable = PageInfo.of(page, take);
        Page<Post> postList = postReader.searchPostListByFileCategories(
                q,
                FileCategory.getFileCategoriesByCategories(majorCategory, middleCategory, subCategory),
                pageable
        );
        PageInfo pageInfo = PageInfo.of(postList);

        PostListResponseFactory factory = PostResponseFactoryProvider.getFactory("자료집게시판");

        List<? extends PostListResDto> responseList = postList.getContent().stream()
                .map(post -> {
                    List<PostFile> postFiles = postFileReader.getPostFileListByPostId(post.getId());
                    return ((DataPostResponseFactory) factory).createDataResponse(post, postFiles);
                })
                .toList();

        return PostListRes.of(responseList, pageInfo);
    }

    public TopLikedPostListResponse getTopLikedPostList(int page, int take, String boardCode) {
        Pageable pageable = PageInfo.of(page, take);
        Page<SimplePostResponse> simplePostDtoList = postReader.findSimplePostDtoListByBoardCode(boardCode, pageable);
        PageInfo pageInfo = PageInfo.of(simplePostDtoList);

        return TopLikedPostListResponse.of(simplePostDtoList.getContent(), pageInfo);
    }
}