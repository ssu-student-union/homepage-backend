package ussum.homepage.application.reaction.service.dto.request;

import ussum.homepage.domain.reaction.PostCommentReaction;

public record PostCommentReactionCreateRequest(
        String reaction
) {
    public PostCommentReaction toDomain(Long postCommentId) {
        return PostCommentReaction.of(
                null,
                postCommentId,
                reaction);
    }
}
