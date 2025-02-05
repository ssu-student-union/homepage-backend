package ussum.homepage.application.post.service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import ussum.homepage.domain.post.Board;
import ussum.homepage.domain.post.Post;

@Schema(description = "건의게시판 데이터 스키마")
@Getter
public class SuggestionPostCreateRequest extends PostCreateRequest {
    private final String suggestionTarget;

    @Builder
    public SuggestionPostCreateRequest(String title, String content, String category, String thumbNailImage,
                                       boolean isNotice, List<Long> postFileList, String suggestionTarget) {
        super(title, content, category, thumbNailImage, isNotice, postFileList);
        this.suggestionTarget = suggestionTarget;
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
                suggestionTarget,
                null,
                null,
                userId,
                board.getId());
    }
}
