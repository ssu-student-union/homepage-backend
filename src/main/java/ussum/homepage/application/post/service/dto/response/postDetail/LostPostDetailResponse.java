package ussum.homepage.application.post.service.dto.response.postDetail;

import lombok.Builder;
import lombok.Getter;
import ussum.homepage.domain.post.Post;

import java.util.List;

@Getter
public class LostPostDetailResponse extends PostDetailResDto{
    private final List<String> imageList;

    @Builder
    private LostPostDetailResponse(Long postId, String categoryName, String authorName, String title, String content, String createdAt,
                                   List<String> imageList) {
        super(postId, categoryName, authorName, title, content, createdAt);
        this.imageList = imageList;
    }

    public static LostPostDetailResponse of(Post post, String authorName, String categoryName, List<String> imageList) {
        return LostPostDetailResponse.builder()
                .postId(post.getId())
                .categoryName(categoryName)
                .authorName(authorName)
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .imageList(imageList)
                .build();
    }
}
