package ussum.homepage.application.user.service.dto.response;

import lombok.Builder;
import ussum.homepage.domain.member.Member;
import ussum.homepage.domain.user.User;

@Builder
public record MyPageInfoResponse(
        String name,
        String nickname,
        String account,
        String studentId,
        String majorCode,
        String memberCode,
        boolean isUnion
) {
    public static MyPageInfoResponse of(User user, Member member, boolean isUnion){
        return MyPageInfoResponse.builder()
                .name(user.getName())
                .nickname(user.getNickname())
                .account(user.getAccountId())
                .studentId(user.getStudentId())
                .memberCode(member.getMemberCode())
                .majorCode(member.getMajorCode())
                .isUnion(isUnion)
                .build();
    }
}
