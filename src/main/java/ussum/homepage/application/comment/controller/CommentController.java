package ussum.homepage.application.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ussum.homepage.application.comment.service.CommentService;
import ussum.homepage.application.comment.service.dto.PostCommentListResponse;
import ussum.homepage.application.comment.service.dto.PostCommentResponse;
import ussum.homepage.application.comment.service.dto.request.PostCommentCreateRequest;
import ussum.homepage.application.comment.service.dto.request.PostCommentUpdateRequest;
import ussum.homepage.application.post.service.dto.BoardListResponse;
import ussum.homepage.global.ApiResponse;

@RequiredArgsConstructor
@RequestMapping
@RestController
public class CommentController {
    private final CommentService commentService;
    @GetMapping("/boards/{boardCode}/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<?>> getPostCommentList(@PathVariable(name = "boardCode") String boardCode,
                                                                                  @PathVariable(name = "postId") Long postId,
                                                                                  @RequestParam(name = "page") int page,
                                                                                  @RequestParam(name = "take") int take,
                                                                                  @RequestParam(required = false, name = "type") String type) {
        PostCommentListResponse comments = commentService.getCommentList(boardCode, postId, page, take, type);
        return ApiResponse.success(comments);
    }
    @PostMapping("/boards/{boardCode}/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<?>> createPostComment(@PathVariable(name = "boardCode") String boardCode,
                                                            @PathVariable(name = "postId") Long postId,
                                                            @RequestBody PostCommentCreateRequest postCommentCreateRequest) {
        PostCommentResponse comment = commentService.createComment(null, boardCode, postId, postCommentCreateRequest);
        return ApiResponse.success(comment);
    }
    @PatchMapping("/boards/{boardCode}/posts/{postId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<?>> editPostComment(@PathVariable(name = "boardCode") String boardCode,
                                                          @PathVariable(name = "postId") Long postId,
                                                          @PathVariable(name = "commentId") Long commentId,
                                                          @RequestBody PostCommentUpdateRequest postCommentUpdateRequest) {
        PostCommentResponse comment = commentService.editComment(null, postId, commentId, postCommentUpdateRequest);
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
