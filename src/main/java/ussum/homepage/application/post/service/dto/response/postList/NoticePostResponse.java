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
    private NoticePostResponse(Long postId, String title, String content, String date, String category, String thumbNail, String status, String author, Boolean isEmergency) {
        super(postId, title, content, date, category);
        this.thumbNail = thumbNail;
        this.status = status;
        this.author = author;
        this.isEmergency = isEmergency;
    }

    public static NoticePostResponse of(Post post, User user) {
        Category category = StringUtils.hasText(post.getCategory()) ? Category.getEnumCategoryCodeFromStringCategoryCode(post.getCategory()) : null;

        return NoticePostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .date(post.getCreatedAt())
                .category(post.getCategory())
                .thumbNail(post.getThumbnailImage())
                .status(post.getStatus())
                .author(user.getName())
                .isEmergency(category!=null&& category.equals(Category.EMERGENCY)? true : false)
                .build();
    }
}
