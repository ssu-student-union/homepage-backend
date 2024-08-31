package ussum.homepage.application.post.service.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import ussum.homepage.domain.post.PostFile;

@Builder(access = AccessLevel.PRIVATE)
public record FileResponse(
        String fileName,
        String fileUrl,
        String fileType
) {
    public static FileResponse of(PostFile postFile){
        return FileResponse.builder()
                .fileName(postFile.getFileName())
                .fileUrl(postFile.getUrl())
                .fileType(postFile.getTypeName())
                .build();
    }
}
