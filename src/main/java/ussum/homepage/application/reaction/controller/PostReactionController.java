package ussum.homepage.application.reaction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ussum.homepage.application.reaction.service.PostReactionService;
import ussum.homepage.application.reaction.service.dto.request.CreatePostReactionReq;
import ussum.homepage.global.ApiResponse;
import ussum.homepage.global.config.auth.UserId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards/{boardCode}/{postId}/reactions")
public class PostReactionController {
    private final PostReactionService postReactionService;
    @PostMapping("/toggle")
    public ResponseEntity<ApiResponse<?>> togglePostReaction(@UserId Long userId, @PathVariable(name = "postId")Long postId, @RequestBody CreatePostReactionReq createPostReactionReq){
        postReactionService.postReactionToggle(userId, postId, createPostReactionReq);
        return ApiResponse.success(null);
    }
}
