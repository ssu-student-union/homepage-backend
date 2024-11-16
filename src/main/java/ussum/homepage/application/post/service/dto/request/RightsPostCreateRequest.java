package ussum.homepage.application.post.service.dto.request;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import ussum.homepage.domain.post.Board;
import ussum.homepage.domain.post.Post;

@Getter
public class RightsPostCreateRequest extends PostCreateRequest {
    private final List<RightsDetailRequest> rightsDetailList;

    @Builder
    public RightsPostCreateRequest(String title, String content, String category,String thumbNailImage, boolean isNotice,
                                   List<Long> postFileList, List<RightsDetailRequest> rightsDetailList) {
        super(title, content, category, thumbNailImage, isNotice, postFileList);
        this.rightsDetailList = rightsDetailList;
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
                category,
                userId,
                board.getId());
    }

    private boolean validation(String boardCod){
        return true;
    }
}

