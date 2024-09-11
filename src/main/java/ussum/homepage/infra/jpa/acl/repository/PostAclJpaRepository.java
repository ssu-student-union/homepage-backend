package ussum.homepage.infra.jpa.acl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ussum.homepage.infra.jpa.acl.entity.PostAclEntity;
import ussum.homepage.infra.jpa.post.entity.BoardCode;

import java.util.List;

public interface PostAclJpaRepository extends JpaRepository<PostAclEntity, Long> {
    @Query("SELECT pa FROM PostAclEntity pa WHERE pa.boardEntity.boardCode = :boardCode")
    List<PostAclEntity> findAllByBoardCode(@Param("boardCode") BoardCode boardCode);

}
