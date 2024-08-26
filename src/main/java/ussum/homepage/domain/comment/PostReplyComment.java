package ussum.homepage.domain.comment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostReplyComment {
    private Long id;
    private String content;
    private Long commentId;
    private Long userId;
    private String createdAt;
    private String lastEditedAt;
    private Boolean isDeleted;
    private String deletedAt;

    public static PostReplyComment of(Long id, String content, Long commentId, Long userId, String createdAt, String lastEditedAt,
                                      Boolean isDeleted, String deletedAt) {
        return PostReplyComment.builder()
                .id(id)
                .content(content)
                .commentId(commentId)
                .userId(userId)
                .createdAt(createdAt)
                .lastEditedAt(lastEditedAt)
                .isDeleted(isDeleted)
                .deletedAt(deletedAt)
                .build();
    }
}
