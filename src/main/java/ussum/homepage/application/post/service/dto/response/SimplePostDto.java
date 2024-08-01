package ussum.homepage.application.post.service.dto.response;

import lombok.Builder;
import lombok.Getter;
import ussum.homepage.domain.post.Post;
import ussum.homepage.infra.jpa.post.PostMapper;
import ussum.homepage.infra.jpa.post.entity.PostEntity;

@Getter
public class SimplePostDto {
    private PostEntity post;
    private Long likeCount;

    public SimplePostDto(PostEntity post, Long likeCount) {
        this.post = post;
        this.likeCount = likeCount;
    }

    public static SimplePostDto of(PostEntity post, Long likeCount) {
        return new SimplePostDto(post, likeCount);
    }
}
