package ussum.homepage.domain.acl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.acl.PostAcl;
import ussum.homepage.domain.acl.PostAclRepository;
import ussum.homepage.domain.group.Group;
import ussum.homepage.domain.group.service.GroupReader;
import ussum.homepage.domain.member.Member;
import ussum.homepage.domain.member.service.MemberReader;
import ussum.homepage.infra.jpa.acl.entity.Action;
import ussum.homepage.infra.jpa.acl.entity.Target;
import ussum.homepage.infra.jpa.acl.entity.Type;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostAclManager {
    private final PostAclRepository postAclRepository;
    private final GroupReader groupReader;
    private final MemberReader memberReader;

    public boolean hasPermission(Long userId, String boardCode, Action action) {
        List<PostAcl> permissions = postAclRepository.findAllByBoardCode(boardCode);

        if (userId == null) {
            return checkAnonymousPermissions(permissions, action);
        }

        List<Member> members = memberReader.getMembersWithUserId(userId);

        // 권한 체크 순서: 그룹 -> 유저 -> 전체
        return checkGroupUserPermissions(members, permissions, action) ||
                checkUserPermissions(permissions, action) ||
                checkEveryonePermissions(permissions, action);
    }

    public boolean hasAllowPermissionForAnonymous(String boardCode, Action action) {
        return checkPermissionsByType(boardCode, action, Target.ANONYMOUS, Type.ALLOW);
    }

    public boolean hasDenyPermissionForAnonymous(String boardCode, Action action) {
        return checkPermissionsByType(boardCode, action, Target.ANONYMOUS, Type.DENY);
    }

    private boolean checkAnonymousPermissions(List<PostAcl> permissions, Action action) {
        return permissions.stream()
                .filter(acl -> Objects.equals(acl.getType(), Type.ALLOW.getStringType()))
                .anyMatch(acl -> Objects.equals(acl.getTarget(), Target.ANONYMOUS.getStringTarget())
                        && Objects.equals(acl.getAction(), action.getStringAction()));
    }

    private boolean checkGroupUserPermissions(List<Member> members, List<PostAcl> permissions, Action action) {
        boolean isGroupUser = members.stream()
                .anyMatch(member -> member.getGroupId() != null);

        if (!isGroupUser) {
            return false;
        }

        return permissions.stream()
                .filter(this::isValidGroupPermission)
                .anyMatch(postAcl -> hasMatchingGroupMember(members, postAcl, action));
    }

    private boolean isValidGroupPermission(PostAcl acl) {
        return acl.getTargetGroup() != null &&
                Objects.equals(acl.getType(), Type.ALLOW.getStringType());
    }

    private boolean hasMatchingGroupMember(List<Member> members, PostAcl postAcl, Action action) {
        return members.stream()
                .filter(member -> member.getGroupId() != null)
                .anyMatch(member -> {
                    Group group = groupReader.getGroupById(member.getGroupId());
                    return groupMatches(postAcl.getTargetGroup(), group.getGroupCode())
                            && Objects.equals(postAcl.getAction(), action.getStringAction());
                });
    }

    private boolean checkUserPermissions(List<PostAcl> permissions, Action action) {
        return permissions.stream()
                .filter(acl -> Objects.equals(acl.getType(), Type.ALLOW.getStringType()))
                .anyMatch(acl -> Objects.equals(acl.getTarget(), Target.USER.getStringTarget())
                        && Objects.equals(acl.getAction(), action.getStringAction()));
    }

    private boolean checkEveryonePermissions(List<PostAcl> permissions, Action action) {
        return permissions.stream()
                .filter(acl -> Objects.equals(acl.getType(), Type.ALLOW.getStringType()))
                .anyMatch(acl -> Objects.equals(acl.getTarget(), Target.EVERYONE.getStringTarget())
                        && Objects.equals(acl.getAction(), action.getStringAction()));
    }

    private boolean checkPermissionsByType(String boardCode, Action action, Target target, Type type) {
        return postAclRepository.findAllByBoardCode(boardCode).stream()
                .filter(acl -> Objects.equals(acl.getType(), type.getStringType()))
                .anyMatch(acl -> Objects.equals(acl.getTarget(), target.getStringTarget())
                        && Objects.equals(acl.getAction(), action.getStringAction()));
    }

    private boolean groupMatches(String aclTargetGroup, String memberGroup) {
        return aclTargetGroup.equals(memberGroup);
    }

    // 특정 그룹의 권한 체크 (PostAclAspect에서 사용)
    public boolean checkGroupPermission(Long userId, String groupCode, Action[] actions, Type type) {
        List<Member> members = memberReader.getMembersWithUserId(userId);

        return members.stream()
                .filter(member -> member.getGroupId() != null)
                .anyMatch(member -> {
                    Group group = groupReader.getGroupById(member.getGroupId());
                    return group.getGroupCode().equals(groupCode) &&
                            Arrays.stream(actions)
                                    .allMatch(action -> hasPermission(userId, group.getGroupCode(), action));
                });
    }
}