package ussum.homepage.application.post.service.dto.response.postList;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.user.User;
import ussum.homepage.infra.jpa.group.entity.GroupCode;
import ussum.homepage.infra.jpa.post.entity.Category;
import ussum.homepage.infra.jpa.post.entity.OngoingStatus;
import ussum.homepage.infra.jpa.post.entity.Status;

@Getter
public class NoticePostResponse extends PostListResDto{
    private final String thumbNail;
    private final String status;
    private final String author;
    private final Boolean isEmergency;

    @Builder
    private NoticePostResponse(Long postId, String title, String date, String thumbNail, String status, String author, Boolean isEmergency) {
        super(postId, title, date);
        this.thumbNail = thumbNail;
        this.status = status;
        this.author = author;
        this.isEmergency = isEmergency;
    }

    public static NoticePostResponse of(Post post, User user) {
        OngoingStatus ongoingStatus = StringUtils.hasText(post.getOnGoingStatus()) ? OngoingStatus.getEnumOngoingStatusFromStringOngoingStatus(post.getOnGoingStatus()) : null;

        return NoticePostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
//                .content(post.getContent())
                .date(post.getCreatedAt())
                .thumbNail(post.getThumbnailImage())
                .status(post.getStatus())
                .author(user.getName())
                .isEmergency(ongoingStatus!=null&& ongoingStatus.equals(Category.EMERGENCY)? true : false)
                .build();
    }
}
