package ussum.homepage.application.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ussum.homepage.application.comment.service.PostCommentManageService;
import ussum.homepage.global.ApiResponse;

@RequiredArgsConstructor
@RequestMapping
@RestController
public class PostCommentManagerController {
    private final PostCommentManageService postCommentManageService;

    @GetMapping("/board/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<?>> getPostCommentList(@PathVariable Long postId,
                                                             @RequestParam(name = "type") String type) { //type은 인기순인지 최신순인지

        return ApiResponse.success(postCommentManageService.getCommentList(postId, type));
    }
}
