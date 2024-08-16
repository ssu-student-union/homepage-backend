package ussum.homepage.infra.jpa.reaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ussum.homepage.infra.jpa.comment.entity.PostReplyCommentEntity;
import ussum.homepage.infra.jpa.postlike.entity.Reaction;
import ussum.homepage.infra.jpa.reaction.entity.PostCommentReactionEntity;
import ussum.homepage.infra.jpa.reaction.entity.PostReplyCommentReactionEntity;
import ussum.homepage.infra.jpa.user.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface PostReplyCommentReactionJpaRepository extends JpaRepository<PostReplyCommentReactionEntity, Long> {
    Optional<PostReplyCommentReactionEntity> findByPostReplyCommentEntityAndUserEntity(PostReplyCommentEntity postReplyCommentEntity, UserEntity userEntity);

    @Query("SELECT r FROM PostReplyCommentReactionEntity r WHERE r.postReplyCommentEntity.id = :replyCommentId")
    List<PostReplyCommentReactionEntity> findAllByPostReplyCommentId(Long replyCommentId);
}