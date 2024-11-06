package ussum.homepage.application.post.controller;

import java.util.Comparator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class PostController {
    //Comparator
//    private final PostService postService;

//    @GetMapping("/{boardCode}/posts")
//    public ResponseEntity<ApiResponse<?>> getBoardPostsList(@RequestParam(value = "page", defaultValue = "0") int page,
//                                                            @RequestParam(value = "take") int take,
//                                                            @PathVariable(name = "boardCode") String boardCode) {
//
//        PostListResponse postList = postService.getPostList(PageRequest.of(page, take, Sort.by("id").descending()), boardCode);
//        return ApiResponse.success(postList);
//    }

//    @Operation(summary = "게시판 인기청원 조회 api", description = """
//            게시판 인기청원 조회 시 필요한 데이터를 조회하는 api 입니다.
//            요청으로 boardCode 그리고 qeury param 형식으로 page, take를 입력하시면 됩니다.
//            """)
//    @GetMapping("/{boardCode}/posts/top-liked")
//    public ResponseEntity<ApiResponse<?>> getTopLikedBoardPostList(@RequestParam(value = "page", defaultValue = "0") int page,
//                                                                   @RequestParam(value = "take") int take,
//                                                                   @PathVariable(name = "boardCode") String boardCode) {
//
//        TopLikedPostListResponse postList = postService.getTopLikedPostList(page, take, boardCode);
//        return ApiResponse.success(postList);
//    }

//    @GetMapping("/{boardCode}/posts/{postId}")
//    public ResponseEntity<ApiResponse<?>> getBoardPost(@PathVariable(name = "boardCode") String boardCode,
//                                                       @PathVariable(name = "postId") Long postId) {
//
//        PostResponse post = postService.getPost(boardCode, postId);
//        return ApiResponse.success(post);
//    }
//
//    @PostMapping("/{boardCode}/posts")
//    public ResponseEntity<ApiResponse<?>> createBoardPost(@UserId Long userId,
//                                                          @PathVariable(name = "boardCode") String boardCode,
//                                                          @RequestBody GeneralPostCreateRequest postCreateRequest) {
//        postService.createPost(userId, boardCode,postCreateRequest);
//        return ApiResponse.success(null);
//    }

//    @PatchMapping("/{boardCode}/posts/{postId}")
//    public ResponseEntity<ApiResponse<?>> editBoardPost(@PathVariable(name = "boardCode") String boardCode,
//                                                        @PathVariable(name = "postId") Long postId,
//                                                        @RequestBody PostUpdateRequest postUpdateRequest) {
//        PostResponse post = postService.editPost(boardCode,postId, postUpdateRequest);
//        return ApiResponse.success(post);
//    }
//
//    @Operation(summary = "게시물 삭제 api", description = """
//            게시물을 삭제하는 api 입니다.
//            """)
//    @DeleteMapping("/{boardCode}/posts/{postId}")
//    public ResponseEntity<ApiResponse<?>> deleteBoardPost(@Parameter(hidden = true) @UserId Long userId,
//                                                          @PathVariable(name = "boardCode") String boardCode,
//                                                          @PathVariable(name = "postId") Long postId) {
//        postService.deletePost(boardCode, postId);
//        return ApiResponse.success(null);
//    }

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
