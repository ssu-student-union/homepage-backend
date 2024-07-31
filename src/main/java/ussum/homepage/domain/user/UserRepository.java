package ussum.homepage.domain.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long userId);
    Optional<User> findByStudentId(Long studentId);
    User save(User user);
    Optional<User> findBykakaoId(String kakaoId);
}
