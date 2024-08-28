package ussum.homepage.application.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/board")
@RestController
@Tag(name = "post_comment", description = "게시물 댓글 생성, 수정, 삭제 api")
public class CommentController {
    private final CommentService commentService;

//    @GetMapping("/posts/{postId}/comments")
//    public ResponseEntity<ApiResponse<?>> getPostCommentList(/*@PathVariable(name = "boardCode") String boardCode,*/
//                                                             @PathVariable(name = "postId") Long postId,
//                                                             @RequestParam(name = "page") int page,
//                                                             @RequestParam(name = "take") int take,
//                                                             @RequestParam(required = false, name = "type") String type) {
//
//        PostCommentListResponse comments = commentService.getCommentList(/*boardCode,*/ postId, page, take, type);
//        return ApiResponse.success(comments);
//    }

    @Operation(summary = "게시물 댓글 생성 api", description = """
            게시물 댓글을 생성하기 위한 api입니다.
            요청 url 상에 postId를 받습니다. 즉, 게시물 id를 받습니다.
            PostCommentCreateRequest 요청 json에 content 필드를 이용하여 댓글 내용을 작성합니다.
          """)
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<?>> createPostComment(@Parameter(hidden = true) @UserId Long userId,
                                                            @PathVariable(name = "postId") Long postId,
                                                            @RequestBody PostCommentCreateRequest postCommentCreateRequest) {
        PostCommentResponse comment = commentService.createComment(userId, postId, postCommentCreateRequest);
        return ApiResponse.success(comment);
    }

    @Operation(summary = "게시물 댓글 수정 api", description = """
            게시물 댓글을 수정하기 위한 api입니다.
            요청 url 상에 postId 와 commentId를 받습니다. 즉, 게시물 id와 댓글 id를 받습니다.
            PostCommentUpdateRequest 요청 json에 content 필드를 이용하여 댓글 내용을 작성합니다.
            """)
    @PatchMapping("/posts/comments/{commentId}")
    public ResponseEntity<ApiResponse<?>> editPostComment(@Parameter(hidden = true) @UserId Long userId,
                                                          @PathVariable(name = "commentId") Long commentId,
                                                          @RequestBody PostCommentUpdateRequest postCommentUpdateRequest) {
        commentService.editComment(userId, commentId, postCommentUpdateRequest);
        return ApiResponse.success(null);
    }

    @Operation(summary = "게시물 댓글 삭제 api", description = """
            게시물 댓글을 삭제하기 위한 api입니다.
            요청 url 상에 commentId를 받습니다. 즉, 댓글 id를 받습니다.
            """)
    @DeleteMapping("/posts/comments/{commentId}")
    public ResponseEntity<ApiResponse<?>> deletePostComment(@Parameter(hidden = true) @UserId Long userId,
                                                            @PathVariable(name = "commentId") Long commentId) {
        commentService.deleteComment(commentId);
        return ApiResponse.success(null);
    }
}
