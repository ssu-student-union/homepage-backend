package ussum.homepage.infra.jpa.post.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ussum.homepage.infra.jpa.post.entity.RightsDetailEntity;

public interface RightsDetailJpaRepository extends JpaRepository<RightsDetailEntity,Long> {
    void deleteByPostEntityId(Long postId);
    List<RightsDetailEntity> findByPostEntityId(Long postId);
}
