package ussum.homepage.application.post.service.dto.response.postDetail;


import lombok.Getter;

import java.util.List;

@Getter
public abstract class PostDetailResDto {
    protected Long postId;
    protected String categoryName;
    protected String authorName;
    protected String title;
    protected String content;
    protected String createdAt;

    protected PostDetailResDto(Long postId, String categoryName, String authorName, String title, String content, String createdAt) {
        this.postId = postId;
        this.categoryName = categoryName;
        this.authorName = authorName;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }
}
