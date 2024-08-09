package ussum.homepage.application.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ussum.homepage.application.comment.service.CommentService;
import ussum.homepage.application.comment.service.dto.response.PostCommentListResponse;
import ussum.homepage.application.comment.service.dto.response.PostCommentResponse;
import ussum.homepage.application.comment.service.dto.request.PostCommentCreateRequest;
import ussum.homepage.application.comment.service.dto.request.PostCommentUpdateRequest;
import ussum.homepage.global.ApiResponse;
import ussum.homepage.global.config.auth.UserId;

@RequiredArgsConstructor
@RequestMapping
@RestController
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/boards/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<?>> getPostCommentList(/*@PathVariable(name = "boardCode") String boardCode,*/
                                                             @PathVariable(name = "postId") Long postId,
                                                             @RequestParam(name = "page") int page,
                                                             @RequestParam(name = "take") int take,
                                                             @RequestParam(required = false, name = "type") String type) {

        PostCommentListResponse comments = commentService.getCommentList(/*boardCode,*/ postId, page, take, type);
        return ApiResponse.success(comments);
    }
    @PostMapping("/boards/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<?>> createPostComment(@UserId Long userId,
                                                            @PathVariable(name = "postId") Long postId,
                                                            @RequestBody PostCommentCreateRequest postCommentCreateRequest) {
        PostCommentResponse comment = commentService.createComment(userId, postId, postCommentCreateRequest);
        return ApiResponse.success(comment);
    }
    @PatchMapping("/boards/posts/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<?>> editPostComment(@UserId Long userId,
                                                          /*@PathVariable(name = "boardCode") String boardCode,*/
                                                          @PathVariable(name = "postId") Long postId,
                                                          @PathVariable(name = "commentId") Long commentId,
                                                          @RequestBody PostCommentUpdateRequest postCommentUpdateRequest) {
        PostCommentResponse comment = commentService.editComment(userId, postId, commentId, postCommentUpdateRequest);
        return ApiResponse.success(comment);
    }
    @DeleteMapping("/boards/{boardCode}/posts/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<?>> deletePostComment(@PathVariable(name = "boardCode") String boardCode,
                                                            @PathVariable(name = "postId") Long postId,
                                                            @PathVariable(name = "commentId") Long commentId) {
        commentService.deleteComment(commentId);
        return ApiResponse.success(null);
    }
}
