package ussum.homepage.infra.jpa.user;

import org.springframework.stereotype.Component;
import ussum.homepage.domain.user.User;
import ussum.homepage.infra.jpa.user.entity.UserEntity;

@Component
public class UserMapper {
    public User toDomain(UserEntity userEntity) {
        return User.of(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getStudentId(),
                userEntity.getProfileImage(),
                userEntity.getKakaoId(),
                userEntity.getCreatedAt(),
                userEntity.getUpdatedAt()
        );
    }

    public UserEntity toEntity(User user) {
        return UserEntity.of(
                user.getId(),
                user.getName(),
                user.getStudentId(),
                user.getKakaoId(),
                user.getProfileImage()
        );
    }
}
