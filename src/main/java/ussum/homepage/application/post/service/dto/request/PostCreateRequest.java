package ussum.homepage.application.post.service.dto.request;

import lombok.Getter;

@Getter
public abstract class PostCreateRequest {
    protected String title;
    protected String content;
    protected String thumbNailImage;
    protected boolean isNotice;

    public PostCreateRequest(String title, String content,  String thumbNailImage, boolean isNotice) {
        this.title = title;
        this.content = content;
        this.thumbNailImage = thumbNailImage;
        this.isNotice = isNotice;
    }
}
