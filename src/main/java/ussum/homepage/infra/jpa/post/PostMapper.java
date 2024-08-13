package ussum.homepage.infra.jpa.post;

import org.springframework.stereotype.Component;
import ussum.homepage.application.post.service.dto.response.SimplePostResponse;
import ussum.homepage.application.post.service.dto.response.TopLikedPostListResponse;
import ussum.homepage.domain.post.Post;
import ussum.homepage.global.common.PageInfo;
import ussum.homepage.infra.jpa.post.entity.*;
import ussum.homepage.infra.jpa.user.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class PostMapper {
    public Post toDomain(PostEntity postEntity){
        String onGoingStatus = Optional.ofNullable(postEntity.getOngoingStatus())
                .map(OngoingStatus::getStringOnGoingStatus)
                .orElse(null);

        return Post.of(
                postEntity.getId(),
                postEntity.getTitle(),
                postEntity.getContent(),
                postEntity.getViewCount(),
                postEntity.getThumbnailImage(),
                postEntity.getStatus().getStringStatus(),
                onGoingStatus,
                postEntity.getCreatedAt(),
                postEntity.getUpdatedAt(),
                postEntity.getLastEditedAt(),
                postEntity.getUserEntity().getId(),
                postEntity.getBoardEntity().getId(),
                postEntity.getCategoryEntity().getId()
        );
    }

    public PostEntity toEntity(Post post, UserEntity user, BoardEntity board, CategoryEntity category) {
        LocalDateTime lastEditedAt = Optional.ofNullable(post.getLastEditedAt())
                .filter(date -> !"null".equals(date))
                .map(LocalDateTime::parse)
                .orElse(null);

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
