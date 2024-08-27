package ussum.homepage.application.post.service.dto.response.postSave;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record PostFileMediatorResponse(
        List<String> originalFileNames,
        List<Map<String, String>> urlList
) {
    public static PostFileMediatorResponse of(List<String> originalFileNames, List<Map<String, String>> urlList){
        return PostFileMediatorResponse.builder()
                .originalFileNames(originalFileNames)
                .urlList(urlList)
                .build();
    }
}
