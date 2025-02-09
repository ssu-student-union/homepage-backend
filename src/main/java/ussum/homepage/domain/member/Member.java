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
    private Boolean isVerified;

    public static Member of(Long id, Boolean isAdmin, String memberCode, String majorCode, Long userId, Long groupId, Boolean isVerified) {
        return Member.builder()
                .id(id)
                .isAdmin(isAdmin)
                .memberCode(memberCode)
                .majorCode(majorCode)
                .userId(userId)
                .groupId(groupId)
                .isVerified(isVerified)
                .build();
    }

    public static Member createMember(Boolean isAdmin, String memberCode, String majorCode, Long userId, Boolean isVerified) {
        return Member.builder()
                .isAdmin(isAdmin)
                .memberCode(memberCode)
                .majorCode(majorCode)
                .userId(userId)
                .isVerified(isVerified)
                .build();
    }

}
