package ussum.homepage.application.comment.service.dto.request;

import ussum.homepage.domain.comment.PostReplyComment;

public record PostReplyCommentCreateRequest(
        String content
) {
    public PostReplyComment toDomain(Long userId,Long commentId) {
        return PostReplyComment.of(
                null,
                content,
                commentId,
                userId,
                null,
                null,
                false,
                null
        );
    }

}
