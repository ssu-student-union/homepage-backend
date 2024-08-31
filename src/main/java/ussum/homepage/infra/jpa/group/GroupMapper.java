package ussum.homepage.infra.jpa.group;

import org.springframework.stereotype.Component;
import ussum.homepage.domain.group.Group;
import ussum.homepage.infra.jpa.group.entity.GroupCode;
import ussum.homepage.infra.jpa.group.entity.GroupEntity;

@Component
public class GroupMapper {
    public Group toDomain(GroupEntity groupEntity) {
        return Group.of(
                groupEntity.getId(),
                GroupCode.fromEnumOrNull(groupEntity.getGroupCode()),
                groupEntity.getName()
        );
    }

    public GroupEntity toEntity(Group group) {
        return GroupEntity.of(
                group.getId(),
                GroupCode.getEnumGroupCodeFromStringGroupCode(group.getGroupCode()),
                group.getName()
        );
    }
}
