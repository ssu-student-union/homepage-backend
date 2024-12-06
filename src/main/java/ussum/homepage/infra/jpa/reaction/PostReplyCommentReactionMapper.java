package ussum.homepage.infra.jpa.reaction;

import org.springframework.stereotype.Component;
import ussum.homepage.domain.reaction.PostReplyCommentReaction;
import ussum.homepage.infra.jpa.comment.entity.PostReplyCommentEntity;
import ussum.homepage.infra.jpa.postlike.entity.Reaction;
import ussum.homepage.infra.jpa.reaction.entity.PostReplyCommentReactionEntity;
import ussum.homepage.infra.jpa.user.entity.UserEntity;

@Component
public class PostReplyCommentReactionMapper {
    public PostReplyCommentReaction toDomain(PostReplyCommentReactionEntity postReplyCommentReactionEntity) {
        return PostReplyCommentReaction.of(
                postReplyCommentReactionEntity.getId(),
                postReplyCommentReactionEntity.getPostReplyCommentEntity().getId(),
                postReplyCommentReactionEntity.getUserEntity().getId(),
                postReplyCommentReactionEntity.getReaction().getStringReaction()
        );
    }

    public PostReplyCommentReactionEntity toEntity(PostReplyCommentReaction postReplyCommentReaction) {
        return PostReplyCommentReactionEntity.of(
                postReplyCommentReaction.getId(),
                PostReplyCommentEntity.from(postReplyCommentReaction.getPostReplyCommentId()),
                UserEntity.from(postReplyCommentReaction.getUserId()),
                Reaction.getEnumReactionFromStringReaction(postReplyCommentReaction.getReaction())
        );
    }
}
