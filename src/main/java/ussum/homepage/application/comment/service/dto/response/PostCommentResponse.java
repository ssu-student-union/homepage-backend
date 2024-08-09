package ussum.homepage.application.comment.service.dto.response;

import ussum.homepage.domain.comment.PostComment;

import java.util.List;

public record PostCommentResponse(
        Long id,
        String authorName,
        String content,
        String commentType,
        String lastEditedAt,
        Integer likeCount,
        List<PostReplyCommentResponse> postReplyComments
) {
    public static PostCommentResponse of(PostComment postComment, String authorName, String commentType, Integer likeCount, List<PostReplyCommentResponse> postReplyComments) {
        return new PostCommentResponse(
                postComment.getId(),
                authorName,
                postComment.getContent(),
                commentType,
                postComment.getLastEditedAt(),
                likeCount,
                postReplyComments);
    }
}
