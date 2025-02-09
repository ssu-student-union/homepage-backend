package ussum.homepage.domain.acl.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.acl.PostAcl;
import ussum.homepage.domain.acl.PostAclRepository;
import ussum.homepage.domain.group.Group;
import ussum.homepage.domain.group.service.GroupReader;
import ussum.homepage.domain.member.Member;
import ussum.homepage.domain.member.service.MemberReader;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostAclManager {
    private final PostAclRepository postAclRepository;
    private final GroupReader groupReader;
    private final MemberReader memberReader;

    // 로그인된 사용자의 권한 확인
    public boolean hasPermission(Long userId, String boardCode, String action) {
        List<PostAcl> permissions = postAclRepository.findAllByBoardCode(boardCode);
        List<Member> members = memberReader.getMembersWithUserId(userId);

        // 1. 자치기구(총학생회, 자치단체) 그룹 권한 확인
        boolean isGroupUser = members.stream().anyMatch(member -> member.getGroupId() != null);
        System.out.println("isGroupUser = " + isGroupUser);

        // 자치기구에 속한 사용자라면 그룹 권한만 확인
        if (isGroupUser) {
            boolean groupHasPermission = checkGroupPermission(members, permissions, action);
            System.out.println("groupHasPermission = " + groupHasPermission);
            // 자치기구에 속한 사용자가 그룹 권한이 없으면 false 반환
            return groupHasPermission;
        }

//        // 일반 사용자 중 인증 되지 않은 사용자(UNVERIFIED_USER) 신입생 권한 확인
        boolean unverifiedUserHasPermission = checkUnverifiedUserPermission(permissions, action);
        System.out.println("unverifiedUserHasPermission = " + unverifiedUserHasPermission);

        // 인증 되지 않은 사용자 권한이 있으면 true 반환
        if (unverifiedUserHasPermission) {
            return true;
        }

        // 인증 되지 않은 사용자라면, 일반 사용자 권한 확인, EVERYONE 권한 확인 로직을 타면 안된다.
        // 또한, 해당 라인부터는 members의 요소가 자치기구가 아닌, 일반 유저이기 때문에 많아야 1개일 것이다.
        if (members.stream().noneMatch(Member::getIsVerified)) {
            return false;
        }

        // 2. 일반 사용자(USER) 권한 확인
        boolean userHasPermission = checkUserPermission(permissions, action);
        System.out.println("userHasPermission = " + userHasPermission);

        // 로그인 사용자 권한이 있으면 true 반환
        if (userHasPermission) {
            return true;
        }

        // 3. EVERYONE 권한 확인 (모든 사용자에게 열려있는 경우)
        boolean everyonePermission = checkEveryonePermission(permissions, action);
        System.out.println("everyonePermission = " + everyonePermission);

        // 최종적으로 EVERYONE 권한을 반환
        return everyonePermission;
    }

    // 비로그인 사용자의 ALLOW 권한 확인
    public boolean hasAllowPermissionForAnonymous(String boardCode, String action) {
        List<PostAcl> permissions = postAclRepository.findAllByBoardCode(boardCode);
        return permissions.stream()
                .filter(acl -> acl.getType().equals("ALLOW"))
                .anyMatch(acl -> acl.getTarget() != null
                        && acl.getAction().equals(action)
                        && acl.getTarget().equals("ANONYMOUS"));
    }

    // 비로그인 사용자의 DENY 권한 확인
    public boolean hasDenyPermissionForAnonymous(String boardCode, String action) {
        List<PostAcl> permissions = postAclRepository.findAllByBoardCode(boardCode);
        return permissions.stream()
                .filter(acl -> acl.getType().equals("DENY"))
                .anyMatch(acl -> acl.getTarget() != null
                        && acl.getAction().equals(action)
                        && acl.getTarget().equals("ANONYMOUS"));
    }

    // 특정 그룹에 대한 권한 확인 로직
    private boolean checkGroupPermission(List<Member> members, List<PostAcl> permissions, String action) {
        return permissions.stream()
                .filter(postAcl -> postAcl.getTargetGroup() != null)
                .anyMatch(postAcl -> members.stream()
                        .anyMatch(member -> {
                            if (member.getGroupId() == null) return false;
                            Group group = groupReader.getGroupById(member.getGroupId());

                            // 그룹 코드 일치 여부와 해당 액션에 대한 권한 확인
                            return groupMatches(postAcl.getTargetGroup(), group.getGroupCode())
                                    && postAcl.getAction().equals(action)
                                    && postAcl.getType().equals("ALLOW");
                        })
                );
    }

    // 로그인된 인증되지 않은 사용자(UNVERIFIED_USER) 대한 권한 확인
    private boolean checkUnverifiedUserPermission(List<PostAcl> permissions, String action) {
        return permissions.stream()
                .anyMatch(postAcl -> postAcl.getTarget() != null
                        && postAcl.getAction().equals(action)
                        && postAcl.getTarget().equals("UNVERIFIED_USER")
                        && postAcl.getType().equals("ALLOW"));
    }


    // 로그인된 사용자(USER)에 대한 권한 확인
    private boolean checkUserPermission(List<PostAcl> permissions, String action) {
        return permissions.stream()
                .anyMatch(postAcl -> postAcl.getTarget() != null
                        && postAcl.getAction().equals(action)
                        && postAcl.getTarget().equals("USER")
                        && postAcl.getType().equals("ALLOW"));
    }

    // 모든 사용자(EVERYONE)에 대한 권한 확인
    private boolean checkEveryonePermission(List<PostAcl> permissions, String action) {
        return permissions.stream()
                .anyMatch(postAcl -> postAcl.getTarget() != null
                        && postAcl.getAction().equals(action)
                        && postAcl.getTarget().equals("EVERYONE")
                        && postAcl.getType().equals("ALLOW"));
    }

    private boolean groupMatches(String aclTargetGroup, String memberGroup) {
        return aclTargetGroup.equals(memberGroup);
    }
}
