package ussum.homepage.infra.jpa.reaction;

import org.springframework.stereotype.Component;
import ussum.homepage.domain.reaction.PostCommentReaction;
import ussum.homepage.infra.jpa.comment.entity.PostCommentEntity;
import ussum.homepage.infra.jpa.postlike.entity.Reaction;
import ussum.homepage.infra.jpa.reaction.entity.PostCommentReactionEntity;
import ussum.homepage.infra.jpa.user.entity.UserEntity;

@Component
public class PostCommentReactionMapper {
    public PostCommentReaction toDomain(PostCommentReactionEntity postCommentReactionEntity) {
        return PostCommentReaction.of(
                postCommentReactionEntity.getId(),
                postCommentReactionEntity.getUserEntity().getId(),
                postCommentReactionEntity.getPostCommentEntity().getId(),
                postCommentReactionEntity.getReaction().getStringReaction()
        );
    }

    public PostCommentReactionEntity toEntity(PostCommentReaction postCommentReaction) {
        return PostCommentReactionEntity.of(
                postCommentReaction.getId(),
                PostCommentEntity.from(postCommentReaction.getPostCommentId()),
                UserEntity.from(postCommentReaction.getUserId()),
                Reaction.getEnumReactionFromStringReaction(postCommentReaction.getReaction())
        );
    }

}
