package ussum.homepage.application.post.service.dto.response.postList;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import ussum.homepage.domain.post.Post;

@Getter
public class NoticePostResponse extends PostListResDto{
    private final String thumbNail;
    private final String status;

    @Builder
    private NoticePostResponse(Long postId, String title, String content, String date, String thumbNail, String status) {
        super(postId, title, content, date);
        this.thumbNail = thumbNail;
        this.status = status;
    }

    public static NoticePostResponse of(Post post) {
        return NoticePostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .date(post.getCreatedAt().toString())
                .thumbNail(post.getThumbnailImage())
                .status(post.getStatus())
                .build();
    }
}
