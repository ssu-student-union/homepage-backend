package ussum.homepage.infra.jpa.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ussum.homepage.infra.jpa.comment.entity.PostCommentEntity;
import ussum.homepage.infra.jpa.comment.entity.PostReplyCommentEntity;
import ussum.homepage.infra.jpa.reaction.entity.PostReplyCommentReactionEntity;

import java.util.List;

public interface PostReplyCommentJpaRepository extends JpaRepository<PostReplyCommentEntity, Long> {
    @Query("SELECT rc FROM PostReplyCommentEntity rc WHERE rc.postCommentEntity.id = :commentId")
    List<PostReplyCommentEntity> findAllByCommentId(Long commentId);

    @Query("SELECT pc FROM PostReplyCommentEntity pc WHERE pc.postCommentEntity.id = :commentId")
    List<PostReplyCommentEntity> findAllByPostCommentId(Long commentId);
}
