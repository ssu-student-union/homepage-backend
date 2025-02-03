package ussum.homepage.application.user.service.dto.request;

public record MyPageUpdateRequest(
        String currentPassword,
        String newPassword,
        String confirmNewPassword
) {

}
