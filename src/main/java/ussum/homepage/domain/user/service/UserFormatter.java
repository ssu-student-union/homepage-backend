package ussum.homepage.domain.user.service;

import ussum.homepage.application.user.service.dto.response.UserResponse;

public interface UserFormatter {
    UserResponse format(Long userId);
}
