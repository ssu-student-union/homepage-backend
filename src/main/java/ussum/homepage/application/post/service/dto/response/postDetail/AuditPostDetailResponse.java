package ussum.homepage.application.post.service.dto.response.postDetail;

import lombok.Builder;
import lombok.Getter;
import ussum.homepage.application.post.service.dto.response.FileResponse;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.user.User;

import java.util.List;

@Getter
public class AuditPostDetailResponse extends PostDetailResDto {
//    private final List<String> imageList;
//    private final List<String> fileList;
private final List<FileResponse> fileResponseList;

    @Builder
    private AuditPostDetailResponse(Long postId, String categoryName, String authorName, String title, String content, String createdAt, String lastEditedAt, Boolean isAuthor,
                                    List<FileResponse> fileResponseList) {
        super(postId, categoryName, authorName, title, content, createdAt, lastEditedAt, isAuthor);
//        this.imageList = imageList;
//        this.fileList = fileList;
        this.fileResponseList = fileResponseList;
    }

    public static PostDetailResDto of(Post post, Boolean isAuthor, User user, String categoryName, List<FileResponse> fileResponseList) {
        return AuditPostDetailResponse.builder()
                .postId(post.getId())
                .categoryName(categoryName)
                .authorName(user.getName())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .lastEditedAt(post.getLastEditedAt())
                .isAuthor(isAuthor)
//                .imageList(imageList)
//                .fileList(fileList)
                .fileResponseList(fileResponseList)
                .build();
    }

}
