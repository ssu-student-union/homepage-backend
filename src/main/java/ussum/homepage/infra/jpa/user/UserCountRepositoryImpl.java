package ussum.homepage.infra.jpa.user;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ussum.homepage.domain.user.UserCountRepository;
import ussum.homepage.infra.jpa.user.repository.UserCountJpaRepository;

@RequiredArgsConstructor
@Repository
public class UserCountRepositoryImpl implements UserCountRepository {

    private final UserCountJpaRepository userCountJpaRepository;

    @Override
    public Long count() {
        return userCountJpaRepository.count();
    }

    @Override
    public Long countByDate(LocalDateTime date) {
        return userCountJpaRepository.countByCreatedAt(date);
    }

    @Override
    public Long countByDateAfter(LocalDateTime date) {
        return userCountJpaRepository.countByCreatedAtAfter(date);
    }
}
