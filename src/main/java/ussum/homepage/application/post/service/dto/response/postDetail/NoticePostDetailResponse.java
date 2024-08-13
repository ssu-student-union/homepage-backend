package ussum.homepage.application.post.service.dto.response.postDetail;

import lombok.Builder;
import lombok.Getter;
import ussum.homepage.domain.post.Post;

import java.util.List;

@Getter
public class NoticePostDetailResponse extends PostDetailResDto {
    private final List<String> imageList;
    private final List<String> fileList;

    @Builder
    private NoticePostDetailResponse(Long postId, String categoryName, String authorName, String title, String content, String createdAt, Boolean isAuthor,
                                     List<String> imageList, List<String> fileList) {
        super(postId, categoryName, authorName, title, content, createdAt, isAuthor);
        this.imageList = imageList;
        this.fileList = fileList;
    }

    public static NoticePostDetailResponse of(Post post, Boolean isAuthor, String authorName, String categoryName, List<String> imageList, List<String> fileList) {
        return NoticePostDetailResponse.builder()
                .postId(post.getId())
                .authorName(authorName)
                .categoryName(categoryName)
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .isAuthor(isAuthor)
                .imageList(imageList)
                .fileList(fileList)
                .build();
    }

}
