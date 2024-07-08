package ussum.homepage.application.user.service.dto.response;

import ussum.homepage.domain.user.User;

public record UserResponse(
        Long id,
        String name,
        Long studentId,
        Boolean isFirst,
        String profileImage,
        String createdAt
) {
    public static UserResponse of(User user, Boolean isFirst) {
        return new UserResponse(user.getId(), user.getName(), user.getStudentId(), isFirst, user.getProfileImage(), user.getCreatedAt());
    }
}
