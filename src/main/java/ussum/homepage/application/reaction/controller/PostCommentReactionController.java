package ussum.homepage.application.reaction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ussum.homepage.application.reaction.service.PostCommentReactionService;
import ussum.homepage.application.reaction.service.dto.request.PostCommentReactionCreateRequest;
import ussum.homepage.application.reaction.service.dto.response.PostCommentReactionResponse;
import ussum.homepage.global.ApiResponse;

@RequiredArgsConstructor
@RequestMapping
@RestController
public class PostCommentReactionController {
    private final PostCommentReactionService postCommentReactionService;

    @PostMapping("/boards/posts/comments/{commentId}/reactions")
    public ApiResponse<Void> createPostCommentReaction(@PathVariable(name = "commentId") Long commentId,
                                                                              @RequestBody PostCommentReactionCreateRequest postCommentReactionCreateRequest) {
        PostCommentReactionResponse commentReaction = postCommentReactionService.createPostCommentReaction(commentId, postCommentReactionCreateRequest);
//        return ApiResponse.onSuccess(commentReaction);
        return ApiResponse.onSuccess(null);
    }

    @DeleteMapping("/boards/posts/comments/{commentId}/reactions")
    public ApiResponse<Void> deletePostCommentReaction(@PathVariable(name = "commentId") Long commentId,
                                                                              @RequestBody PostCommentReactionCreateRequest postCommentReactionCreateRequest) {
        postCommentReactionService.deletePostCommentReaction(commentId, postCommentReactionCreateRequest);
        return ApiResponse.onSuccess(null);
    }
}
