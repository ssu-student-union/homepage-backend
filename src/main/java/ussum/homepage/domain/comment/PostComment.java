package ussum.homepage.domain.comment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ussum.homepage.infra.utils.DateUtils;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostComment {
    private Long id;
    private String content;
    private Long postId;
    private Long userId;
    private String commentType;
    private String createdAt;
    private String updatedAt;
    private String lastEditedAt;
    private Boolean isDeleted;
    private String deletedAt;

    public static PostComment of(Long id,
                                 String content,
                                 Long postId,
                                 Long userId,
                                 String commentType,
                                 LocalDateTime createdAt,
                                 LocalDateTime updatedAt,
                                 LocalDateTime lastEditedAt,
                                 Boolean isDeleted,
                                 LocalDateTime deletedAt) {
        return new PostComment(id, content, postId, userId, commentType, DateUtils.formatHourMinSecToCustomString(createdAt),
                DateUtils.formatHourMinSecToCustomString(updatedAt), DateUtils.formatHourMinSecToCustomString(lastEditedAt),
                isDeleted, DateUtils.formatHourMinSecToCustomString(deletedAt));
    }

}
