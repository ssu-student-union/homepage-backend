package ussum.homepage.application.user.service.dto.response;

import lombok.Builder;
import ussum.homepage.domain.member.Member;
import ussum.homepage.domain.user.User;

@Builder
public record MyPageInfoResponse(
        String name,
        String account,
        String studentId,
        String majorCode,
        String memberCode,
        boolean union
) {
    public static MyPageInfoResponse of(User user, Member member){
        return MyPageInfoResponse.builder()
                .name(user.getName())
                .account(user.getAccountId())
                .studentId(user.getStudentId())
                .memberCode(member.getMemberCode())
                .majorCode(member.getMajorCode())
                .union(user.getStudentId() == null)
                .build();
    }
}
