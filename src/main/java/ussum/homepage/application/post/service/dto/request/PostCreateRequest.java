package ussum.homepage.application.post.service.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.List;
import lombok.Getter;
import ussum.homepage.domain.post.Board;
import ussum.homepage.domain.post.Post;

@Getter
//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "boardCode",defaultImpl = GeneralPostCreateRequest.class)
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = RightsPostCreateRequest.class, name = "인권신고게시판")
//})
public abstract class PostCreateRequest {
    protected String title;
    protected String content;
    protected String thumbNailImage;
    protected boolean isNotice;
    protected List<Long> postFileList;

    public PostCreateRequest(String title, String content, String thumbNailImage, boolean isNotice, List<Long> postFileList) {
        this.title = title;
        this.content = content;
        this.thumbNailImage = thumbNailImage;
        this.isNotice = isNotice;
        this.postFileList = postFileList;
    }

    public abstract Post toDomain(Board board, Long userId);
}
