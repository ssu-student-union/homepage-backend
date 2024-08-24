package ussum.homepage.infra.jpa.post.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "post_file")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String typeName;
    @Enumerated(EnumType.STRING)
    private FileCategory fileCategory;
    private String url;
    private String size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;

    public static PostFileEntity of(Long id, String typeName, FileCategory fileCategory, String url, String size, PostEntity postEntity) {
        return new PostFileEntity(id, typeName, fileCategory, url, size, postEntity);
    }

}

