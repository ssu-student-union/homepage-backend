package ussum.homepage.infra.jpa.post.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "post_file")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String typeName;
    private String url;
    private String size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;

}

