package ussum.homepage.infra.jpa.user;

import org.springframework.stereotype.Component;
import ussum.homepage.domain.user.User;
import ussum.homepage.infra.jpa.user.entity.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public User toDomain(UserEntity userEntity) {
        return User.of(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getNickname(),
                userEntity.getStudentId(),
                userEntity.getKakaoId(),
                userEntity.getProfileImage(),
                userEntity.getAccountId(),
                userEntity.getPassword(),
                userEntity.getCreatedAt(),
                userEntity.getUpdatedAt(),
                userEntity.getRefreshToken()
        );
    }

    public UserEntity toEntity(User user) {
        return UserEntity.of(
                user.getId(),
                user.getName(),
                user.getNickname(),
                user.getStudentId(),
                user.getKakaoId(),
                user.getProfileImage(),
                user.getAccountId(),
                user.getPassword(),
                user.getRefreshToken()
        );
    }
}
