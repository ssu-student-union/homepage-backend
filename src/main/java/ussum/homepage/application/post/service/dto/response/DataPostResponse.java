package ussum.homepage.application.post.service.dto.response;

import lombok.Builder;
import lombok.Getter;
import ussum.homepage.application.post.service.dto.response.postList.PostListResDto;
import ussum.homepage.domain.post.Post;

import java.util.List;

@Getter
public class DataPostResponse extends PostListResDto {
    private final Boolean isNotice;
    private final List<FileResponse> files;

    @Builder
    private DataPostResponse(Long postId, String title, String content, String date, String category, Boolean isNotice, List<FileResponse> files) {
        super(postId, title, content, date, category);
        this.isNotice = isNotice;
        this.files = files;
    }

    public static DataPostResponse of(Post post, List<FileResponse> files) {
        return DataPostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .category(post.getCategory())
                .date(post.getCreatedAt())
                .isNotice(post.getTitle().equals("총학생회칙"))
                .files(files)
                .build();
    }
}
