package ussum.homepage.infra.jpa.post.dto;

import ussum.homepage.infra.jpa.post.entity.PostEntity;


public record SimplePostDto(PostEntity postEntity, Long likeCount) {

    public static SimplePostDto of(PostEntity post, Long likeCount) {
        return new SimplePostDto(post, likeCount);
    }
}
