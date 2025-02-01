package ussum.homepage.application.post.service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import ussum.homepage.domain.post.Board;
import ussum.homepage.domain.post.Post;

@Schema(description = "질의응답게시판 데이터 스키마")
@Getter
public class QnAPostCreateRequest extends PostCreateRequest {
    private final String qnaTarget;

    @Builder
    public QnAPostCreateRequest(String title, String content, String category, String thumbNailImage,
                                       boolean isNotice, List<Long> postFileList, String qnaTarget) {
        super(title, content, category, thumbNailImage, isNotice, postFileList);
        this.qnaTarget = qnaTarget;
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
                null,
                qnaTarget,
                userId,
                board.getId());
    }
}
