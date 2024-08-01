package ussum.homepage.application.reaction.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ussum.homepage.application.reaction.service.PostReactionService;
import ussum.homepage.application.reaction.service.dto.request.CreatePostReactionReq;
import ussum.homepage.global.ApiResponse;
import ussum.homepage.global.config.auth.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ussum.homepage.application.reaction.service.dto.request.PostReactionCreateRequest;
import ussum.homepage.application.reaction.service.dto.response.PostReactionResponse;


@RequiredArgsConstructor
@RequestMapping
@RestController
@Tag(name = "post_reaction", description = "게시물 반응 api")
public class PostReactionController {
    private final PostReactionService postReactionService;

    @Operation(summary = "게시물 반응 통합 api", description = """
            게시물 반응을 위한 통합 api입니다.
            
            요청 json으로 like 또는 unlike를 받습니다.
            
            ex. like를 Request에 넣어서 요청을 하면 좋아요가 생성되고, 동일한 요청을 한번더 요청하면 이전에 눌렀던 좋아요가 취소됩니다.
            
            """)
    @PostMapping("/toggle/{postId}")
    public ResponseEntity<ApiResponse<?>> togglePostReaction(@UserId Long userId, @PathVariable(name = "postId")Long postId, @RequestBody CreatePostReactionReq createPostReactionReq) {
        postReactionService.postReactionToggle(userId, postId, createPostReactionReq);
        return ApiResponse.success(null);
    }

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
    public ApiResponse<Void> deletePostReaction(@UserId Long userId, @PathVariable(name = "postId") Long postId,
                                                @RequestBody PostReactionCreateRequest postReactionCreateRequest) {
        postReactionService.deletePostReaction(userId, postId, postReactionCreateRequest);
        return ApiResponse.onSuccess(null);

    }
}
