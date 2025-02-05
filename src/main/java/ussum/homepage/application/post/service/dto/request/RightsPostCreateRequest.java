package ussum.homepage.application.post.service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import ussum.homepage.domain.post.Board;
import ussum.homepage.domain.post.Post;

@Schema(description = "인권신고게시판 데이터 스키마")
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
                "접수대기",
                null,
                null,
                null,
                userId,
                board.getId());
    }

    private boolean validation(String boardCod){
        return true;
    }
}

