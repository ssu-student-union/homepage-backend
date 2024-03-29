package ussum.homepage.infra.jpa.post.entity;
import jakarta.persistence.*;
import lombok.*;
import ussum.homepage.infra.jpa.BaseEntity;
import ussum.homepage.infra.jpa.user.entity.MajorCode;

@Entity
@Table(name = "category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CategoryEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private MajorCode majorCode;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    public static CategoryEntity of(Long id, MajorCode majorCode, String name, BoardEntity board) {
        return new CategoryEntity(id, majorCode, name, board);
    }

    public static CategoryEntity from(Long id) {
        return new CategoryEntity(id, null, null,null);
    }
}
