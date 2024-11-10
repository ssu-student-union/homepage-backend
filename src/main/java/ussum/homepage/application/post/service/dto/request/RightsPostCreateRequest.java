package ussum.homepage.application.post.service.dto.request;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RightsPostCreateRequest extends PostCreateRequest {
    private final List<RightsDetailRequest> relatedPeople;

    @Builder
    public RightsPostCreateRequest(String title, String content, String thumbNailImage, boolean isNotice,
                                   List<Long> postFileList, List<RightsDetailRequest> relatedPeople) {
        super(title, content, thumbNailImage, isNotice, postFileList);
        this.relatedPeople = relatedPeople;
    }
}
