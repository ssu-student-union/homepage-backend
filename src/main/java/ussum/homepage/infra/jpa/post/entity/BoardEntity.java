package ussum.homepage.infra.jpa.post.entity;
import jakarta.persistence.*;
import lombok.*;
import ussum.homepage.infra.jpa.BaseEntity;

@Entity
@Table(name = "board")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private BoardCode boardCode;
    private String name;

    public BoardEntity(Long id, BoardCode boardCode, String name) {
        this.id = id;
        this.boardCode = boardCode;
        this.name = name;
    }

    public static BoardEntity from(Long id){
        return new BoardEntity(id, null,null);
    }
    public static BoardEntity of(Long id, BoardCode boardCode, String name){
        return new BoardEntity(id, boardCode,name);
    }
}
