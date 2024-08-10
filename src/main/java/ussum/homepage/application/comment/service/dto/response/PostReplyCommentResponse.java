package ussum.homepage.application.comment.service.dto.response;

import ussum.homepage.domain.comment.PostReplyComment;

public record PostReplyCommentResponse(
        Long id,
        String authorName,
        String content,
        String createdAt,
        String lastEditedAt,
        Integer likeCount,
        Boolean isAuthor
) {
    public static PostReplyCommentResponse of(PostReplyComment postReplyComment, String authorName, Integer likeCount, Boolean isAuthor) {
        return new PostReplyCommentResponse(
                postReplyComment.getId(),
                authorName,
                postReplyComment.getContent(),
                postReplyComment.getCreatedAt(),
                postReplyComment.getLastEditedAt(),
                likeCount,
                isAuthor
        );
    }
}
