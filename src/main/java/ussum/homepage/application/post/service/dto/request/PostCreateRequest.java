package ussum.homepage.application.post.service.dto.request;

import lombok.Builder;
import ussum.homepage.domain.post.Board;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.user.User;
import ussum.homepage.infra.jpa.post.entity.Category;

import java.util.List;

public record PostCreateRequest(
        String title,
        String content,
        String categoryCode,
        String thumbNailImage,
        boolean isNotice,
        List<Long> postFileList
) {
    public Post toDomain(Board board, Long userId) {
        String status = "새로운";
        if (isNotice) {
            status = "긴급공지";
        }
        return Post.of(
                null,
                title,
                content,
                1,
                thumbNailImage,
                status,
//                OnGoingStatus,
                null,
                null,
                null,
                categoryCode,
                userId,
                board.getId()
        );
    }

    public Post toDomain(Long boardId, Long userId, Category category) {
//        String status = "새로운";
//        if (isNotice) {
//            status = "긴급";
//        }
        return Post.of(
                null,
                title,
                content,
                1,
                thumbNailImage,
                "새로운",
//                OnGoingStatus,
                null,
                null,
                null,
                category.getStringCategoryCode(),
                userId,
                boardId
        );
    }

}
