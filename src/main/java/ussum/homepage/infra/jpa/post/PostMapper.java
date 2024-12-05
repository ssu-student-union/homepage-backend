package ussum.homepage.infra.jpa.post;

import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;
import ussum.homepage.application.post.service.dto.response.SimplePostResponse;
import ussum.homepage.application.post.service.dto.response.TopLikedPostListResponse;
import ussum.homepage.application.post.service.dto.response.postList.PostListRes;
import ussum.homepage.application.post.service.dto.response.postList.PostListResDto;
import ussum.homepage.domain.post.Post;
import ussum.homepage.global.common.PageInfo;
import ussum.homepage.infra.jpa.post.entity.*;
import ussum.homepage.infra.jpa.user.entity.UserEntity;
import ussum.homepage.infra.utils.DateUtils;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class PostMapper {
    public Post toDomain(PostEntity postEntity){
//        String onGoingStatus = OngoingStatus.fromEnumOrNull(postEntity.getOngoingStatus());
        String category = Category.fromEnumOrNull(postEntity.getCategory());
        String suggestionTarget = postEntity.getSuggestionTarget() != null  ?
                SuggestionTarget.getTargetStringFromSuggestionTarget(postEntity.getSuggestionTarget()) : null;

        return Post.of(
                postEntity.getId(),
                postEntity.getTitle(),
                postEntity.getContent(),
                postEntity.getViewCount(),
                postEntity.getThumbnailImage(),
                postEntity.getStatus().getStringStatus(),
//                onGoingStatus
                postEntity.getCreatedAt(),
                postEntity.getUpdatedAt(),
                postEntity.getLastEditedAt(),
                category,
                suggestionTarget,
                postEntity.getUserEntity().getId(),
                postEntity.getBoardEntity().getId()
        );
    }

    public PostEntity toEntity(Post post) {
        LocalDateTime lastEditedAt = DateUtils.parseHourMinSecFromCustomString(post.getLastEditedAt());
//        OngoingStatus ongoingStatus = OngoingStatus.fromStringOrNull(post.getOnGoingStatus());

        return PostEntity.of(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getViewCount(),
                post.getThumbnailImage(),
                Status.getEnumStatusFromStringStatus(post.getStatus()),
//                ongoingStatus,
                lastEditedAt,
                Category.fromStringOrNull(post.getCategory()),
                SuggestionTarget.fromStringOrNull(post.getSuggestionTarget()),
                UserEntity.from(post.getUserId()),
                BoardEntity.from(post.getBoardId())
        );
    }

    public TopLikedPostListResponse toTopLikedPostListResponse(List<SimplePostResponse> postResponseList, PageInfo pageInfo){
        return TopLikedPostListResponse.of(postResponseList, pageInfo);
    }

    public Page<PostListResDto> toPage(List<PostListResDto> postListRes, Pageable pageable){
        long totalElements = postListRes.size();
        // PageImpl을 사용하여 List<? extends PostListRes>를 Page<PostListRes>로 변환
        return new PageImpl<>(postListRes, pageable, totalElements);
    }

}
