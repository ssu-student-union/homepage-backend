package ussum.homepage.application.reaction.service.dto.response;

import ussum.homepage.application.post.service.dto.response.PostResponse;
import ussum.homepage.application.user.service.dto.response.UserResponse;

public record PostReactionRes(
        String id,
        PostResponse post,
        UserResponse author,
        String reaction
) {
}
