package ussum.homepage.application.comment.service.dto.response;

import java.util.List;

public record CommentListResponse(
        List<PostCommentResponse> postComments,
        Integer total
) {
    public static CommentListResponse of(List<PostCommentResponse> postCommentResponses, Integer total) {
        return new CommentListResponse(postCommentResponses, total);
    }
}
