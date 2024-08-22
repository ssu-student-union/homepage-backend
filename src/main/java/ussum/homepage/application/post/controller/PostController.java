package ussum.homepage.application.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ussum.homepage.application.post.service.PostService;
import ussum.homepage.application.post.service.dto.request.PostCreateRequest;
import ussum.homepage.application.post.service.dto.request.PostUpdateRequest;
import ussum.homepage.application.post.service.dto.response.TopLikedPostListResponse;
import ussum.homepage.global.ApiResponse;
import ussum.homepage.global.config.auth.UserId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class PostController {
    private final PostService postService;

//    @GetMapping("/{boardCode}/posts")
//    public ResponseEntity<ApiResponse<?>> getBoardPostsList(@RequestParam(value = "page", defaultValue = "0") int page,
//                                                            @RequestParam(value = "take") int take,
//                                                           @PathVariable(name = "boardCode") String boardCode) {
//
//        PostListResponse postList = postService.getPostList(PageRequest.of(page, take, Sort.by("id").descending()), boardCode);
//        return ApiResponse.success(postList);
//    }

    @Operation(summary = "게시판 인기청원 조회 api", description = """
            게시판 인기청원 조회 시 필요한 데이터를 조회하는 api 입니다.
            요청으로 boardCode 그리고 qeury param 형식으로 page, take를 입력하시면 됩니다.
            """)
    @GetMapping("/{boardCode}/posts/top-liked")
    public ResponseEntity<ApiResponse<?>> getTopLikedBoardPostList(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                   @RequestParam(value = "take") int take,
                                                                   @PathVariable(name = "boardCode") String boardCode) {

        TopLikedPostListResponse postList = postService.getTopLikedPostList(page, take, boardCode);
        return ApiResponse.success(postList);
    }

//    @GetMapping("/{boardCode}/posts/{postId}")
//    public ResponseEntity<ApiResponse<?>> getBoardPost(@PathVariable(name = "boardCode") String boardCode,
//                                                       @PathVariable(name = "postId") Long postId) {
//
//        PostResponse post = postService.getPost(boardCode, postId);
//        return ApiResponse.success(post);
//    }

    @PostMapping("/{boardCode}/posts")
    public ResponseEntity<ApiResponse<?>> createBoardPost(@UserId Long userId,
                                                          @PathVariable(name = "boardCode") String boardCode,
                                                          @RequestBody PostCreateRequest postCreateRequest) {
        postService.createPost(userId, boardCode,postCreateRequest);
        return ApiResponse.success(null);
    }

//    @PatchMapping("/{boardCode}/posts/{postId}")
//    public ResponseEntity<ApiResponse<?>> editBoardPost(@PathVariable(name = "boardCode") String boardCode,
//                                                        @PathVariable(name = "postId") Long postId,
//                                                        @RequestBody PostUpdateRequest postUpdateRequest) {
//        PostResponse post = postService.editPost(boardCode,postId, postUpdateRequest);
//        return ApiResponse.success(post);
//    }

    @DeleteMapping("/{boardCode}/posts/{postId}")
    public ResponseEntity<ApiResponse<?>> deleteBoardPost(@PathVariable(name = "boardCode") String boardCode,
                                                          @PathVariable(name = "postId") Long postId) {
        postService.deletePost(boardCode, postId);
        return ApiResponse.success(null);
    }

//    @GetMapping("/{boardCode}/posts/search")
//    public ResponseEntity<ApiResponse<?>> searchBoardPost(@RequestParam(value = "q") String q,
//                                                          @RequestParam(value = "categoryCode") String categoryCode,
//                                                          @RequestParam(value = "page", defaultValue = "0") int page,
//                                                          @RequestParam(value = "take") int take,
//                                                          @PathVariable String boardCode) {
//
//        PostListResponse postList = postService.searchPost(PageRequest.of(page, take, Sort.by("id").descending()), boardCode, q, categoryCode);
//        return ApiResponse.success(postList);
//    }


}
