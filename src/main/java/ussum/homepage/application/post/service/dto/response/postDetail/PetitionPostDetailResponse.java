package ussum.homepage.application.post.service.dto.response.postDetail;

import lombok.Builder;
import lombok.Getter;
import ussum.homepage.domain.post.Post;

@Getter
public class PetitionPostDetailResponse extends PostDetailResDto {
    private final Integer likeCount;
    private final String status;

    @Builder
    private PetitionPostDetailResponse(Long postId, String categoryName, String authorName, String title, String content, String createdAt,
                                       Integer likeCount, String status) {
        super(postId, categoryName, authorName, title, content, createdAt);
        this.likeCount = likeCount;
        this.status = status;
    }

    public static PetitionPostDetailResponse of(Post post, String authorName, Integer likeCount, String petitionStatus) {
        return PetitionPostDetailResponse.builder()
                .postId(post.getId())
                .categoryName(petitionStatus)
                .authorName(authorName)
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .likeCount(likeCount)
                .status(petitionStatus)
                .build();
    }

}
