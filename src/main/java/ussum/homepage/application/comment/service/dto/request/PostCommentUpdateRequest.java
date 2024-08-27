package ussum.homepage.application.comment.service.dto.request;

import ussum.homepage.domain.comment.PostComment;
import ussum.homepage.infra.utils.DateUtils;

import java.time.LocalDateTime;

public record PostCommentUpdateRequest(
        String content
) {
    public PostComment toDomain(PostComment postComment, Long commentId, Long postId, Long userId) {
        return PostComment.of(
                commentId,
                content,
                postId,
                userId,
                postComment.getCommentType(),
                DateUtils.parseHourMinSecFromCustomString(postComment.getCreatedAt()),
                DateUtils.parseHourMinSecFromCustomString(postComment.getUpdatedAt()),
                LocalDateTime.now(),
                postComment.getIsDeleted(),
                DateUtils.parseHourMinSecFromCustomString(postComment.getDeletedAt())
        );
    }

}
