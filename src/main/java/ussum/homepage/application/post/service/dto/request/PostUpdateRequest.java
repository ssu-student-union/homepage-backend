package ussum.homepage.application.post.service.dto.request;

import ussum.homepage.domain.post.Board;
import ussum.homepage.domain.post.Post;
import ussum.homepage.infra.jpa.post.entity.Category;
import ussum.homepage.infra.utils.DateUtils;

import java.time.LocalDateTime;
import java.util.List;

public record PostUpdateRequest(
        String title,
        String content,
        String categoryCode,
        String thumbnailImage,
        List<Long> postFileList
) {
    public Post toDomain(Post post, Board board, Category category) {
        return Post.of(
                post.getId(),
                title,
                content,
                post.getViewCount(),
                thumbnailImage,
                post.getStatus(),
//                post.getOnGoingStatus(),
                DateUtils.parseHourMinSecFromCustomString(post.getCreatedAt()),
                DateUtils.parseHourMinSecFromCustomString(post.getUpdatedAt()),
                LocalDateTime.now(),
                category.getStringCategoryCode(),
                post.getUserId(),
                board.getId()
        );
    }

}
