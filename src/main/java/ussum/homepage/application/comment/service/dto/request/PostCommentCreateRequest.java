package ussum.homepage.application.comment.service.dto.request;

import ussum.homepage.domain.comment.PostComment;

public record PostCommentCreateRequest(
        String content
) {
    public PostComment toDomain(Long userId, Long postId, String commentType) {
        return PostComment.of(
                null,
                content,
                postId,
                userId,
                commentType,
                null,
                null,
                null,
                null
        );
    }
}
