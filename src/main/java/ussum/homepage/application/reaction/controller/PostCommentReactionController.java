package ussum.homepage.application.reaction.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ussum.homepage.application.reaction.service.PostCommentReactionService;
import ussum.homepage.application.reaction.service.dto.request.CreatePostCommentReactionReq;
import ussum.homepage.application.reaction.service.dto.request.PostCommentReactionCreateRequest;
import ussum.homepage.application.reaction.service.dto.response.PostCommentReactionResponse;
import ussum.homepage.global.ApiResponse;
import ussum.homepage.global.config.auth.UserId;

@RequiredArgsConstructor
@RequestMapping
@RestController
@Tag(name = "post_comment_reaction", description = "게시물 댓글 반응 api")
public class PostCommentReactionController {
    private final PostCommentReactionService postCommentReactionService;

    @Operation(summary = "게시물 댓글 반응 통합 api", description = """
            게시물 댓글 반응을 위한 통합 api입니다.
            
            요청 json으로 like 또는 unlike를 받습니다.
            
            ex. like를 Request에 넣어서 요청을 하면 댓글 좋아요가 생성되고, 동일한 요청을 한번더 요청하면 이전에 눌렀던 댓긇 좋아요가 취소됩니다.
            
            """)
    @PostMapping("/toggle/posts/{commentId}")
    public ResponseEntity<ApiResponse<?>> togglePostCommentReaction(@UserId Long userId,
                                                                    @PathVariable(name = "commentId") Long commentId,
                                                                    @RequestBody CreatePostCommentReactionReq createPostCommentReactionReq) {
        postCommentReactionService.postCommentReactionToggle(userId, commentId, createPostCommentReactionReq);
        return ApiResponse.success("댓글에 성공적으로 반응하였습니다.");
    }

    @Operation(summary = "게시물 댓글 반응 생성 api", description = """
            게시물 댓글 반응을 등록하기 위한 api입니다.
            
            요청 json으로 like 또는 unlike를 
            받습니다.
            
            """)
    @PostMapping("/boards/posts/comments/{commentId}/reactions")
    public ResponseEntity<ApiResponse<?>> createPostCommentReaction(@PathVariable(name = "commentId") Long commentId,
                                                                    @RequestBody PostCommentReactionCreateRequest postCommentReactionCreateRequest) {
        postCommentReactionService.createPostCommentReaction(commentId, postCommentReactionCreateRequest);
        return ApiResponse.success(null);
    }

    @Operation(summary = "게시물 댓글 반응 삭제 api", description = """
            게시물 댓글 반응을 삭제하기 위한 api입니다.
            
            요청 json으로 like 또는 unlike를 받습니다.
            
            """)
    @DeleteMapping("/boards/posts/comments/{commentId}/reactions")
    public ResponseEntity<ApiResponse<?>> deletePostCommentReaction(@PathVariable(name = "commentId") Long commentId,
                                                                    @RequestBody PostCommentReactionCreateRequest postCommentReactionCreateRequest) {
        postCommentReactionService.deletePostCommentReaction(commentId, postCommentReactionCreateRequest);
        return ApiResponse.success(null);
    }
}
