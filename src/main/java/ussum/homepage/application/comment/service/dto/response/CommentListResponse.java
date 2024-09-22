package ussum.homepage.application.comment.service.dto.response;

import java.util.List;

public record CommentListResponse(
        List<PostCommentResponse> postComments,
        Integer total
) {
    public static CommentListResponse of(List<PostCommentResponse> postCommentResponses, Integer total) {
        return new CommentListResponse(postCommentResponses, total);
    }

    public void validAuthority(List<String> canAuthorityList) {
        postComments.forEach(postComment -> {
            postComment.canAuthority(canAuthorityList);
            postComment.getPostReplyComments().stream().forEach(
                    postReplyComment -> postReplyComment.canAuthority(canAuthorityList)
            );
        });
    }
}
