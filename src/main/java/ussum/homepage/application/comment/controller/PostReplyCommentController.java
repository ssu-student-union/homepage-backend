package ussum.homepage.application.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ussum.homepage.application.comment.service.PostReplyCommentService;
import ussum.homepage.application.comment.service.dto.request.PostReplyCommentCreateRequest;
import ussum.homepage.global.ApiResponse;
import ussum.homepage.global.config.auth.UserId;

@RequiredArgsConstructor
@RequestMapping
@RestController
public class PostReplyCommentController {
    private final PostReplyCommentService postReplyCommentService;

    @PostMapping("/board/posts/comments/{commentId}")
    public ResponseEntity<ApiResponse<?>> createPostReplyComment(@UserId Long userId,
//                                                                 @PathVariable(name = "postId") Long postId,
                                                                 @PathVariable(name = "commentId") Long commentId,
                                                                 @RequestBody PostReplyCommentCreateRequest postReplyCommentCreateRequest) {
        postReplyCommentService.createReplyComment(userId, commentId, postReplyCommentCreateRequest);
        return ApiResponse.success(null);
    }

}
