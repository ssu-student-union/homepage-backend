package ussum.homepage.infra.jpa.group;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ussum.homepage.domain.group.Group;
import ussum.homepage.domain.group.GroupRepository;
import ussum.homepage.infra.jpa.group.repository.GroupJpaRepository;

import java.util.List;
import java.util.Optional;

import static ussum.homepage.infra.jpa.group.entity.QGroupEntity.groupEntity;

@Repository
@RequiredArgsConstructor
public class GroupRepositoryImpl implements GroupRepository{
    private final GroupJpaRepository groupJpaRepository;
    private final GroupMapper groupMapper;
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Group> findByGroupId(Long groupId) {
        return groupJpaRepository.findById(groupId).map(groupMapper::toDomain);
    }

    @Override
    public List<Group> findAllByGroupIdList(List<Long> groupIdList) {
        return queryFactory
                .selectFrom(groupEntity)
                .where(groupEntity.id.in(groupIdList))
                .fetch()
                .stream()
                .map(groupMapper::toDomain)
                .toList();
    }
}
