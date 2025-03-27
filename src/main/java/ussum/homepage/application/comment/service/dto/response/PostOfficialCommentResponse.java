package ussum.homepage.application.comment.service.dto.response;

import ussum.homepage.domain.comment.PostComment;

public record PostOfficialCommentResponse(
        Long id,
        String authorName,
        String content,
        String commentType,
        String createdAt,
        String lastEditedAt,
        Boolean isAuthor,
        Boolean isDeleted
) {
    public static PostOfficialCommentResponse of(PostComment postComment, String authorName, String commentType, Boolean isAuthor) {
        return new PostOfficialCommentResponse(
                postComment.getId(),
                authorName,
                postComment.getContent(),
                commentType,
                postComment.getCreatedAt(),
                postComment.getLastEditedAt(),
                isAuthor,
                postComment.getIsDeleted());
    }
}
