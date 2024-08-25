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
    @Builder
    public PostCreateRequest {
    }

    public PostCreateRequest(String title, String content, String categoryCode, String thumbNailImage, List<Long> postFileList) {
        this(title, content, categoryCode, thumbNailImage, false, postFileList);
    }

    public Post toDomain(Board board, Long userId, Category category) {
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
                userId, //이건 채워넣어야 함, user쪽 개발되면
                board.getId()
        );
    }

    public Post toDomain(Long boardId, Long userId, Category category) {
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
                userId, //이건 채워넣어야 함, user쪽 개발되면
                boardId
        );
    }

}
