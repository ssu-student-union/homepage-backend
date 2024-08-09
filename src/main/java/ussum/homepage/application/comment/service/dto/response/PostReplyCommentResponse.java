package ussum.homepage.application.comment.service.dto.response;

import ussum.homepage.domain.comment.PostReplyComment;

public record PostReplyCommentResponse(
        Long id,
        String authorName,
        String content,
        String lastEditedAt,
        Integer likeCount
) {
    public static PostReplyCommentResponse of(PostReplyComment postReplyComment, String authorName, Integer likeCount) {
        return new PostReplyCommentResponse(
                postReplyComment.getId(),
                authorName,
                postReplyComment.getContent(),
                postReplyComment.getLastEditedAt(),
                likeCount
        );
    }
}
