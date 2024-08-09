package ussum.homepage.application.reaction.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ussum.homepage.application.reaction.service.PostReplyCommentReactionService;
import ussum.homepage.application.reaction.service.dto.request.CreatePostCommentReactionReq;
import ussum.homepage.application.reaction.service.dto.request.CreatePostReplyCommentReactionReq;
import ussum.homepage.global.ApiResponse;
import ussum.homepage.global.config.auth.UserId;

@RequiredArgsConstructor
@RequestMapping
@RestController
@Tag(name = "post_reply_comment_reaction", description = "게시물 대댓글 반응 api")
public class PostReplyCommentReactionController {
    private final PostReplyCommentReactionService postReplyCommentReactionService;

    @Operation(summary = "게시물 대댓글 반응 통합 api", description = """
            게시물 대댓글 반응을 위한 통합 api입니다.
            
            요청 json으로 like 또는 unlike를 받습니다.
            
            ex. like를 Request에 넣어서 요청을 하면 대댓글 좋아요가 생성되고, 동일한 요청을 한번더 요청하면 이전에 눌렀던 대댓긇 좋아요가 취소됩니다.
            
            """)
    @PostMapping("/toggle/posts/comments/{reply-commentId}")
    public ResponseEntity<ApiResponse<?>> togglePostReplyCommentReaction(@UserId Long userId,
                                                                    @PathVariable(name = "reply-commentId") Long replyCommentId,
                                                                    @RequestBody CreatePostReplyCommentReactionReq createPostReplyCommentReactionReq) {
        postReplyCommentReactionService.postReplyCommentReactionToggle(userId, replyCommentId, createPostReplyCommentReactionReq);
        return ApiResponse.success("댓글에 성공적으로 반응하였습니다.");
    }
}
