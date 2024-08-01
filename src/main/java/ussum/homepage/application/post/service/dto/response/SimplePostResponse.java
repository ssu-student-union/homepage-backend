package ussum.homepage.application.post.service.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import ussum.homepage.domain.post.Post;

@Builder(access = AccessLevel.PRIVATE)
public record SimplePostResponse(
        Long postId,
        String title,
        String content,
        String date,
        Integer like
) {
    public static SimplePostResponse of(Post post,
                                        Integer like){
        return SimplePostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .date(post.getCreatedAt())
                .like(like)
                .build();
    }
}
