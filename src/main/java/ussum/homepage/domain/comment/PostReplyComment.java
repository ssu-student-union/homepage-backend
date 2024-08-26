package ussum.homepage.domain.comment;

import lombok.Builder;
import lombok.Getter;
import ussum.homepage.infra.utils.DateUtils;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostReplyComment {
    private Long id;
    private String content;
    private Long commentId;
    private Long userId;
    private String createdAt;
    private String updatedAt;
    private String lastEditedAt;
    private Boolean isDeleted;
    private String deletedAt;

    public static PostReplyComment of(Long id, String content, Long commentId, Long userId,
                                      LocalDateTime createdAt, LocalDateTime updatedAt,
                                      LocalDateTime lastEditedAt,
                                      Boolean isDeleted, LocalDateTime deletedAt) {
        return PostReplyComment.builder()
                .id(id)
                .content(content)
                .commentId(commentId)
                .userId(userId)
                .createdAt(DateUtils.formatHourMinSecToCustomString(createdAt))
                .updatedAt(DateUtils.formatHourMinSecToCustomString(updatedAt))
                .lastEditedAt(DateUtils.formatHourMinSecToCustomString(lastEditedAt))
                .isDeleted(isDeleted)
                .deletedAt(DateUtils.formatHourMinSecToCustomString(deletedAt))
                .build();
    }
}
