package ussum.homepage.infra.jpa.comment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ussum.homepage.infra.jpa.comment.entity.PostCommentEntity;

import java.util.List;
import java.util.Optional;

public interface PostCommentJpaRepository extends JpaRepository<PostCommentEntity, Long> {
    @Query("SELECT pc FROM PostCommentEntity pc JOIN pc.postEntity p WHERE p.id = :postId")
    Page<PostCommentEntity> findAllByPostId(Pageable pageable, @Param("postId") Long postId);

    @Query("SELECT pc FROM PostCommentEntity pc JOIN pc.postEntity p WHERE p.id = :postId")
    List<PostCommentEntity> findAllByPostId(@Param("postId") Long postId);

    @Query("SELECT pc FROM PostCommentEntity pc JOIN pc.postEntity p JOIN pc.userEntity u WHERE p.id = :postId AND u.id = :userId")
    Optional<PostCommentEntity> findByPostIdAndUserId(@Param("postId") Long postId, @Param("userId") Long userId);

    @Query("SELECT c FROM PostCommentEntity c " +
            "LEFT JOIN PostCommentReactionEntity r ON c.id = r.postCommentEntity.id AND r.reaction = 'LIKE' " +
            "WHERE c.postEntity.id = :postId " +
            "GROUP BY c.id " +
            "ORDER BY COUNT(r.id) DESC , c.createdAt DESC")
    List<PostCommentEntity> findAllByPostIdOrderByLikesDesc(Long postId);

    List<PostCommentEntity> findAllByPostEntityIdOrderByCreatedAtDesc(Long postId);
}
