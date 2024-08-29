package ussum.homepage.domain.group;

import java.util.List;
import java.util.Optional;

public interface GroupRepository {
    Optional<Group> findByGroupId(Long groupId);
    List<Group> findAllByGroupIdList(List<Long> groupIdList);
}
