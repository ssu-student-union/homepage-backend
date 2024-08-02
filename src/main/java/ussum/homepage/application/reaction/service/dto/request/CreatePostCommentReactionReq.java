package ussum.homepage.application.reaction.service.dto.request;

import ussum.homepage.domain.reaction.PostCommentReaction;

public record CreatePostCommentReactionReq(
        String reaction
) {
    public PostCommentReaction toDomain(Long commentId, Long userId) {
        return PostCommentReaction.of(
                null,
                commentId,
                userId,
                reaction
        );
    }
}
