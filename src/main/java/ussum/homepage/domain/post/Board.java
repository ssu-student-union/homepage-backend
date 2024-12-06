package ussum.homepage.domain.post;

import jakarta.persistence.*;
import ussum.homepage.infra.jpa.post.entity.BoardCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Board {
    private Long id;
    private String boardCode;
    private String name;
    private String createdAt;
    private String updatedAt;
    public static Board of(Long id,
                           String boardCode,
                           String name, LocalDateTime createdAt, LocalDateTime updatedAt){
        return new Board(id, String.valueOf(boardCode), name,String.valueOf(createdAt),String.valueOf(updatedAt));
    }
}
