package ussum.homepage.application.comment.service.dto.request;

import ussum.homepage.domain.comment.PostReplyComment;

public record PostReplyCommentUpdateRequest(
        String content
) {
    public PostReplyComment toDomain(PostReplyComment postReplyComment, Long userId, Long commentId) {
        return PostReplyComment.of(
                postReplyComment.getId(),
                content,
                commentId,
                userId,
                postReplyComment.getCreatedAt(),
                postReplyComment.getLastEditedAt(),
                postReplyComment.getIsDeleted(),
                postReplyComment.getDeletedAt()
        );
    }
}
