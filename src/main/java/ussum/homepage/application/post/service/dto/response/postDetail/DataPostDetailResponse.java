package ussum.homepage.application.post.service.dto.response.postDetail;

import lombok.Builder;
import lombok.Getter;
import ussum.homepage.application.post.service.dto.response.FileResponse;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.user.User;

import java.util.List;

@Getter
public class DataPostDetailResponse extends PostDetailResDto{
    private final List<FileResponse> fileResponseList;

    @Builder
    private DataPostDetailResponse(Long postId, String categoryName, String authorName, String title, String content, String createdAt, String lastEditedAt, Boolean isAuthor,
                                   List<FileResponse> fileResponseList, List<String> canAuthority) {
        super(postId, categoryName, authorName, title, content, createdAt, lastEditedAt, isAuthor, canAuthority);
        this.fileResponseList = fileResponseList;
    }

    public static DataPostDetailResponse of(Post post, Boolean isAuthor, User user, String categoryName, List<FileResponse> fileResponseList) {
        return DataPostDetailResponse.builder()
                .postId(post.getId())
                .categoryName(categoryName)
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

