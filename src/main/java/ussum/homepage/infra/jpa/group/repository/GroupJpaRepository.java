package ussum.homepage.infra.jpa.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ussum.homepage.domain.group.Group;
import ussum.homepage.infra.jpa.group.entity.GroupCode;
import ussum.homepage.infra.jpa.group.entity.GroupEntity;

import java.util.Optional;

public interface GroupJpaRepository extends JpaRepository<GroupEntity, Long> {
    @Query("SELECT g FROM GroupEntity g WHERE g.groupCode = :groupCodeEnum")
    Optional<GroupEntity> findByGroupCode(GroupCode groupCodeEnum);

}
