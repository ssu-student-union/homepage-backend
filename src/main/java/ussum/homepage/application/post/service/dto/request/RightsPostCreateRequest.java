package ussum.homepage.application.post.service.dto.request;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import ussum.homepage.domain.post.Board;
import ussum.homepage.domain.post.Post;

@Getter
public class RightsPostCreateRequest extends PostCreateRequest {
    private final List<RightsDetailRequest> relatedPeople;

    @Builder
    public RightsPostCreateRequest(String title, String content, String categoryCode,String thumbNailImage, boolean isNotice,
                                   List<Long> postFileList, List<RightsDetailRequest> relatedPeople) {
        super(title, content, categoryCode, thumbNailImage, isNotice, postFileList);
        this.relatedPeople = relatedPeople;
    }

    @Override
    public Post toDomain(Board board, Long userId) {

        return Post.of(null,
                title,
                content,
                1,
                thumbNailImage,
                "새로운",
                null, null, null,
                categoryCode,
                userId,
                board.getId());
    }

    private boolean validation(String boardCod){
        return true;
    }
}

