package ussum.homepage.infra.jpa.post;

import org.springframework.stereotype.Component;
import ussum.homepage.domain.post.PostFile;
import ussum.homepage.infra.jpa.post.entity.PostFileEntity;

import java.util.List;

@Component
public class PostFileMapper {
    public PostFile toDomain(PostFileEntity postFileEntity) {
        return PostFile.of(
                postFileEntity.getId(),
                postFileEntity.getTypeName(),
                postFileEntity.getUrl(),
                postFileEntity.getSize(),
                null
        );
    }

    public List<PostFile> toDomain(List<PostFileEntity> postFileEntities) {
        return postFileEntities.stream()
                .map(this::toDomain)
                .toList();
    }

    public PostFileEntity toEntity(PostFile postFile) {
        return PostFileEntity.of(
                postFile.getId(),
                postFile.getTypeName(),
                postFile.getUrl(),
                null,
                null
        );
    }


}
