package ussum.homepage.application.user.service.dto.request;

import ussum.homepage.domain.user.User;

public record UserRequest(
        Long id,
        String name,
        String studentId,
        String profileImage
) {
    public User toDomain(){
        return User.of(
                null,
                name,
                studentId,
                profileImage,
                null,
                null,
                null,
                null
        );
    }

}


