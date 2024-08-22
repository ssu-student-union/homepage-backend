package ussum.homepage.application.user.service.dto.request;

public record CouncilLoginRequest(
        String accountId,
        String password
) {
}
