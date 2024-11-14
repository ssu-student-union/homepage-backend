package ussum.homepage.infra.jpa.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ussum.homepage.infra.jpa.post.entity.RightsDetailEntity;

public interface RightsDetailJpaRepository extends JpaRepository<RightsDetailEntity,Long> {
    void deleteByPostEntityId(Long postId);
}
