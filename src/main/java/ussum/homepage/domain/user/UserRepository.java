package ussum.homepage.domain.user;

import ussum.homepage.application.user.service.dto.request.OnBoardingRequest;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long userId);
    Optional<User> findByStudentId(String studentId);
    Optional<User> findBykakaoId(String kakaoId);
    Optional<User> findByAccountId(String accountId);
    User save(User user);
    void updateOnBoardingUser(Long userId, OnBoardingRequest request);
}
