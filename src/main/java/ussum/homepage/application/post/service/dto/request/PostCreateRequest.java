package ussum.homepage.application.post.service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;
import ussum.homepage.domain.post.Board;
import ussum.homepage.domain.post.Post;

@Schema(description = "게시글 생성 요청 기본 DTO")
@Getter
public abstract class PostCreateRequest {
    protected String title;
    protected String content;
    protected String category;
    protected String thumbNailImage;
    protected boolean isNotice;
    protected List<Long> postFileList;

    protected PostCreateRequest() {
        // 빈 생성자
    }

    public PostCreateRequest(String title, String content, String category, String thumbNailImage, boolean isNotice, List<Long> postFileList) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.thumbNailImage = thumbNailImage;
        this.isNotice = isNotice;
        this.postFileList = postFileList;
    }

    public abstract Post toDomain(Board board, Long userId);
}
