package ussum.homepage.infra.jpa.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ussum.homepage.domain.user.User;
import ussum.homepage.infra.jpa.user.entity.UserEntity;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByKakaoId(Long kakaoId);
}
