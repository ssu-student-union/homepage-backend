package ussum.homepage.application.post.service.dto.response.postDetail;

import lombok.Builder;
import lombok.Getter;
import ussum.homepage.domain.post.Post;
import ussum.homepage.infra.jpa.post.entity.OngoingStatus;

import java.util.List;

@Getter
public class PetitionPostDetailResponse extends PostDetailResDto {
    private final Integer likeCount;
    private final String onGoingStatus;
    private final List<String> imageList;

    @Builder
    private PetitionPostDetailResponse(Long postId, String categoryName, String authorName, String title, String content, String createdAt, Boolean isAuthor,
                                       Integer likeCount, String onGoingStatus, List<String> imageList) {
        super(postId, categoryName, authorName, title, content, createdAt, isAuthor);
        this.likeCount = likeCount;
        this.onGoingStatus = onGoingStatus;
        this.imageList = imageList;
    }

    public static PetitionPostDetailResponse of(Post post, Boolean isAuthor, String authorName, Integer likeCount, String onGoingStatus, List<String> imageList) {
        return PetitionPostDetailResponse.builder()
                .postId(post.getId())
                .categoryName(OngoingStatus.toKorean(onGoingStatus))
                .authorName(authorName)
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .isAuthor(isAuthor)
                .likeCount(likeCount)
                .onGoingStatus(onGoingStatus)
                .imageList(imageList)
                .build();
    }

}
