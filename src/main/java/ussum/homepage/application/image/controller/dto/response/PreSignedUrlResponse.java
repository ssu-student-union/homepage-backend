package ussum.homepage.application.image.controller.dto.response;

import java.util.List;
import java.util.Map;

public record PreSignedUrlResponse(
        List<Map<String, String>> preSignedUrls,
        List<String> originalFileNames
) {
    public static PreSignedUrlResponse of(List<Map<String, String>> preSignedUrls, List<String> originalFileNames) {
        return new PreSignedUrlResponse(preSignedUrls, originalFileNames);
    }
}
