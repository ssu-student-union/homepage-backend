package ussum.homepage.application.reaction.service.dto.response;

import ussum.homepage.application.post.service.dto.response.PostResponse;
import ussum.homepage.application.user.service.dto.response.UserResponse;

public record PostReactionResponse(
        Long id,
        PostResponse postResponse,
        UserResponse author,
        String reaction
) {
    public static PostReactionResponse of(Long id,
                                          PostResponse postResponse,
                                          UserResponse author,
                                          String reaction
    ) {
        return new PostReactionResponse(
                id,
                postResponse,
                author,
                reaction
        );
    }
}
