package ussum.homepage.application.reaction.service.dto.request;

import ussum.homepage.domain.postlike.PostReaction;

public record PostReactionCreateRequest(
        String reaction
) {
    public PostReaction toDomain(Long postId, Long userId) {
        return PostReaction.of(
                null,
                reaction,
                postId,
                userId
                );
    }
}
