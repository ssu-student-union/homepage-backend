package ussum.homepage.infra.jpa.group;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ussum.homepage.domain.group.Group;
import ussum.homepage.domain.group.GroupRepository;
import ussum.homepage.infra.jpa.group.repository.GroupJpaRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GroupRepositoryImpl implements GroupRepository{
    private final GroupJpaRepository groupJpaRepository;
    private final GroupMapper groupMapper;

    @Override
    public Optional<Group> findByGroupId(Long groupId) {
        return groupJpaRepository.findById(groupId).map(groupMapper::toDomain);
    }
}
