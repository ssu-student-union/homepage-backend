package ussum.homepage.domain.comment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostComment {
    private Long id;
    private String content;
    private Long postId;
    private Long userId;
    private String commentType;
    private String createdAt;
    private String lastEditedAt;
    private Boolean isDeleted;
    private String deletedAt;

    public static PostComment of(Long id,
                                 String content,
                                 Long postId,
                                 Long userId,
                                 String commentType,
                                 String createdAt,
                                 String lastEditedAt,
                                 Boolean isDeleted,
                                 String deletedAt) {
        return new PostComment(id, content, postId, userId, commentType, createdAt, lastEditedAt, isDeleted, deletedAt);
    }
}
