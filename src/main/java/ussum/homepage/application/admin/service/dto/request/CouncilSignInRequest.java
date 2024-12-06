package ussum.homepage.application.admin.service.dto.request;

import java.util.List;

public record CouncilSignInRequest(
        String accountId,
        String password,
        String councilName,
        List<Long> groupIdList,
        String memberCode,
        String majorCode
) {
}
