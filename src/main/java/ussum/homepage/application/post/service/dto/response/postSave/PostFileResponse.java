package ussum.homepage.application.post.service.dto.response.postSave;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostFileResponse {
    private Long id;
    private String url;
    private String originalFileName;

    public static PostFileResponse of(Long id, String url, String originalFileName){
        return PostFileResponse.builder()
                .id(id)
                .url(url)
                .originalFileName(originalFileName)
                .build();
    }
}
