package ussum.homepage.infra.jpa.reaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ussum.homepage.infra.jpa.comment.entity.PostCommentEntity;
import ussum.homepage.infra.jpa.postlike.entity.Reaction;
import ussum.homepage.infra.jpa.reaction.entity.PostCommentReactionEntity;
import ussum.homepage.infra.jpa.user.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface PostCommentReactionJpaRepository extends JpaRepository<PostCommentReactionEntity, Long> {
    @Query("SELECT r FROM PostCommentReactionEntity r WHERE r.postCommentEntity.id = :commentId AND r.userEntity.id = :userId AND r.reaction = :reaction")
    Optional<PostCommentReactionEntity> findByCommentIdAndUserIdAndReaction(Long commentId, Long userId, Reaction reaction);

    @Query("SELECT r FROM PostCommentReactionEntity r WHERE r.postCommentEntity.id = :commentId")
    List<PostCommentReactionEntity> findAllByPostCommentId(Long commentId);
}
