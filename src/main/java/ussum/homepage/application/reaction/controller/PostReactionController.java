package ussum.homepage.application.reaction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ussum.homepage.application.reaction.service.PostReactionService;
import ussum.homepage.application.reaction.service.dto.request.PostReactionCreateRequest;
import ussum.homepage.application.reaction.service.dto.response.PostReactionResponse;
import ussum.homepage.global.ApiResponse;

@RequiredArgsConstructor
@RequestMapping
@RestController
public class PostReactionController {
    private final PostReactionService postReactionService;

    @PostMapping("/boards/posts/{postId}/reactions")
    public ApiResponse<Void> createPostReaction(@PathVariable(name = "postId") Long postId,
                                                @RequestBody PostReactionCreateRequest postReactionCreateRequest) {
        PostReactionResponse postReaction = postReactionService.createPostReaction(postId, postReactionCreateRequest);
        return ApiResponse.onSuccess(null);
    }
}
