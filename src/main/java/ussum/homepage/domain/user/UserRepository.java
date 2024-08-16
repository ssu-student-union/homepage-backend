package ussum.homepage.domain.user;

import ussum.homepage.application.user.service.dto.request.OnBoardingRequest;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long userId);
    Optional<User> findByStudentId(String studentId);
    User save(User user);
    Optional<User> findBykakaoId(String kakaoId);
    void updateOnBoardingUser(OnBoardingRequest request);
}
