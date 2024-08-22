package ussum.homepage.application.post.service.dto.response;

import lombok.Builder;
import lombok.Getter;
import ussum.homepage.application.post.service.dto.response.postList.PetitionPostResponse;
import ussum.homepage.application.post.service.dto.response.postList.PostListResDto;
import ussum.homepage.domain.post.Post;

@Getter
public class DataPostResponse extends PostListResDto {
    private final Boolean isNotice;

    @Builder
    private DataPostResponse(Long postId, String title, /*String content,*/ String date, Boolean isNotice) {
        super(postId, title, /*content,*/ date);
        this.isNotice = isNotice;
    }

    public static DataPostResponse of(Post post) {
        return DataPostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
//                .content(post.getContent())
                .date(post.getCreatedAt().toString())
                .isNotice(post.getTitle().equals("총학생회칙"))
                .build();
    }
}
