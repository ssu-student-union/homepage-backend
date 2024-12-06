package ussum.homepage.application.comment.service.dto.response;

import java.util.List;

public record CommentListResponse(
        List<PostCommentResponse> postComments,
        Integer total,
        List<String> allowedAuthorities
) {
    public static CommentListResponse of(List<PostCommentResponse> postCommentResponses, Integer total) {
        return new CommentListResponse(postCommentResponses, total, List.of());
    }

//    public void validAuthority(List<String> canAuthorityList) {
//        postComments.forEach(postComment -> {
//            postComment.canAuthority(canAuthorityList);
//            postComment.getPostReplyComments().stream().forEach(
//                    postReplyComment -> postReplyComment.canAuthority(canAuthorityList)
//            );
//        });
//    }

    public CommentListResponse validAuthorities(List<String> allowedAuthorities) {
        return new CommentListResponse(this.postComments, this.total, allowedAuthorities);
    }
}
