package ussum.homepage.application.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ussum.homepage.application.post.service.PostService;
import ussum.homepage.application.post.service.dto.request.PostCreateRequest;
import ussum.homepage.application.post.service.dto.request.PostUpdateRequest;
import ussum.homepage.application.post.service.dto.response.PostListResponse;
import ussum.homepage.application.post.service.dto.response.PostResponse;
import ussum.homepage.global.ApiResponse;
import ussum.homepage.global.config.auth.UserId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class PostController {
    private final PostService postService;

    @GetMapping("/{boardCode}/posts")
    public ApiResponse<PostListResponse> getBoardPostsList(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "take") int take,
                                                           @PathVariable(name = "boardCode") String boardCode) {

        PostListResponse postList = postService.getPostList(PageRequest.of(page, take, Sort.by("id").descending()), boardCode);
        return ApiResponse.onSuccess(postList);
    }

    @GetMapping("/{boardCode}/posts/{postId}")
    public ApiResponse<PostResponse> getBoardPost(@PathVariable(name = "boardCode") String boardCode,
                                                  @PathVariable(name = "postId") Long postId) {

        return ApiResponse.onSuccess(postService.getPost(boardCode, postId));
    }

    @PostMapping("/{boardCode}/posts")
    public ApiResponse<?> createBoardPost(@UserId Long userId, @PathVariable(name = "boardCode") String boardCode,
                                          @RequestBody PostCreateRequest postCreateRequest) {
        postService.createPost(userId, boardCode,postCreateRequest);
        return ApiResponse.onSuccess(null);
    }

    @PatchMapping("/{boardCode}/posts/{postId}")
    public ApiResponse<PostResponse> editBoardPost(@PathVariable(name = "boardCode") String boardCode, @PathVariable(name = "postId") Long postId,
                                                   @RequestBody PostUpdateRequest postUpdateRequest) {
        PostResponse post = postService.editPost(boardCode,postId, postUpdateRequest);
        return ApiResponse.onSuccess(post);
    }

    @DeleteMapping("/{boardCode}/posts/{postId}")
    public ApiResponse<?> deleteBoardPost(@PathVariable(name = "boardCode") String boardCode, @PathVariable(name = "postId") Long postId) {
        postService.deletePost(boardCode, postId);
        return ApiResponse.onSuccess(null);
    }

    @GetMapping("/{boardCode}/posts/search")
    public ApiResponse<PostListResponse> searchBoardPost(@RequestParam(value = "q") String q, @RequestParam(value = "categorycode") String categoryCode,
                                                    @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "take") int take,
                                                    @PathVariable String boardCode) {
        return ApiResponse.onSuccess(postService.searchPost(
                PageRequest.of(page, take, Sort.by("id").descending()), boardCode, q, categoryCode)
        );
    }


}
