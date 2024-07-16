package ussum.homepage.infra.jpa.reaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ussum.homepage.infra.jpa.comment.entity.PostCommentEntity;
import ussum.homepage.infra.jpa.postlike.entity.Reaction;
import ussum.homepage.infra.jpa.reaction.entity.PostCommentReactionEntity;
import ussum.homepage.infra.jpa.user.entity.UserEntity;

import java.util.Optional;

public interface PostCommentReactionJpaRepository extends JpaRepository<PostCommentReactionEntity, Long> {
    Optional<PostCommentReactionEntity> findByPostCommentEntityAndUserEntityAndReaction(PostCommentEntity postCommentEntity, UserEntity userEntity, Reaction reaction);


}
