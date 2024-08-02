package ussum.homepage.application.post.service.dto.request;

import ussum.homepage.domain.post.Board;
import ussum.homepage.domain.post.Category;
import ussum.homepage.domain.post.Post;

import java.time.LocalDateTime;

public record PostUpdateRequest(
        String title,
        String content,
        String categoryCode,
        String thumbnailImage
) {
    public Post toDomain(Post post, Board board, Category category) {
        return Post.of(
                post.getId(),
                title,
                content,
                post.getViewCount(),
                thumbnailImage,
                post.getStatus(),
                LocalDateTime.parse(post.getCreatedAt()),
                LocalDateTime.parse(post.getUpdatedAt()),
                LocalDateTime.now(),
                post.getUserId(),
                board.getId(),
                category.getId()
        );
    }

}
