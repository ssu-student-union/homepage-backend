package ussum.homepage.application.post.service.dto.request;

import java.util.List;
import lombok.Getter;

@Getter
public class RigthPostCreateRequest extends PostCreateRequest {
    private final List<RelatedPeople> relatedPeople;

    public RigthPostCreateRequest(String title, String content, String thumbNailImage, boolean isNotice,
                                  List<Long> postFileList) {
        super(title, content, thumbNailImage, isNotice, postFileList);
    }
}
