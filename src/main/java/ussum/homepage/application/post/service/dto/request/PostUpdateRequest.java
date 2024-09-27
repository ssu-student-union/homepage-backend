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
        Boolean isNotice,
        String thumbnailImage,
        List<Long> postFileList
) {
    public Post toDomain(Post post, Board board) {
        return Post.of(
                post.getId(),
                title,
                content,
                post.getViewCount(),
                thumbnailImage,
                post.getStatus(),
                DateUtils.parseHourMinSecFromCustomString(post.getCreatedAt()),
                DateUtils.parseHourMinSecFromCustomString(post.getUpdatedAt()),
                LocalDateTime.now(),
                Category.fromEnumOrNull(isNotice?Category.EMERGENCY:null),
                post.getUserId(),
                board.getId()
        );
    }
}
