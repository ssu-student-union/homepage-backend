package ussum.homepage.application.post.service.dto.request;

import java.util.List;
import lombok.Getter;
import ussum.homepage.domain.post.Board;
import ussum.homepage.domain.post.Post;

@Getter
public class PostCreateRequest {
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

    public Post toDomain(Board board, Long userId){
        String status = "새로운";
        if(isNotice){
            status = "긴급공지";
        }
        return Post.of(null,
                title,
                content,
                1,
                thumbNailImage,
                status,
                null,null,null,
                null,
                userId,
                board.getId());
    }
}
