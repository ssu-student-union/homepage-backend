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
@RequestMapping("/board")
@RestController
@Tag(name = "post_comment_and_post_reply_comment", description = "게시물 단건 조회 시 댓글과 대댓글을 모두 조회하는 api")
public class PostCommentManagerController {
    private final PostCommentManageService postCommentManageService;

    @Operation(summary = "게시물 단건 조회 시 댓글과 대댓글을 모두 조회하는 api", description = """
            게시물 단건 조회 시 댓글과 대댓글을 모두 조회하는 api 입니다. 
            요청으로 postId, type(인기순 or 최신순), 그리고 String 형의 실제 studentId 값을 넣으면 됩니다.
            studentId를 받는 이유는 비로그인과 로그인 모두 조회는 할 수 있고, 
            이때 로그인 유저가 조회 시 토큰 발급 시 같이 반환 되었던 studentId 값을 이용하여, 해당 user가 해당 댓글 혹은 대댓글을 작성하였는지의 여부를 판단하기 위하여 isAuthor 필드를 이용하였습니다.
            studentId 넣고 반환되는 isAuthor 필드가 true 라면 해당 user는 본인이 작성한 댓글, 대댓글 임을 나타냅니다.
            하지만 isAuthor 필드가 false 라면(비로그인 포함) 해당 user는 본인이 작성한 댓글, 대댓글이 아니기에 수정, 삭제를 막아야 합니다. 
            """)
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<?>> getPostCommentList(@RequestParam(required = false) Long userId,
                                                             @PathVariable Long postId,
                                                             @RequestParam(name = "type") String type
                                                             ) { //type은 "인기순" 인지 "최신순"인지
        return ApiResponse.success(postCommentManageService.getCommentList(userId, postId, type));
    }
}
