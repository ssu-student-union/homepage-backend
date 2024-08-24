package ussum.homepage.infra.jpa.post;

import org.springframework.stereotype.Component;
import ussum.homepage.domain.post.PostFile;
import ussum.homepage.infra.jpa.post.entity.FileCategory;
import ussum.homepage.infra.jpa.post.entity.PostFileEntity;

import java.util.List;

@Component
public class PostFileMapper {
    public PostFile toDomain(PostFileEntity postFileEntity) {
        String fileCategoryString = postFileEntity.getFileCategory() != null
                ? postFileEntity.getFileCategory().toString()
                : null;

        return PostFile.of(
                postFileEntity.getId(),
                postFileEntity.getTypeName(),
                fileCategoryString,
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
                FileCategory.getEnumFileCategoryFromString(postFile.getFileCategory()),
                postFile.getUrl(),
                null,
                null
        );
    }


}
