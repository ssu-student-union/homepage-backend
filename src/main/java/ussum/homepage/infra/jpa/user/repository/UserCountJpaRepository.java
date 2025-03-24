package ussum.homepage.infra.jpa.user.repository;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import ussum.homepage.infra.jpa.user.entity.UserEntity;

public interface UserCountJpaRepository extends JpaRepository<UserEntity, Long> {

    Long countByCreatedAt(LocalDateTime createdAt);

    Long countByCreatedAtAfter(LocalDateTime date);
}
