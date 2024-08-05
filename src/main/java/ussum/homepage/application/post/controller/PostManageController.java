package ussum.homepage.application.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ussum.homepage.application.post.service.PostManageService;
import ussum.homepage.application.post.service.PostService;
import ussum.homepage.application.post.service.dto.response.PostListResponse;
import ussum.homepage.global.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class PostManageController {

    private final PostManageService postManageService;

    @GetMapping("/{boardCode}/posts")
    public ResponseEntity<ApiResponse<?>> getBoardPostsList(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "take") int take,
                                                            @PathVariable(name = "boardCode") String boardCode) {

//        PostListResponse postList = postService.getPostList(PageRequest.of(page, take, Sort.by("id").descending()), boardCode);
        return ApiResponse.success(postManageService.getPostList(page, take, boardCode));
    }

    @GetMapping("/{boardCode}/posts/{postId}")
    public ResponseEntity<ApiResponse<?>> getBoardPost(@PathVariable(name = "boardCode") String boardCode,
                                                       @PathVariable(name = "postId") Long postId) {

        return ApiResponse.success(postManageService.getPost(boardCode, postId));
    }

}
