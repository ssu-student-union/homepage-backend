package ussum.homepage.domain.user;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long userId);
    User save(User user);
    Optional<User> findBykakaoId(Long kakaoId);
}
