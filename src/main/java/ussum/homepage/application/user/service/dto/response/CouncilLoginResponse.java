package ussum.homepage.application.user.service.dto.response;

import lombok.Builder;
import ussum.homepage.domain.group.Group;
import ussum.homepage.domain.member.Member;
import ussum.homepage.global.jwt.JwtTokenInfo;

import java.util.List;

@Builder
public record CouncilLoginResponse(
        String accessToken,
        List<String> groupCodeList,
        String memberName,
        String majorName
) {
    public static CouncilLoginResponse of(JwtTokenInfo tokenInfo, List<String> groupCodeList, String memberCode, String majorName) {
        return CouncilLoginResponse.builder()
                .accessToken(tokenInfo.getAccessToken())
                .groupCodeList(groupCodeList)
                .memberName(memberCode)
                .majorName(majorName)
                .build();
    }
}
