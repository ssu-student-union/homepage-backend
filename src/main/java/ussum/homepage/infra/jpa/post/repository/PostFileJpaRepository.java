package ussum.homepage.infra.jpa.post.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ussum.homepage.infra.jpa.post.entity.PostFileEntity;

import java.util.List;

public interface PostFileJpaRepository extends JpaRepository<PostFileEntity, Long> {
    @Query("SELECT pf FROM PostFileEntity pf WHERE pf.postEntity.id = :postId")
    List<PostFileEntity> findAllByPostId(@Param("postId") Long postId);
}
