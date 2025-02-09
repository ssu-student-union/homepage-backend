package ussum.homepage.application.post.service.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import ussum.homepage.domain.post.Board;
import ussum.homepage.domain.post.Post;
import ussum.homepage.infra.jpa.post.entity.Status;
import ussum.homepage.infra.utils.DateUtils;

import java.time.LocalDateTime;
import java.util.List;

public record PostUpdateRequest(
        String title,
        String content,
        @JsonAlias({"categoryCode", "category"})
        String categoryCode,
        Boolean isNotice,
        String thumbnailImage,
        List<Long> postFileList,
        List<RightsDetailRequest> rightsDetailList
) {
    public Post toDomain(Post post, Board board) {
        return Post.of(
                post.getId(),
                title,
                content,
                post.getViewCount(),
                thumbnailImage,
//                Status.fromEnumOrNull(isNotice?Status.EMERGENCY_NOTICE:Status.getEnumStatusFromStringStatus(post.getStatus())),
                // 삼항 연산자를 활용하여 상태 처리
                Status.fromEnumOrNull(
                        isNotice ? Status.EMERGENCY_NOTICE :
                                (Status.getEnumStatusFromStringStatus(post.getStatus()).equals(Status.EMERGENCY_NOTICE) ? Status.GENERAL : Status.getEnumStatusFromStringStatus(post.getStatus()))
                ),
                DateUtils.parseHourMinSecFromCustomString(post.getCreatedAt()),
                DateUtils.parseHourMinSecFromCustomString(post.getUpdatedAt()),
                LocalDateTime.now(),
                categoryCode,
                post.getSuggestionTarget(),
                post.getQnaMajorCode(),
                post.getQnaMemberCode(),
                post.getUserId(),
                board.getId()
        );
    }
    public Post toDataDomain(Post post) {
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
                categoryCode,
                post.getSuggestionTarget(),
                post.getQnaMajorCode(),
                post.getQnaMemberCode(),
                post.getUserId(),
                6L
        );
    }
}
