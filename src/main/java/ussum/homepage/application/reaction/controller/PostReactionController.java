package ussum.homepage.application.reaction.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ussum.homepage.application.reaction.service.PostReactionService;
import ussum.homepage.application.reaction.service.dto.request.PostReactionCreateRequest;
import ussum.homepage.application.reaction.service.dto.response.PostReactionResponse;
import ussum.homepage.global.ApiResponse;

@RequiredArgsConstructor
@RequestMapping
@RestController
@Tag(name = "post_reaction", description = "게시물 반응 api")
public class PostReactionController {
    private final PostReactionService postReactionService;

    @Operation(summary = "게시물 반응 생성 api", description = """
            게시물 반응을 등록하기 위한 api입니다.
            
            요청 json으로 like 또는 unlike를 받습니다.
            
            """)
    @PostMapping("/boards/posts/{postId}/reactions")
    public ApiResponse<Void> createPostReaction(@PathVariable(name = "postId") Long postId,
                                                @RequestBody PostReactionCreateRequest postReactionCreateRequest) {
        PostReactionResponse postReaction = postReactionService.createPostReaction(postId, postReactionCreateRequest);
        return ApiResponse.onSuccess(null);
    }

    @Operation(summary = "게시물 반응 삭제 api", description = """
            게시물 반응을 삭제하기 위한 api입니다.
            
            요청 json으로 like 또는 unlike를 받습니다.
            
            """)
    @DeleteMapping("/boards/posts/{postId}/reactions")
    public ApiResponse<Void> deletePostReaction(@PathVariable(name = "postId") Long postId,
                                                @RequestBody PostReactionCreateRequest postReactionCreateRequest) {
        postReactionService.deletePostReaction(postId, postReactionCreateRequest);
        return ApiResponse.onSuccess(null);
    }
}