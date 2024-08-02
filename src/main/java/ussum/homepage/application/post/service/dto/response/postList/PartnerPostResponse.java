package ussum.homepage.application.post.service.dto.response.postList;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import ussum.homepage.domain.post.Post;

@Getter
public class PartnerPostResponse extends PostListResDto{
    private final String thumbNail;
    private final String status;

    @Builder
    private PartnerPostResponse(String postId, String title, String content, String date, String thumbNail, String status) {
        super(postId, title, content, date);
        this.thumbNail = thumbNail;
        this.status = status;
    }

    public static PartnerPostResponse of(Post post) {
        return PartnerPostResponse.builder()
                .postId(post.getId().toString())
                .title(post.getTitle())
                .content(post.getContent())
                .date(post.getCreatedAt().toString())
                .thumbNail(post.getThumbnailImage())
                .status(post.getStatus())
                .build();
    }
}
