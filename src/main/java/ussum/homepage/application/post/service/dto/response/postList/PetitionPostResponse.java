package ussum.homepage.application.post.service.dto.response.postList;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import ussum.homepage.domain.post.Post;


@Getter
public class PetitionPostResponse extends PostListResDto{
    private final Integer likeCount;
    private final String status;

    @Builder
    private PetitionPostResponse(Long postId, String title, String date, Integer likeCount, String status) {
        super(postId, title, date);
        this.likeCount = likeCount;
        this.status = status;
    }

    public static PetitionPostResponse of(Post post, Integer likeCount) {
        return PetitionPostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
//                .content(post.getContent())
                .date(post.getCreatedAt().toString())
                .likeCount(likeCount)
                .status(post.getStatus())
                .build();
    }
}
