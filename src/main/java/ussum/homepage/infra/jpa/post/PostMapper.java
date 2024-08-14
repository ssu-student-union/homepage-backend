package ussum.homepage.infra.jpa.post;

import org.springframework.stereotype.Component;
import ussum.homepage.application.post.service.dto.response.SimplePostResponse;
import ussum.homepage.application.post.service.dto.response.TopLikedPostListResponse;
import ussum.homepage.domain.post.Board;
import ussum.homepage.domain.post.Category;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.user.User;
import ussum.homepage.global.common.PageInfo;
import ussum.homepage.infra.jpa.post.entity.*;
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
                postEntity.getStatus().getStringStatus(),
                postEntity.getOngoingStatus().getStringOnGoingStatus(),
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
                Status.getEnumStatusFromStringStatus(post.getStatus()),
                OngoingStatus.getEnumOngoingStatusFromStringOngoingStatus(post.getOnGoingStatus()),
                lastEditedAt,
                user,
                board,
                category
        );
    }

    public TopLikedPostListResponse toTopLikedPostListResponse(List<SimplePostResponse> postResponseList, PageInfo pageInfo){
        return TopLikedPostListResponse.of(postResponseList, pageInfo);
    }

}
