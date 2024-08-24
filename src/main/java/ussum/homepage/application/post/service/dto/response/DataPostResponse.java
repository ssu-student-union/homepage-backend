package ussum.homepage.application.post.service.dto.response;

import lombok.Builder;
import lombok.Getter;
import ussum.homepage.application.post.service.dto.response.postList.PetitionPostResponse;
import ussum.homepage.application.post.service.dto.response.postList.PostListResDto;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.PostFile;

import java.util.List;

@Getter
public class DataPostResponse extends PostListResDto {
    private final Boolean isNotice;
    private final List<String> fileNames;
    private final List<String> files;

    @Builder
    private DataPostResponse(Long postId, String title, /*String content,*/ String date, String category, Boolean isNotice, List<String> fileNames, List<String> files) {
        super(postId, title, null, date, category);
        this.isNotice = isNotice;
        this.fileNames = fileNames;
        this.files = files;
    }

    public static DataPostResponse of(Post post, List<PostFile> postFiles) {
        return DataPostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
//                .content(post.getContent())
                .date(post.getCreatedAt().toString())
                .isNotice(post.getTitle().equals("총학생회칙"))
                .fileNames(postFiles.stream().map(postFile ->postFile.getFileCategory()).toList())
                .files(postFiles.stream().map(postFile -> postFile.getUrl()).toList())
                .build();
    }
}
