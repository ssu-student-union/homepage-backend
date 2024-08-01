package ussum.homepage.application.post.service.dto.response;

import ussum.homepage.domain.post.Post;

import java.util.List;
import java.util.function.Function;

public record PostListResponse(
        List<PostResponse> posts,
        Integer totalElements,
        Integer numberOfElements
) {
    public static PostListResponse of(List<Post> posts, Integer totalElements, Integer numberOfElements, Function<Long, PostResponse> formatter) {
        return new PostListResponse(
                posts.stream()
                        .map(post -> formatter.apply(post.getId()))
                        .toList(), totalElements, numberOfElements);
    }
}
