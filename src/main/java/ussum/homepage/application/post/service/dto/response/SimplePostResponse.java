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
        String onGoingStatus,
        Integer like
) {
    public static SimplePostResponse of(Post post,
                                        Integer like){
        return SimplePostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .date(post.getCreatedAt())
                .onGoingStatus(post.getOnGoingStatus())
                .like(like)
                .build();
    }
}
