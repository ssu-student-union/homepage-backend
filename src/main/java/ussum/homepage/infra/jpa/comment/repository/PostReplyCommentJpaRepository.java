package ussum.homepage.infra.jpa.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ussum.homepage.infra.jpa.comment.entity.PostReplyCommentEntity;

import java.util.List;

public interface PostReplyCommentJpaRepository extends JpaRepository<PostReplyCommentEntity, Long> {
    @Query("SELECT rc FROM PostReplyCommentEntity rc WHERE rc.postCommentEntity.id = :commentId ORDER BY rc.createdAt DESC")
    List<PostReplyCommentEntity> findAllByCommentIdOrderByCreatedAtDesc(Long commentId);

}
