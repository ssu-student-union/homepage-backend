package ussum.homepage.infra.jpa.reaction;

import org.springframework.stereotype.Component;
import ussum.homepage.domain.reaction.PostCommentReaction;
import ussum.homepage.infra.jpa.comment.entity.PostCommentEntity;
import ussum.homepage.infra.jpa.postlike.entity.Reaction;
import ussum.homepage.infra.jpa.reaction.entity.PostCommentReactionEntity;

@Component
public class PostCommentReactionMapper {
    public PostCommentReaction toDomain(PostCommentReactionEntity postCommentReactionEntity) {
        return PostCommentReaction.of(
                postCommentReactionEntity.getId(),
                postCommentReactionEntity.getPostCommentEntity().getId(),
                String.valueOf(postCommentReactionEntity.getReaction())
        );
    }

    public PostCommentReactionEntity toEntity(PostCommentReaction postCommentReaction) {
        return PostCommentReactionEntity.of(
                postCommentReaction.getId(),
                PostCommentEntity.from(postCommentReaction.getPostCommentId()),
                Reaction.getEnumRactionFromStringReaction(postCommentReaction.getReactionType())
        );
    }

}
