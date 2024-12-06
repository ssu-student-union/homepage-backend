package ussum.homepage.application.comment.service.dto.request;

import ussum.homepage.domain.comment.PostReplyComment;
import ussum.homepage.infra.utils.DateUtils;

import java.time.LocalDateTime;

public record PostReplyCommentUpdateRequest(
        String content
) {
    public PostReplyComment toDomain(PostReplyComment postReplyComment, Long userId) {
        return PostReplyComment.of(
                postReplyComment.getId(),
                content,
                postReplyComment.getCommentId(),
                userId,
                DateUtils.parseHourMinSecFromCustomString(postReplyComment.getCreatedAt()),
                DateUtils.parseHourMinSecFromCustomString(postReplyComment.getUpdatedAt()),
                LocalDateTime.now(),
                postReplyComment.getIsDeleted(),
                DateUtils.parseHourMinSecFromCustomString(postReplyComment.getDeletedAt())
        );
    }

}