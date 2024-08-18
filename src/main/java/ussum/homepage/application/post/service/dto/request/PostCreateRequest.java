package ussum.homepage.application.post.service.dto.request;

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
        List<Long> postFileList
) {
    public Post toDomain(Board board, User user, Category category, String OnGoingStatus) {
        return Post.of(
                null,
                title,
                content,
                1,
                thumbNailImage,
                "새로운",
                OnGoingStatus,
                null,
                null,
                null,
                category,
                user.getId(), //이건 채워넣어야 함, user쪽 개발되면
                board.getId()
        );
    }

}
