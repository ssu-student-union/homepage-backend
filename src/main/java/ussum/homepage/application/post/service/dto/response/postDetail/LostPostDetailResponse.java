package ussum.homepage.application.post.service.dto.response.postDetail;

import lombok.Builder;
import lombok.Getter;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.user.User;

import java.util.List;

@Getter
public class LostPostDetailResponse extends PostDetailResDto{
    private final List<String> imageList;

    @Builder
    private LostPostDetailResponse(Long postId, String categoryName, String authorName, String title, String content, String createdAt, Boolean isAuthor,
                                   List<String> imageList) {
        super(postId, categoryName, authorName, title, content, createdAt, isAuthor);
        this.imageList = imageList;
    }

    public static LostPostDetailResponse of(Post post, Boolean isAuthor, User user, String categoryName, List<String> imageList) {
        return LostPostDetailResponse.builder()
                .postId(post.getId())
                .categoryName(categoryName)
                .authorName(user.getName())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .isAuthor(isAuthor)
                .imageList(imageList)
                .build();
    }

}
