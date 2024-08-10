package ussum.homepage.application.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ussum.homepage.application.comment.service.PostCommentManageService;
import ussum.homepage.application.post.service.dto.request.PostUserRequest;
import ussum.homepage.global.ApiResponse;

@RequiredArgsConstructor
@RequestMapping
@RestController
@Tag(name = "post_comment_and_post_reply_comment", description = "게시물 단건 조회 시 댓글과 대댓글을 모두 조회하는 api")
public class PostCommentManagerController {
    private final PostCommentManageService postCommentManageService;

    @Operation(summary = "게시물 단건 조회 시 댓글과 대댓글을 모두 조회하는 api", description = """
            게시물 단건 조회 시 댓글과 대댓글을 모두 조회하는 api 입니다. 
            
            요청으로 postId, type(인기순 or 최신순), 그리고 form-data 형식으로 userId의 값으로 Long 형으로 받습니다.
            
            """)
    @GetMapping("/board/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<?>> getPostCommentList(@PathVariable Long postId,
                                                             @RequestParam(name = "type") String type,
                                                             @ModelAttribute PostUserRequest postUserRequest) { //type은 "인기순" 인지 "최신순"인지

        return ApiResponse.success(postCommentManageService.getCommentList(postId, type, postUserRequest));
    }
}
