package ussum.homepage.infra.jpa.post;

import org.springframework.stereotype.Component;
import ussum.homepage.domain.post.PostFile;
import ussum.homepage.infra.jpa.post.entity.PostFileEntity;

@Component
public class PostFileMapper {
    public PostFile toDomain(PostFileEntity postFileEntity) {
        return PostFile.of(
                postFileEntity.getId(),
                postFileEntity.getTypeName(),
                postFileEntity.getUrl(),
                postFileEntity.getSize(),
                postFileEntity.getPostEntity().getId()
        );
    }


}
