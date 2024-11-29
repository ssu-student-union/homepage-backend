package ussum.homepage.application.image.controller.dto.response;

import ussum.homepage.application.image.service.dto.PreSignedUrlInfo;

import java.util.List;
import java.util.Map;

public record PreSignedUrlResponse(
        List<PreSignedUrlInfo> preSignedUrls
) {
    public static PreSignedUrlResponse of(List<PreSignedUrlInfo> preSignedUrls) {
        return new PreSignedUrlResponse(preSignedUrls);
    }
}
