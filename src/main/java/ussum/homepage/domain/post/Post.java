package ussum.homepage.domain.post;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ussum.homepage.infra.jpa.post.entity.BoardEntity;
import ussum.homepage.infra.jpa.post.entity.CategoryEntity;
import ussum.homepage.infra.jpa.user.entity.UserEntity;

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
    private String createdAt;
    private String updatedAt;
    private String lastEditedAt;
//    private String deletedAt;
    private Long userId;
    private Long boardId;
    private Long categoryId;

    public static Post of(Long id,
                          String title,
                          String content,
                          Integer viewCount,
                          String thumbnailImage,
                          LocalDateTime createdAt,
                          LocalDateTime updatedAt,
                          LocalDateTime lastEditedAt,
//                          LocalDateTime deletedAt,
                          Long userId,
                          Long boardId,
                          Long categoryId) {
        return new Post(id, title, content, viewCount, thumbnailImage, String.valueOf(createdAt),
                String.valueOf(updatedAt), String.valueOf(lastEditedAt),
                userId, boardId, categoryId);
    }
}
