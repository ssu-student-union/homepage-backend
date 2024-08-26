package ussum.homepage.application.post.service.dto.response.postDetail;

import lombok.Builder;
import lombok.Getter;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.user.User;

import java.util.List;

@Getter
public class AuditPostDetailResponse extends PostDetailResDto {
    private final List<String> imageList;
    private final List<String> fileList;

    @Builder
    private AuditPostDetailResponse(Long postId, String categoryName, String authorName, String title, String content, String createdAt, String lastEditedAt, Boolean isAuthor,
                                    List<String> imageList, List<String> fileList) {
        super(postId, categoryName, authorName, title, content, createdAt, lastEditedAt, isAuthor);
        this.imageList = imageList;
        this.fileList = fileList;
    }

    public static PostDetailResDto of(Post post, Boolean isAuthor, User user, String categoryName, List<String> imageList, List<String> fileList) {
        return AuditPostDetailResponse.builder()
                .postId(post.getId())
                .categoryName(categoryName)
                .authorName(user.getName())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .lastEditedAt(post.getLastEditedAt())
                .isAuthor(isAuthor)
                .imageList(imageList)
                .fileList(fileList)
                .build();
    }

}
