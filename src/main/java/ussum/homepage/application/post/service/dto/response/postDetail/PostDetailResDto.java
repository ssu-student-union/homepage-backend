package ussum.homepage.application.post.service.dto.response.postDetail;


import lombok.Getter;

import java.util.List;

@Getter
public abstract class PostDetailResDto {
    protected Long postId;
    protected String category;
    protected String authorName;
    protected String title;
    protected String content;
    protected String createdAt;
    protected String lastEditedAt;
    protected Boolean isAuthor;
    protected List<String> allowedAuthorities;

    protected PostDetailResDto(Long postId, String category, String authorName, String title, String content, String createdAt, String lastEditedAt, Boolean isAuthor, List<String> allowedAuthorities) {
        this.postId = postId;
        this.category = category;
        this.authorName = authorName;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.lastEditedAt = lastEditedAt;
        this.isAuthor = isAuthor;
        this.allowedAuthorities = allowedAuthorities;
    }

    public void canAuthority(List<String> allowedAuthorities) {
        this.allowedAuthorities = allowedAuthorities;
    }
}
