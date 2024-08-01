package ussum.homepage.infra.jpa.post;

import org.springframework.stereotype.Component;
import ussum.homepage.application.post.service.dto.response.SimplePostResponse;
import ussum.homepage.application.post.service.dto.response.TopLikedPostListResponse;
import ussum.homepage.domain.post.Board;
import ussum.homepage.domain.post.Category;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.user.User;
import ussum.homepage.global.common.PageInfo;
import ussum.homepage.infra.jpa.post.entity.BoardCode;
import ussum.homepage.infra.jpa.post.entity.BoardEntity;
import ussum.homepage.infra.jpa.post.entity.CategoryEntity;
import ussum.homepage.infra.jpa.post.entity.PostEntity;
import ussum.homepage.infra.jpa.user.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.List;

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
                postEntity.getDeletedAt(),
                postEntity.getUserEntity().getId(),
                postEntity.getBoardEntity().getId(),
                postEntity.getCategoryEntity().getId()
        );
    }

    public PostEntity toEntity(Post post, UserEntity user, BoardEntity board, CategoryEntity category) {
//        PostEntity from = PostEntity.from(post.getId());
//        LocalDateTime lastEditedAt = post.getLastEditedAt() != null ? LocalDateTime.parse(post.getLastEditedAt()) : null;
//        LocalDateTime deletedAt = post.getDeletedAt() != null ? LocalDateTime.parse(post.getDeletedAt()) : null;
        LocalDateTime lastEditedAt = null;
        LocalDateTime deletedAt = null;

        if (post.getLastEditedAt() != null && !"null".equals(post.getLastEditedAt())) {
            lastEditedAt = LocalDateTime.parse(post.getLastEditedAt());
        }

        if (post.getDeletedAt() != null && !"null".equals(post.getDeletedAt())) {
            deletedAt = LocalDateTime.parse(post.getDeletedAt());
        }

        return PostEntity.of(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getViewCount(),
                post.getThumbnailImage(),
                lastEditedAt,
                deletedAt,
                user,
                board,
                category
        );
    }

    public TopLikedPostListResponse toTopLikedPostListResponse(List<SimplePostResponse> postResponseList, PageInfo pageInfo){
        return TopLikedPostListResponse.of(postResponseList, pageInfo);
    }

}
