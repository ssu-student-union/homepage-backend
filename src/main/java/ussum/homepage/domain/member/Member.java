package ussum.homepage.domain.member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Member {
    private Long id;
    private Boolean isAdmin;
    private String memberCode;
    private String majorCode;
    private Long userId;
    private Long groupId;

    public static Member of(Long id, Boolean isAdmin, String memberCode, String majorCode, Long userId, Long groupId) {
        return Member.builder()
                .id(id)
                .isAdmin(isAdmin)
                .memberCode(memberCode)
                .majorCode(majorCode)
                .userId(userId)
                .groupId(groupId)
                .build();
    }

    public static Member createMember(Boolean isAdmin, String memberCode, String majorCode, Long userId) {
        return Member.builder()
                .isAdmin(isAdmin)
                .memberCode(memberCode)
                .majorCode(majorCode)
                .userId(userId)
                .build();
    }

}
