package ussum.homepage.application.post.service.dto.response.postDetail;

import lombok.Builder;
import lombok.Getter;
import ussum.homepage.domain.post.Post;

@Getter
public class PetitionPostDetailResponse extends PostDetailResDto {
    private final Integer likeCount;
    private final String status;

    @Builder
    private PetitionPostDetailResponse(Long postId, String categoryName, String authorName, String title, String content, String createdAt, Boolean isAuthor,
                                       Integer likeCount, String status) {
        super(postId, categoryName, authorName, title, content, createdAt, isAuthor);
        this.likeCount = likeCount;
        this.status = status;
    }

    public static PetitionPostDetailResponse of(Post post, Boolean isAuthor, String authorName, Integer likeCount, String petitionStatus) {
        return PetitionPostDetailResponse.builder()
                .postId(post.getId())
                .categoryName(petitionStatus)
                .authorName(authorName)
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .isAuthor(isAuthor)
                .likeCount(likeCount)
                .status(petitionStatus)
                .build();
    }

}
