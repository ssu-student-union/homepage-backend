package ussum.homepage.application.post.service.dto.request;

import java.util.List;
import lombok.Getter;
import ussum.homepage.domain.post.Board;
import ussum.homepage.domain.post.Post;

@Getter
public abstract class PostCreateRequest {
    protected String title;
    protected String content;
    protected String category;
    protected String thumbNailImage;
    protected boolean isNotice;
    protected List<Long> postFileList;

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
