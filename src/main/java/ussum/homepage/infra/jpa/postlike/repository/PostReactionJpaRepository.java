package ussum.homepage.infra.jpa.postlike.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ussum.homepage.infra.jpa.post.entity.PostEntity;
import ussum.homepage.infra.jpa.postlike.entity.PostReactionEntity;
import ussum.homepage.infra.jpa.postlike.entity.Reaction;
import ussum.homepage.infra.jpa.reaction.entity.PostCommentReactionEntity;
import ussum.homepage.infra.jpa.user.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface PostReactionJpaRepository extends JpaRepository<PostReactionEntity, Long> {
    @Query("SELECT r FROM PostReactionEntity r WHERE r.postEntity.id = :postId AND r.userEntity.id = :userId AND r.reaction = :reaction")
    Optional<PostReactionEntity> findByPostIdAndUserIdAndReaction(Long postId, Long userId, Reaction reaction);

    @Query("SELECT r FROM PostReactionEntity r WHERE r.postEntity.id = :postId")
    List<PostReactionEntity> findAllByPostId(Long postId);

}
