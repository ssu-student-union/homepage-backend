package ussum.homepage.application.post.service.dto.response.postSave;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PostFileListResponse {
    private String thumbnailUrl;
    private List<PostFileResponse> postFiles;


    public static PostFileListResponse of(String thumbnailUrl, List<PostFileResponse> postFiles) {
        return PostFileListResponse.builder()
                .thumbnailUrl(thumbnailUrl)
                .postFiles(postFiles)
                .build();
    }
}
