package ussum.homepage.application.post.service.dto.response.postDetail;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import ussum.homepage.application.post.service.dto.response.FileResponse;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.user.User;

@Getter
public class ServicePostDetailResponse extends PostDetailResDto{
    private final List<FileResponse> fileResponseList;

    @Builder
    private ServicePostDetailResponse(Long postId, String categoryName, String authorName, String title, String content, String createdAt, String lastEditedAt, Boolean isAuthor,
                                     List<FileResponse> fileResponseList, List<String> canAuthority) {
        super(postId, categoryName, authorName, title, content, createdAt, lastEditedAt, isAuthor, canAuthority);
        this.fileResponseList = fileResponseList;
    }

    public static ServicePostDetailResponse of(Post post, Boolean isAuthor, User user, List<FileResponse> fileResponseList) {
        return ServicePostDetailResponse.builder()
                .postId(post.getId())
                .authorName(user.getName())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .lastEditedAt(post.getLastEditedAt())
                .isAuthor(isAuthor)
                .fileResponseList(fileResponseList)
                .build();
    }
}
