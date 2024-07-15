package ussum.homepage.application.reaction.service.dto.response;

import ussum.homepage.application.comment.service.dto.PostCommentResponse;
import ussum.homepage.application.user.service.dto.response.UserResponse;

public record PostCommentReactionResponse(
        Long id,
        PostCommentResponse postCommentResponse,
        UserResponse author,
        String reaction
) {
    public static PostCommentReactionResponse of(Long postCommentReactionId,
                                                 PostCommentResponse postCommentResponse,
                                                 UserResponse userResponse,
                                                 String reaction) {

        return new PostCommentReactionResponse(
                postCommentReactionId,
                postCommentResponse,
                userResponse,
                reaction
        );
    }

}
