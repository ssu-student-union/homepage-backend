package ussum.homepage.application.post.service.dto.request;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import ussum.homepage.domain.post.Board;
import ussum.homepage.domain.post.Post;

@Getter
public class RightsPostCreateRequest extends PostCreateRequest {

    private final List<RightsDetailRequest> relatedPeople;
    private final String categoryCode;

    @Builder
    public RightsPostCreateRequest(String title, String content, String thumbNailImage, String categoryCode, boolean isNotice,
                                   List<Long> postFileList, List<RightsDetailRequest> relatedPeople) {
        super(title, content, thumbNailImage, isNotice, postFileList);
        this.relatedPeople = relatedPeople;
        this.postFileList = postFileList;
        this.categoryCode = categoryCode;
    }

    @Override
    public Post toDomain(Board board, Long userId) {

        return Post.of(null,
                title,
                content,
                null,
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

