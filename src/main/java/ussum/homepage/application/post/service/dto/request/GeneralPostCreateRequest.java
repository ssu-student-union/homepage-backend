package ussum.homepage.application.post.service.dto.request;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import ussum.homepage.domain.post.Board;
import ussum.homepage.domain.post.Post;
import ussum.homepage.infra.jpa.post.entity.Category;

@Getter
public class GeneralPostCreateRequest extends PostCreateRequest {

    @Builder
    public GeneralPostCreateRequest(String title, String content, String category, String thumbNailImage, boolean isNotice, List<Long> postFileList) {
        super(title, content,category, thumbNailImage, isNotice,postFileList);
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
                category,
                null,
                null,
                userId,
                board.getId());
    }
    public Post toDomain(Long boardId, Long userId, Category category){
        return Post.of(null,
                title,
                content,
                1,
                thumbNailImage,
                "새로운",
                null,null,null,
                this.category,
                null,
                null,
                userId,
                boardId);
    }

}
