package ussum.homepage.application.post.service;

import java.util.*;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ussum.homepage.application.comment.service.dto.response.PostOfficialCommentResponse;
import ussum.homepage.application.post.service.dto.request.GeneralPostCreateRequest;
import ussum.homepage.application.post.service.dto.request.PostCreateRequest;
import ussum.homepage.application.post.service.dto.request.PostFileDeleteRequest;
import ussum.homepage.application.post.service.dto.request.PostUpdateRequest;
import ussum.homepage.application.post.service.dto.response.FileResponse;
import ussum.homepage.application.post.service.dto.response.SimplePostResponse;
import ussum.homepage.application.post.service.dto.response.TopLikedPostListResponse;
import ussum.homepage.application.post.service.dto.response.postDetail.*;
import ussum.homepage.application.post.service.dto.response.postList.*;

import ussum.homepage.application.post.service.dto.response.postSave.*;

import ussum.homepage.application.user.service.dto.request.OnBoardingEmailRequest;
import ussum.homepage.application.user.service.dto.response.CollegeAndDepartmentResponse;
import ussum.homepage.application.user.service.dto.response.MyPostsResponse;
import ussum.homepage.domain.comment.PostComment;
import ussum.homepage.domain.comment.service.PostCommentReader;
import ussum.homepage.domain.comment.service.PostOfficialCommentFormatter;

import ussum.homepage.domain.group.service.GroupReader;
import ussum.homepage.domain.member.Member;
import ussum.homepage.domain.member.exception.MemberNotFoundException;
import ussum.homepage.domain.member.service.MemberReader;
import ussum.homepage.domain.post.Board;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.PostFile;
import ussum.homepage.domain.post.RightsDetail;
import ussum.homepage.domain.post.service.*;
import ussum.homepage.domain.post.service.factory.BoardFactory;
import ussum.homepage.domain.post.service.factory.BoardImpl;
import ussum.homepage.domain.post.service.factory.PostFactory;
import ussum.homepage.domain.post.service.factory.postList.*;
import ussum.homepage.domain.post.service.formatter.PostDetailFunction;
import ussum.homepage.domain.post.service.processor.PetitionPostProcessor;
import ussum.homepage.domain.postlike.service.PostReactionManager;
import ussum.homepage.domain.postlike.service.PostReactionReader;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.exception.OnBoardingMessagingException;
import ussum.homepage.domain.user.service.UserReader;
import ussum.homepage.global.common.PageInfo;
import ussum.homepage.global.error.exception.GeneralException;
import ussum.homepage.global.error.status.ErrorStatus;
import ussum.homepage.infra.jpa.group.entity.GroupCode;
import ussum.homepage.infra.jpa.member.entity.MajorCode;
import ussum.homepage.infra.jpa.member.entity.MemberCode;
import ussum.homepage.infra.jpa.post.PostMapper;
import ussum.homepage.infra.jpa.post.entity.*;
import ussum.homepage.infra.utils.S3utils;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static ussum.homepage.global.error.status.ErrorStatus.*;

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
    private final PostFactory postFactory;
    private final PostAdditionalAppender postAdditionalAppender;
    private final PostAdditionalReader postAdditionalReader;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String SENDER_EMAIL_ADDRESS;

    private final Map<String, PostDetailFunction<Post, Boolean, Boolean, User, Member, Integer, String, FileResponse, PostOfficialCommentResponse, RightsDetail,? extends PostDetailResDto>> postDetailResponseMap = Map.of(
            "공지사항게시판", (post, isAuthor, ignored, user, another_ignored1, another_ignored2, categoryName, fileResponseList, another_ignored3, another_ignored4) -> NoticePostDetailResponse.of(post, isAuthor, user, categoryName, fileResponseList),
            "자료집게시판", (post, isAuthor, ignored, user, another_ignored1, another_ignored2, categoryName, fileResponseList, another_ignored3, another_ignored4) -> DataPostDetailResponse.of(post, isAuthor, user, categoryName, fileResponseList),
            "분실물게시판", (post, isAuthor, ignored, user, another_ignored1, another_ignored2, categoryName, fileResponseList, another_ignored3, another_ignored4) -> LostPostDetailResponse.of(post, isAuthor, user, categoryName, fileResponseList),
            "제휴게시판", (post, isAuthor, ignored, user, another_ignored1, another_ignored2, categoryName, fileResponseList, another_ignored3, another_ignored4) -> PartnerPostDetailResponse.of(post, isAuthor, user, categoryName, fileResponseList),
            "감사기구게시판", (post, isAuthor, ignored, user, another_ignored1, another_ignored2, categoryName, fileResponseList, another_ignored3, another_ignored4) -> AuditPostDetailResponse.of(post, isAuthor, user, categoryName, fileResponseList),
            "청원게시판", (post, isAuthor, isLiked, user, ignored, likeCount, categoryName, fileResponseList, postOfficialCommentResponseList, another_ignored1) -> PetitionPostDetailResponse.of(post, isAuthor, isLiked, user, likeCount, categoryName, fileResponseList, postOfficialCommentResponseList),
            "건의게시판", (post, isAuthor, ignored, user, another_ignored1, another_ignored2, categoryName, fileResponseList, postOfficialCommentResponseList, another_ignored3) -> SuggestionPostDetailResponse.of(post, isAuthor, user, categoryName, fileResponseList, postOfficialCommentResponseList),
            "인권신고게시판", (post, isAuthor, ignored, user, another_ignored1, another_ignored2, categoryName, fileResponseList,postOfficialCommentResponseList,rightsDetailList) -> RightsPostDetailResponse.of(post,isAuthor,user,categoryName,fileResponseList,postOfficialCommentResponseList, rightsDetailList),
            "서비스공지사항", (post, isAuthor, ignored, user,  another_ignored1, another_ignored2, another_ignored3, fileResponseList, another_ignored4, another_ignored5) -> ServicePostDetailResponse.of(post,isAuthor,user,fileResponseList),
            "질의응답게시판", (post, isAuthor, ignored, user, member, another_ignored1, categoryName, another_ignored2, postOfficialCommentResponseList, another_ignored3) -> QnAPostDetailResponse.of(post, isAuthor, user, member, categoryName, postOfficialCommentResponseList)
    );
    private final PostMapper postMapper;

    public PostListRes<?> getPostList(Long userId, String boardCode, int page, int take, String groupCode, String memberCode, String category, String suggestionTarget, String qnaMajorCode, String qnaMemberCode) {
        Board board = boardReader.getBoardWithBoardCode(boardCode);

        //factory 사용 로직
        BoardImpl boardImpl = BoardFactory.createBoard(boardCode, board.getId());
        Pageable pageable = PageInfo.of(page, take);

        GroupCode groupCodeEnum = StringUtils.hasText(groupCode) ? GroupCode.getEnumGroupCodeFromStringGroupCode(groupCode) : null;
        MemberCode memberCodeEnum = StringUtils.hasText(memberCode) ? MemberCode.getEnumMemberCodeFromStringMemberCode(memberCode) : null;
        Category categoryEnum = StringUtils.hasText(category) ? Category.getEnumCategoryCodeFromStringCategoryCode(category) : null;
        SuggestionTarget suggestionTargetEnum = StringUtils.hasText(suggestionTarget) ? SuggestionTarget.fromString(suggestionTarget) : null;
        MajorCode qnaMajorCodeEnum = StringUtils.hasText(qnaMajorCode) ? MajorCode.getEnumMajorCodeFromStringMajorCode(qnaMajorCode) : null;
        MemberCode qnaMemberCodeEnum = StringUtils.hasText(qnaMemberCode) ? MemberCode.getEnumMemberCodeFromStringMemberCode(qnaMemberCode) : null;
        boolean unionUser = userId == null ? false :
                memberReader.getMembersWithUserId(userId).stream()
                        .map(Member::getGroupId)
                        .filter(groupId -> groupId != null)
                        .anyMatch(groupId -> groupId.equals(11L));
        Page<Post> postList = null;

        if ((board.getId() == 8 || board.getId() == 7) && !unionUser){
            postList = boardImpl.getPostListByUserId(postReader, groupCodeEnum, memberCodeEnum, categoryEnum, suggestionTargetEnum, userId, pageable);
        } else postList = boardImpl.getPostList(postReader, groupCodeEnum, memberCodeEnum, categoryEnum, suggestionTargetEnum, qnaMajorCodeEnum, qnaMemberCodeEnum, pageable);

        PageInfo pageInfo = PageInfo.of(postList);

        List<? extends PostListResDto> responseList = postList.getContent().stream()
                .map(post -> {
                    PostListResponseFactory factory = PostResponseFactoryProvider.getFactory(board.getName());
                    return factory.createResponse(post, postReader, postReactionReader, userReader, memberReader);
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

        PostListResponseFactory factory = PostResponseFactoryProvider.getFactory(BoardCode.DATA.getStringBoardCode());

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

        PostDetailFunction<Post, Boolean, Boolean, User, Member, Integer, String, FileResponse, PostOfficialCommentResponse, RightsDetail, ? extends PostDetailResDto> responseFunction = postDetailResponseMap.get(board.getName());

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
            response = responseFunction.apply(post, isAuthor, isLiked, user, null, likeCount, post.getCategory(), fileResponseList, postOfficialCommentResponses,null);
        }else if (board.getName().equals("인권신고게시판")){
            List<PostComment> officialPostComments = postCommentReader.getCommentListWithPostIdAndCommentType(userId, postId, "OFFICIAL");
            List<PostOfficialCommentResponse> postOfficialCommentResponses = officialPostComments.stream()
                    .map(postOfficialComment -> postOfficialCommentFormatter.format(postOfficialComment, userId))
                    .toList();
            List<RightsDetail> rightsPostDetailResponseList = postAdditionalReader.getRightsDetailByPostId(post.getId());
            response = responseFunction.apply(post, isAuthor,null,user,null, null,post.getCategory(),fileResponseList,postOfficialCommentResponses,rightsPostDetailResponseList);
        } else if (board.getName().equals("건의게시판")){
            List<PostComment> officialPostComments = postCommentReader.getCommentListWithPostIdAndCommentType(userId, postId, "OFFICIAL");
            List<PostOfficialCommentResponse> postOfficialCommentResponses = officialPostComments.stream()
                    .map(postOfficialComment -> postOfficialCommentFormatter.format(postOfficialComment, userId))
                    .toList();
            response = responseFunction.apply(post, isAuthor, null, user,null, null, post.getCategory(), fileResponseList, postOfficialCommentResponses,null);
        } else if (board.getName().equals("질의응답게시판")) {
            List<PostComment> officialPostComments = postCommentReader.getCommentListWithPostIdAndCommentType(userId, postId, "OFFICIAL");
            List<PostOfficialCommentResponse> postOfficialCommentResponses = officialPostComments.stream()
                    .map(postOfficialComment -> postOfficialCommentFormatter.format(postOfficialComment, userId))
                    .toList();

            List<Member> members = memberReader.getMembersWithUserId(post.getUserId());
            Optional<Member> firstMember = members.stream().findFirst();
            if (firstMember.isEmpty()) {
                throw new MemberNotFoundException(MEMBER_NOT_FOUND);
            }
            response = responseFunction.apply(post, isAuthor, null,user, firstMember.get(),null, post.getCategory(), null, postOfficialCommentResponses, null);
        } else if (board.getName().equals("제휴게시판") || board.getName().equals("공지사항게시판") || board.getName().equals("감사기구게시판") || board.getName().equals("서비스공지사항"))  {
            response = responseFunction.apply(post, isAuthor, null, user,null, null, post.getCategory(), fileResponseList, null,null);
        } else if (board.getName().equals("분실물게시판")) {
            response = responseFunction.apply(post, isAuthor, null, user, null,null, post.getCategory(), fileResponseList, null,null); //분실물 게시판은 파일첨부 제외
        }

        return PostDetailRes.of(response);
    }

    @Transactional
    public PostCreateResponse createBoardPost(Long userId, String boardCode, PostCreateRequest postCreateRequest){
        Board board = boardReader.getBoardWithBoardCode(boardCode);
        PostCreateRequest converPostCreateRequest = postFactory.convert(boardCode,postCreateRequest);
        Post post = postAppender.createPost(converPostCreateRequest.toDomain(board, userId));
        postAdditionalAppender.createAdditional(converPostCreateRequest,post.getId());
        postFileAppender.updatePostIdForIds(converPostCreateRequest.getPostFileList(), post.getId(), "자료집아님");

        // TODO(inho): 임시로 유나님 계정으로 메일 보내게 함
        if (board.getName().equals("질의응답게시판")) {
            sendEmail("[질의응답게시판 질문] " +  postCreateRequest.getTitle() , "질문 대상: " + (post.getQnaMemberCode() == null ? post.getQnaMajorCode() : "") + "\n\n" + "본문: " + postCreateRequest.getContent());
        }
        return PostCreateResponse.of(post.getId(), boardCode);
    }

    @Transactional
    public PostCreateResponse createDataPost(Long userId, /*String fileCategory,*/ GeneralPostCreateRequest generalPostCreateRequest){
        Board board = boardReader.getBoardWithBoardCode(BoardCode.DATA.getStringBoardCode());
        Post post = postAppender.createPost(generalPostCreateRequest.toDomain(board.getId(), userId /*, Category.getEnumCategoryCodeFromStringCategoryCode(
                generalPostCreateRequest.getCategory())*/));
        postFileAppender.updatePostIdAndFileCategoryForIds(generalPostCreateRequest.getPostFileList(), post.getId(), generalPostCreateRequest.getCategory());
        return PostCreateResponse.of(post.getId(), BoardCode.DATA.getStringBoardCode());
    }

    //이거 두개 api 합쳐야댐!!!!
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
    @Transactional
    public PostFileListResponse createBoardDataPostFile(Long userId, MultipartFile[] files, String fileType){
        PostFileMediatorResponse response = s3utils.uploadDataFileWithPath(userId, BoardCode.DATA.getStringBoardCode(), files, fileType);
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
        Optional.ofNullable(postUpdateRequest.rightsDetailList())
                .ifPresent(rightsDetails -> {
                    List<RightsDetail> domainRightsDetails = rightsDetails.stream()
                            .map(request -> request.toDomain(postId))
                            .collect(Collectors.toList());
                    postAdditionalAppender.modifyAdditionalList(postId,domainRightsDetails);
                });
        postFileAppender.updatePostIdForIds(postUpdateRequest.postFileList(), newPost.getId(), "자료집아님");
        return post.getId();
    }

    @Transactional
    public Long editBoardDatePost(Long postId, PostUpdateRequest postUpdateRequest){
        Post post = postReader.getPostWithId(postId);
        Post newPost = postModifier.updatePost(postUpdateRequest.toDataDomain(post));
        postFileAppender.updatePostIdForIds(postUpdateRequest.postFileList(), newPost.getId(), postUpdateRequest.fileCategory());
        return post.getId();
    }

    @Transactional
    public void deletePost(String boardCode, Long postId, PostFileDeleteRequest postFileDeleteRequest) {
        s3utils.deleteFiles(postFileDeleteRequest);
        postModifier.deletePost(boardCode, postId);
    }

    public PostListRes<?> searchPost(Long userId, int page, int take, String q, String boardCode, String groupCode, String memberCode, String qnaMajorCode, String qnaMemberCode, String category) {
        Board board = boardReader.getBoardWithBoardCode(boardCode);

        //factory 사용 로직
        BoardImpl boardImpl = BoardFactory.createBoard(boardCode, board.getId());
        Pageable pageable = PageInfo.of(page, take);

        GroupCode groupCodeEnum = StringUtils.hasText(groupCode) ? GroupCode.getEnumGroupCodeFromStringGroupCode(groupCode) : null;
        MemberCode memberCodeEnum = StringUtils.hasText(memberCode) ? MemberCode.getEnumMemberCodeFromStringMemberCode(memberCode) : null;
        Category categoryEnum = StringUtils.hasText(category) ? Category.getEnumCategoryCodeFromStringCategoryCode(category) : null;
        MajorCode qnaMajorCodeEnum =  StringUtils.hasText(qnaMajorCode) ? MajorCode.getEnumMajorCodeFromStringMajorCode(qnaMajorCode) : null;
        MemberCode qnaMemberCodeEnum =  StringUtils.hasText(qnaMemberCode) ? MemberCode.getEnumMemberCodeFromStringMemberCode(qnaMemberCode) : null;
        boolean rightsUnion = userId == null ? false :
                memberReader.getMembersWithUserId(userId).stream()
                        .map(Member::getGroupId)
                        .filter(groupId -> groupId != null)
                        .anyMatch(groupId -> groupId.equals(9L)||groupId.equals(3L));

        Page<Post> postList = null;

        if (board.getId() == 8  && !rightsUnion){
            postList = boardImpl.searchPostListByUserId(q,postReader,groupCodeEnum,memberCodeEnum,categoryEnum,userId,pageable);
        }else postList = boardImpl.searchPostList(q, postReader, groupCodeEnum, memberCodeEnum, categoryEnum, qnaMajorCodeEnum, qnaMemberCodeEnum, pageable);

        PageInfo pageInfo = PageInfo.of(postList);

        List<? extends PostListResDto> responseList = postList.getContent().stream()
                .map(post -> {
                    PostListResponseFactory factory = PostResponseFactoryProvider.getFactory(board.getName());
                    return factory.createResponse(post, postReader, postReactionReader, userReader, memberReader);
                })
                .toList();

        return PostListRes.of(responseList, pageInfo);

    }

    public PostListRes<?> searchMyPost(Long userId, int page, int take, String q) {

        Pageable pageable = PageInfo.of(page, take);
        Page<Post> postList = postReader.searchMyPosts(userId, q, pageable);
        PageInfo pageInfo = PageInfo.of(postList);

        List<? extends PostListResDto> responseList = postList.getContent().stream()
                .map(post -> {
                    int commentCount = Math.toIntExact(postCommentReader.getCommentCountByPostId(post.getId()));
                    return MyPostResponse.of(post, commentCount);
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

    //자료집 게시판 단건 조회
    public PostDetailRes<?> getDataPost(Long userId, Long postId) {
        Board board = boardReader.getBoardWithBoardCode(BoardCode.DATA.getStringBoardCode());
        Post post = postReader.getPostWithBoardCodeAndPostId(BoardCode.DATA.getStringBoardCode(), postId);
        User user = userReader.getUserWithId(post.getUserId());

        Boolean isAuthor = (userId != null && userId.equals(post.getUserId()));

        List<PostFile> postFileList = postFileReader.getPostFileListByPostId(post.getId());
        List<FileResponse> fileResponseList = postFileList.stream().map(FileResponse::of).toList();

        PostDetailFunction<Post, Boolean, Boolean, User, Member, Integer, String, FileResponse, PostOfficialCommentResponse, RightsDetail, ? extends PostDetailResDto> responseFunction = postDetailResponseMap.get(board.getName());

        if (responseFunction == null) {
            throw new GeneralException(ErrorStatus.INVALID_BOARDCODE);
        }



        PostDetailResDto response = null;
        response = responseFunction.apply(post, isAuthor, null, user, null, null, post.getCategory(), fileResponseList, null,null); //분실물 게시판은 파일첨부 제외

        return PostDetailRes.of(response);
    }

    public PostListRes<MyPostResponse> getMyPostList(Long userId, int page, int take) {
        Pageable pageable = PageInfo.of(page, take);
        Page<Post> postList = postReader.getMyPosts(userId, pageable);
        PageInfo pageInfo = PageInfo.of(postList);

        List<MyPostResponse> list = postList.getContent().stream()
                .map(post -> {
                    int commentCount = Math.toIntExact(postCommentReader.getCommentCountByPostId(post.getId()));
                    return MyPostResponse.of(post, commentCount);
                })
                .toList();

        return PostListRes.of(list, pageInfo);
    }

    public CollegeAndDepartmentResponse getCollegeAndDepartment(Long userId) {
        List<String> colleges = List.of(
                "경영대학",
                "경제통상대학",
                "공과대학",
                "법과대학",
                "사회과학대학",
                "인문대학",
                "자연과학대학",
                "IT대학",
                "융합특성화자유전공학부"
        );

        List<String> departments = List.of(
                "경영학부",
                "벤처중소기업학과",
                "회계학과",
                "금융학부",
                "경제학과",
                "글로벌통상학과",
                "금융경제학과",
                "국제무역학과",
                "화학공학과",
                "신소재공학과",
                "전기공학부",
                "기계공학부",
                "산업정보시스템공학과",
                "건축학부",
                "법학과",
                "국제법무학과",
                "사회복지학부",
                "행정학부",
                "정치외교학과",
                "정보사회학과",
                "언론홍보학과",
                "평생교육학과",
                "기독교학과",
                "국어국문학과",
                "영어영문학과",
                "독어독문학과",
                "불어불문학과",
                "중어중문학과",
                "일어일문학과",
                "철학과",
                "사학과",
                "문예창작전공",
                "영화예술전공",
                "스포츠학부",
                "수학과",
                "물리학과",
                "화학과",
                "정보통계보험수리학과",
                "의생명시스템학부",
                "컴퓨터학부",
                "전자정보공학부",
                "글로벌미디어학부",
                "소프트웨어학부",
                "AI융합학부",
                "미디어경영학과",
                "융합특성화자유전공학부"
        );

        return CollegeAndDepartmentResponse.of(colleges, departments);
    }

    public void sendEmail(String subject, String content) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setTo(SENDER_EMAIL_ADDRESS);
            mimeMessageHelper.setFrom(SENDER_EMAIL_ADDRESS);
//            mimeMessageHelper.setReplyTo(onBoardingEmailRequest.email());
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException | OnBoardingMessagingException onBoardingMessagingException) {
            throw new OnBoardingMessagingException(POST_FAIL_MAIL_ERROR);
        }
    }
}