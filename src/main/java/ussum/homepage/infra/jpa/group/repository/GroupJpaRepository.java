package ussum.homepage.infra.jpa.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ussum.homepage.infra.jpa.group.entity.GroupEntity;

public interface GroupJpaRepository extends JpaRepository<GroupEntity, Long> {
}
