package ussum.homepage.application.post.service.dto.response.postList;

import lombok.Getter;

@Getter
public abstract class PostListResDto {
    protected Long postId;
    protected String title;
    protected String content;
    protected String date;

    protected PostListResDto(Long postId, String title, String content, String date) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.date = date;
    }
}