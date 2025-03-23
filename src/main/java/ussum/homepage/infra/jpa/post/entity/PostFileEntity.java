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
    private String fileName;
    //image인지 file인지
    private String typeName;

    @Enumerated(EnumType.STRING)
    @Column(length = 100) // 적절한 길이로 설정
    private FileCategory fileCategory;
    private String url;
    private String size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;

    public static PostFileEntity of(Long id, String fileName, String typeName, FileCategory fileCategory, String url, String size, PostEntity postEntity) {
        return new PostFileEntity(id, fileName, typeName, fileCategory, url, size, postEntity);
    }

}

