package ussum.homepage.infra.jpa.reaction;

import org.springframework.stereotype.Component;
import ussum.homepage.domain.postlike.PostReaction;
import ussum.homepage.infra.jpa.post.entity.PostEntity;
import ussum.homepage.infra.jpa.postlike.entity.PostReactionEntity;
import ussum.homepage.infra.jpa.postlike.entity.Reaction;
import ussum.homepage.infra.jpa.user.entity.UserEntity;

@Component
public class PostReactionMapper {
    public PostReaction toDomain(PostReactionEntity postReactionEntity) {
        return PostReaction.of(
                postReactionEntity.getId(),
                postReactionEntity.getReaction().getStringReaction(),
                postReactionEntity.getPostEntity().getId(),
                postReactionEntity.getUserEntity().getId()
        );
    }

    public PostReactionEntity toEntity(PostReaction postReaction) {
        return PostReactionEntity.of(
                postReaction.getId(),
                Reaction.getEnumReactionFromStringReaction(postReaction.getReaction()),
                PostEntity.from(postReaction.getPostId()),
                UserEntity.from(postReaction.getUserId())
        );
    }
}
