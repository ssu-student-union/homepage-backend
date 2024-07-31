package ussum.homepage.infra.jpa.post;

import org.springframework.stereotype.Component;
import ussum.homepage.domain.post.Board;
import ussum.homepage.domain.post.Category;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.user.User;
import ussum.homepage.infra.jpa.post.entity.BoardCode;
import ussum.homepage.infra.jpa.post.entity.BoardEntity;
import ussum.homepage.infra.jpa.post.entity.CategoryEntity;
import ussum.homepage.infra.jpa.post.entity.PostEntity;
import ussum.homepage.infra.jpa.user.entity.UserEntity;

import java.time.LocalDateTime;

@Component
public class PostMapper {
    public Post toDomain(PostEntity postEntity){
        return Post.of(
                postEntity.getId(),
                postEntity.getTitle(),
                postEntity.getContent(),
                postEntity.getViewCount(),
                postEntity.getThumbnailImage(),
                postEntity.getCreatedAt(),
                postEntity.getUpdatedAt(),
                postEntity.getLastEditedAt(),
                postEntity.getUserEntity().getId(),
                postEntity.getBoardEntity().getId(),
                postEntity.getCategoryEntity().getId()
        );
    }

    public PostEntity toEntity(Post post, UserEntity user, BoardEntity board, CategoryEntity category) {
        LocalDateTime lastEditedAt = null;
//        LocalDateTime deletedAt = null;

        if (post.getLastEditedAt() != null && !"null".equals(post.getLastEditedAt())) {
            lastEditedAt = LocalDateTime.parse(post.getLastEditedAt());
        }
//        if (post.getDeletedAt() != null && !"null".equals(post.getDeletedAt())) {
//            deletedAt = LocalDateTime.parse(post.getDeletedAt());
//        }

        return PostEntity.of(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getViewCount(),
                post.getThumbnailImage(),
                lastEditedAt,
                user,
                board,
                category
        );
    }

}
