package ussum.homepage.domain.post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ussum.homepage.infra.jpa.post.entity.Category;
import ussum.homepage.infra.utils.DateUtils;


import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Post {
    private Long id;
    private String title;
    private String content;
    private Integer viewCount;
    private String thumbnailImage;
    private String status;
    private String onGoingStatus;
    private String createdAt;
    private String updatedAt;
    private String lastEditedAt;
    private String category;
    private Long userId;
    private Long boardId;


    public static Post of(Long id,
                          String title,
                          String content,
                          Integer viewCount,
                          String thumbnailImage,
                          String status,
                          String onGoingStatus,
                          LocalDateTime createdAt,
                          LocalDateTime updatedAt,
                          LocalDateTime lastEditedAt,
                          Category category,
                          Long userId,
                          Long boardId) {
        return new Post(id, title, content, viewCount, thumbnailImage, status, onGoingStatus,  DateUtils.formatToCustomString(createdAt),
                String.valueOf(updatedAt), String.valueOf(lastEditedAt), category.getStringCategoryCode(),
                userId, boardId);
    }
}
