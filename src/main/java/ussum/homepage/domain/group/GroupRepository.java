package ussum.homepage.domain.group;

import java.util.Optional;

public interface GroupRepository {
    Optional<Group> findByGroupId(Long groupId);
}
