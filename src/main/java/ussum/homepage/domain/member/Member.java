package ussum.homepage.domain.member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Member {
    private Long id;
    private Boolean isAdmin;
    private String memberCode;
    private Long userId;
    private Long groupId;

    public static Member of(Long id, Boolean isAdmin, String memberCode, Long userId, Long groupId) {
        return Member.builder()
                .id(id)
                .isAdmin(isAdmin)
                .memberCode(memberCode)
                .userId(userId)
                .groupId(groupId)
                .build();
    }

}
