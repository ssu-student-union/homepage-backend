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
    private PetitionPostResponse(String postId, String title, String content, String date, Integer likeCount, String status) {
        super(postId, title, content, date);
        this.likeCount = likeCount;
        this.status = status;
    }

    public static PetitionPostResponse of(Post post, Integer likeCount) {
        return PetitionPostResponse.builder()
                .postId(post.getId().toString())
                .title(post.getTitle())
                .content(post.getContent())
                .date(post.getCreatedAt().toString())
                .likeCount(likeCount)
                .status(post.getStatus())
                .build();
    }
}
