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
                postFileEntity.getFileName(),
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
        FileCategory fileCategory = postFile.getFileCategory() != null
                ? FileCategory.getEnumFileCategoryFromString(postFile.getFileCategory())
                : null;

        return PostFileEntity.of(
                postFile.getId(),
                postFile.getFileName(),
                postFile.getTypeName(),
                fileCategory,
                postFile.getUrl(),
                null,
                null
        );
    }


}
