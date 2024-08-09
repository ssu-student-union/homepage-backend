package ussum.homepage.application.reaction.service.dto.request;

import ussum.homepage.domain.reaction.PostCommentReaction;
import ussum.homepage.domain.reaction.PostReplyCommentReaction;

public record CreatePostReplyCommentReactionReq(
        String reaction
) {
    public PostReplyCommentReaction toDomain(Long replyCommentId, Long userId) {
        return PostReplyCommentReaction.of(
                null,
                replyCommentId,
                userId,
                reaction
        );
    }


}
