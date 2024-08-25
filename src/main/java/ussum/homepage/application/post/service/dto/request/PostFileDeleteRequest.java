package ussum.homepage.application.post.service.dto.request;

import java.util.List;

public record PostFileDeleteRequest(
        List<String> fileUrls
) {
}
