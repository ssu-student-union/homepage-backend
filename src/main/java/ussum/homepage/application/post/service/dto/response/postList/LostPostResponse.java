package ussum.homepage.application.post.service.dto.response.postList;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import ussum.homepage.domain.post.Post;


@Getter
public class LostPostResponse extends PostListResDto{
    private final int lostId;
    private final String thumbNail;


    @Builder

    private LostPostResponse(Long postId, String title, String date, String thumbNail, int lostId) {
        super(postId, title, date);
        this.lostId = lostId;
        this.thumbNail = thumbNail;
    }

    public static LostPostResponse of(Post post) {
        return LostPostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
//                .content(post.getContent())
                .date(post.getCreatedAt().toString())
                .thumbNail(post.getThumbnailImage())
                .lostId(Math.toIntExact(post.getId()))
                .build();
    }
}
