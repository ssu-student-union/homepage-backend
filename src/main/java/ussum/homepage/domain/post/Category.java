package ussum.homepage.domain.post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ussum.homepage.infra.jpa.post.entity.BoardEntity;
import ussum.homepage.infra.jpa.user.entity.MajorCode;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Category {
    private Long id;
    private String categoryCode;
    private String name;
    private Long boardId;
    private String createdAt;
    private String updatedAt;

    public static Category of(Long id, String categoryCode, String name, Long boardId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Category(id, String.valueOf(categoryCode), name, boardId, String.valueOf(createdAt), String.valueOf(updatedAt));
    }
}
