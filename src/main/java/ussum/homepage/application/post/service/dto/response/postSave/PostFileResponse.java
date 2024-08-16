package ussum.homepage.application.post.service.dto.response.postSave;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostFileResponse {
    private Long id;
    private String url;

    public static PostFileResponse of(Long id, String url){
        return PostFileResponse.builder()
                .id(id)
                .url(url)
                .build();
    }
}
