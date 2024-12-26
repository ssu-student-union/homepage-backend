package ussum.homepage.application.post.service.dto.response.postList;

import lombok.Builder;
import lombok.Getter;
import ussum.homepage.domain.post.Post;

@Getter
public class ServiceNoticePostResponse extends PostListResDto{

    @Builder
    protected ServiceNoticePostResponse(Long postId, String title, String content, String date, String category) {
        super(postId, title, content, date, category);
    }

    public static ServiceNoticePostResponse of(Post post) {
        return ServiceNoticePostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .category(post.getCategory())
                .date(post.getCreatedAt())
                .build();
    }
}
