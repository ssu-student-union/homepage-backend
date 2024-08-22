package ussum.homepage.application.admin.service.dto.request;

public record CouncilSignInRequest(
        String accountId,
        String password,
        Long groupId,
        String memberCode,
        String majorCode
) {
}
