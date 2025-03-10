//package ussum.homepage.application.post.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import ussum.homepage.application.post.service.dto.request.GeneralPostCreateRequest;
//import ussum.homepage.application.post.service.dto.request.PostUpdateRequest;
//import ussum.homepage.application.post.service.dto.response.*;
//import ussum.homepage.domain.post.Board;
//import ussum.homepage.domain.post.Post;
//import ussum.homepage.domain.post.service.*;
//import ussum.homepage.domain.user.User;
//import ussum.homepage.domain.user.service.UserReader;
//import ussum.homepage.global.common.PageInfo;
//import ussum.homepage.infra.jpa.post.PostMapper;
//import ussum.homepage.infra.jpa.post.dto.SimplePostDto;
//import ussum.homepage.infra.jpa.post.entity.CalendarCategory;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
////@Transactional(readOnly = true)
//@Transactional
//public class PostService {
//    private final BoardReader boardReader;
//    private final UserReader userReader;
//    private final PostReader postReader;
//    private final PostAppender postAppender;
//    private final PostModifier postModifier;
//    private final PostMapper postMapper;
//
//
////    public PostListResponse getPostList(Pageable pageable, String boardCode) {
//////        Board board = boardReader.getBoardWithBoardCode(boardCode);
////        Page<Post> postList = postReader.getPostList(pageable, boardCode);
////        return PostListResponse.of(postList.getContent(), (int) postList.getTotalElements(), postList.getNumberOfElements(), postFormatter::format);
////    }
//
//    public TopLikedPostListResponse getTopLikedPostList(int page, int take, String boardCode){
//        Pageable pageable = PageInfo.of(page,take);
//        Page<SimplePostResponse> simplePostDtoList = postReader.findSimplePostDtoListByBoardCode(boardCode, pageable);
//        PageInfo pageInfo = PageInfo.of(simplePostDtoList);
//
//        return TopLikedPostListResponse.of(simplePostDtoList.getContent(), pageInfo);
//    }
//
////    public PostResponse getPost(String boardCode, Long postId) {
////        return postFormatter.format(
////                postReader.getPostWithBoardCodeAndPostId(boardCode, postId).getId()
////        );
////    }
//
//    public void createPost(Long userId, String boardCode, GeneralPostCreateRequest postCreateRequest) {
//        Board board = boardReader.getBoardWithBoardCode(boardCode);
//
//        postAppender.createPost(postCreateRequest.toDomain(board, userId, CalendarCategory.getEnumCategoryCodeFromStringCategoryCode(postCreateRequest.categoryCode())));
//    }
//
////    public PostResponse editPost(String boardCode,Long postId, PostUpdateRequest postUpdateRequest) {
////        return postFormatter.format(
////                postModifier.updatePost(boardCode, postId, postUpdateRequest).getId()
////        );
////    }
//
//    public void deletePost(String boardCode,Long postId){
//        postModifier.deletePost(boardCode, postId);
//    }
//
////    public PostListResponse searchPost(Pageable pageable, String boardCode, String q, String categoryCode) {
////        Page<Post> searchPost = postReader.getPostListBySearch(pageable, boardCode, q, categoryCode);
////        return PostListResponse.of(searchPost.getContent(), (int) searchPost.getTotalElements(), searchPost.getNumberOfElements(), postFormatter::format);
////    }
//
//    private List<SimplePostResponse> createSimplePostResponse(List<SimplePostResponse> simplePostDtoList){
//        return simplePostDtoList.stream().toList();
//    }
//}
//
