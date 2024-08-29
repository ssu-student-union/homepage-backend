package ussum.homepage.domain.group;

import ussum.homepage.infra.jpa.group.entity.GroupCode;

import java.util.Optional;

public interface GroupRepository {
    Optional<Group> findByGroupId(Long groupId);
    Optional<Group> findByGroupCode(GroupCode groupCodeEnum);
}
