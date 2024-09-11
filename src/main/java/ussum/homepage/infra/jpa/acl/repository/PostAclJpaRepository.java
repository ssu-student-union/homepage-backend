package ussum.homepage.infra.jpa.acl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ussum.homepage.domain.acl.PostAcl;
import ussum.homepage.infra.jpa.acl.entity.BoardAclEntity;
import ussum.homepage.infra.jpa.acl.entity.PostAclEntity;

import java.util.List;

public interface PostAclJpaRepository extends JpaRepository<PostAclEntity, Long> {
    @Query("SELECT pa FROM PostAclEntity pa WHERE pa.postEntity.id = :postId")
    List<PostAclEntity> findAllByPostId(@Param("postId") Long postId);
}
