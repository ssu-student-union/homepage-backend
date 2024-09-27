package ussum.homepage.application.post.service.dto.response.postList;

import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.user.User;
import ussum.homepage.infra.jpa.post.entity.Category;

@Getter
public class NoticePostResponse extends PostListResDto{
    private final String thumbNail;
    private final String status;
    private final String author;

    @Builder
    private NoticePostResponse(Long postId, String title, String content, String date, String category, String thumbNail, String status, String author) {
        super(postId, title, content, date, category);
        this.thumbNail = thumbNail;
        this.status = status;
        this.author = author;
    }

    public static NoticePostResponse of(Post post, User user) {
        return NoticePostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .date(post.getCreatedAt())
                .category(post.getCategory())
                .thumbNail(post.getThumbnailImage())
                .status(post.getStatus())
                .author(user.getName())
                .build();
    }
}
