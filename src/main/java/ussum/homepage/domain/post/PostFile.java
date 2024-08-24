package ussum.homepage.domain.post;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ussum.homepage.infra.jpa.post.entity.PostEntity;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostFile {
    private Long id;
    private String typeName;
    private String fileCategory;
    private String url;
    private String size;
    private Long postId;

    public static PostFile of(Long id, String typeName, String fileCategory, String url, String size, Long postId) {
        return new PostFile(id, typeName, fileCategory, url, size, postId);
    }
}
