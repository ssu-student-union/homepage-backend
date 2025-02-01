package ussum.homepage.domain.post;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ussum.homepage.infra.jpa.post.entity.SuggestionTarget;
import ussum.homepage.infra.utils.DateUtils;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Post {
    private Long id;
    private String title;
    private String content;
    private Integer viewCount;
    private String thumbnailImage;
    private String status;
    private String createdAt;
    private String updatedAt;
    private String lastEditedAt;
    private String category;
    private String suggestionTarget;
    private String qnaTarget;
    private Long userId;
    private Long boardId;

    public static Post of(Long id,
                          String title,
                          String content,
                          Integer viewCount,
                          String thumbnailImage,
                          String status,
                          LocalDateTime createdAt,
                          LocalDateTime updatedAt,
                          LocalDateTime lastEditedAt,
                          String category,
                          String suggestionTarget,
                          String qnaTarget,
                          Long userId,
                          Long boardId) {
        return new Post(id, title, content, viewCount, thumbnailImage, status, DateUtils.formatHourMinSecToCustomString(createdAt),
                DateUtils.formatHourMinSecToCustomString(updatedAt), DateUtils.formatHourMinSecToCustomString(lastEditedAt), category,
                suggestionTarget, qnaTarget, userId, boardId);
    }
}
