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
    private final String qnaMajorCode;
    private final String qnaMemberCode;

    @Builder
    public QnAPostCreateRequest(String title, String content, String category, String thumbNailImage,
                                       boolean isNotice, List<Long> postFileList, String qnaMajorCode, String qnaMemberCode) {
        super(title, content, category, thumbNailImage, isNotice, postFileList);
        this.qnaMajorCode = qnaMajorCode;
        this.qnaMemberCode = qnaMemberCode;
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
                qnaMajorCode,
                qnaMemberCode,
                userId,
                board.getId());
    }
}
