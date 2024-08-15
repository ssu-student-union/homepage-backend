package ussum.homepage.application.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ussum.homepage.application.post.service.PostManageService;
import ussum.homepage.application.post.service.dto.request.PostCreateRequest;
import ussum.homepage.application.post.service.dto.request.PostUpdateRequest;
import ussum.homepage.application.post.service.dto.request.PostUserRequest;
import ussum.homepage.global.ApiResponse;
import ussum.homepage.global.config.auth.UserId;

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
                                                       @PathVariable(name = "postId") Long postId,
                                                       @ModelAttribute PostUserRequest postUserRequest) {

        return ApiResponse.success(postManageService.getPost(postUserRequest, boardCode, postId));
    }

    @PostMapping("/{boardCode}/posts")
    public ResponseEntity<ApiResponse<?>> createBoardPost(@UserId Long userId,
                                                          @PathVariable(name = "boardCode") String boardCode,
                                                          @RequestBody PostCreateRequest postCreateRequest){
        return ApiResponse.success(postManageService.createBoardPost(userId, boardCode, postCreateRequest));
    }

    @PostMapping("/{boardCode}/posts/files")
    public ResponseEntity<ApiResponse<?>> createBoardPostFile(@UserId Long userId,
                                                              @PathVariable(name = "boardCode") String boardCode,
                                                              @RequestPart(value = "files") MultipartFile[] files,
                                                              @RequestParam(value = "type") String typeName) {
        return ApiResponse.success(postManageService.createBoardPostFile(userId, boardCode, files, typeName));
    }

    @PatchMapping("/{boardCode}/posts/{postId}")
    public ResponseEntity<ApiResponse<?>> editBoardPost(@PathVariable(name = "boardCode") String boardCode,
                                                        @PathVariable(name = "postId") Long postId,
                                                        @RequestBody PostUpdateRequest postUpdateRequest) {
        return ApiResponse.success(postManageService.editBoardPost(boardCode, postId, postUpdateRequest));
    }

    @PostMapping("/userIdTest")
    public ResponseEntity<ApiResponse<?>> apiTest(@UserId Long userId) {
        System.out.println("userId = " + userId);;
        return null;
    }

}
