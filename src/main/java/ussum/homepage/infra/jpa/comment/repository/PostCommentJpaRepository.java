package ussum.homepage.infra.jpa.comment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ussum.homepage.infra.jpa.comment.entity.PostCommentEntity;

public interface PostCommentJpaRepository extends JpaRepository<PostCommentEntity, Long> {
    @Query("SELECT pc FROM PostCommentEntity pc JOIN pc.postEntity p WHERE p.id = :postId")
    Page<PostCommentEntity> findAllByPostId(Pageable pageable, @Param("postId") Long postId);

    @Query("SELECT pc FROM PostCommentEntity pc JOIN pc.postEntity p JOIN pc.userEntity u WHERE p.id = :postId AND u.id = :userId")
    PostCommentEntity findByPostIdAndUserId(@Param("postId") Long postId, @Param("userId") Long userId);
}
