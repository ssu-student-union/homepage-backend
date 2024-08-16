package ussum.homepage.application.post.service.dto.response.postList;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.user.User;

@Getter
public class NoticePostResponse extends PostListResDto{
    private final String thumbNail;
    private final String status;
    private final String author;

    @Builder
    private NoticePostResponse(String postId, String title, String content, String date, String thumbNail, String status, String author) {
        super(postId, title, content, date);
        this.thumbNail = thumbNail;
        this.status = status;
        this.author = author;
    }

    public static NoticePostResponse of(Post post, User user) {
        return NoticePostResponse.builder()
                .postId(post.getId().toString())
                .title(post.getTitle())
                .content(post.getContent())
                .date(post.getCreatedAt().toString())
                .thumbNail(post.getThumbnailImage())
                .status(post.getStatus())
                .author(user.getName())
                .build();
    }
}
