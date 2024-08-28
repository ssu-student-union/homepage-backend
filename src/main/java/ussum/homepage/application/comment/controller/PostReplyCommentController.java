package ussum.homepage.application.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ussum.homepage.application.comment.service.PostReplyCommentService;
import ussum.homepage.application.comment.service.dto.request.PostReplyCommentCreateRequest;
import ussum.homepage.application.comment.service.dto.request.PostReplyCommentUpdateRequest;
import ussum.homepage.application.comment.service.dto.response.PostReplyCommentResponse;
import ussum.homepage.global.ApiResponse;
import ussum.homepage.global.config.auth.UserId;

@RequiredArgsConstructor
@RequestMapping("/board")
@RestController
@Tag(name = "post_reply_comment", description = "게시물 대댓글 생성, 수정, 삭제 api")
public class PostReplyCommentController {
    private final PostReplyCommentService postReplyCommentService;

    @Operation(summary = "게시물 대댓글 생성 api", description = """
            게시물 댓글을 생성하기 위한 api입니다.
            요청 url 상에 commentId를 받습니다. 즉, 댓글 id를 받습니다.
            PostReplyCommentCreateRequest 요청 json에 content 필드를 이용하여 댓글 내용을 작성합니다.
            """)
    @PostMapping("/posts/comments/{commentId}/reply-comments")
    public ResponseEntity<ApiResponse<?>> createPostReplyComment(@Parameter(hidden = true) @UserId Long userId,
                                                                 @PathVariable(name = "commentId") Long commentId,
                                                                 @RequestBody PostReplyCommentCreateRequest postReplyCommentCreateRequest) {
        postReplyCommentService.createReplyComment(userId, commentId, postReplyCommentCreateRequest);
        return ApiResponse.success(null);
    }

    @Operation(summary = "게시물 대댓글 수정 api", description = """
            게시물 댓글을 수정하기 위한 api입니다.
            요청 url 상에 commentId 와 reply-commentId를 받습니다. 즉, 댓글 id와 대댓글 id를 받습니다.
            PostReplyCommentUpdateRequest 요청 json에 content 필드를 이용하여 댓글 내용을 작성합니다.
            """)
    @PatchMapping("/posts/comments/reply-comments/{reply-commentId}")
    public ResponseEntity<ApiResponse<?>> editPostReplyComment(@Parameter(hidden = true) @UserId Long userId,
                                                               @PathVariable(name = "reply-commentId") Long replyCommentId,
                                                               @RequestBody PostReplyCommentUpdateRequest postReplyCommentUpdateRequest) {
        postReplyCommentService.editReplyComment(userId, replyCommentId, postReplyCommentUpdateRequest);
        return ApiResponse.success(null);
    }

    @Operation(summary = "게시물 대댓글 삭제 api", description = """
            게시물 댓글을 삭제하기 위한 api입니다.
            요청 url 상에 reply-commentId를 받습니다. 즉, 대댓글 id를 받습니다.
            """)
    @DeleteMapping("/posts/comments/reply-comments/{reply-commentId}")
    public ResponseEntity<ApiResponse<?>> deletePostReplyComment(@Parameter(hidden = true) @UserId Long userId,
                                                                 @PathVariable(name = "reply-commentId") Long replyCommentId) {
        postReplyCommentService.deleteReplyComment(replyCommentId);
        return ApiResponse.success(null);
    }
}
