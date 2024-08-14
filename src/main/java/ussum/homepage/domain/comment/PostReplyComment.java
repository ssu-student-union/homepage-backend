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

    public static PostReplyComment of(Long id, String content, Long commentId, Long userId, String createdAt, String lastEditedAt) {
        return PostReplyComment.builder()
                .id(id)
                .content(content)
                .commentId(commentId)
                .userId(userId)
                .createdAt(createdAt)
                .lastEditedAt(lastEditedAt)
                .build();
    }
}
