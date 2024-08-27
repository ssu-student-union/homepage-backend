package ussum.homepage.application.user.service.dto.response;

import lombok.Builder;
import ussum.homepage.domain.group.Group;
import ussum.homepage.domain.member.Member;
import ussum.homepage.global.jwt.JwtTokenInfo;

@Builder
public record CouncilLoginResponse(
        String accessToken,
        String groupName,
        String memberName
) {
    public static CouncilLoginResponse of(JwtTokenInfo tokenInfo, Group group, Member member ) {
        return CouncilLoginResponse.builder()
                .accessToken(tokenInfo.getAccessToken())
                .groupName(group.getGroupCode())
                .memberName(member.getMemberCode())
                .build();
    }
}
