package ussum.homepage.domain.group.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.group.Group;
import ussum.homepage.domain.group.GroupRepository;
import ussum.homepage.domain.group.exception.GroupNotFoundException;

import java.util.List;

import static ussum.homepage.global.error.status.ErrorStatus.GROUP_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class GroupReader {
    private final GroupRepository groupRepository;

    public Group getGroupByGroupId(Long groupId) {
        return groupRepository.findByGroupId(groupId).orElseThrow(() -> new GroupNotFoundException(GROUP_NOT_FOUND));
    }

    public List<Group> getGroupsByGroupIdList(List<Long> groupIdList) {
        return groupRepository.findAllByGroupIdList(groupIdList);
    }
}
