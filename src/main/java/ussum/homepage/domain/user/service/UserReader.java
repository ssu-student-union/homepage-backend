package ussum.homepage.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.UserRepository;
import ussum.homepage.global.error.exception.GeneralException;

import java.util.Optional;

import static ussum.homepage.global.error.status.ErrorStatus.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserReader {
    private final UserRepository userRepository;

    public User getUserWithId(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new GeneralException(USER_NOT_FOUND));
    }

    public Optional<User> findBykakaoId(Long kakaoId) {
        return userRepository.findBykakaoId(kakaoId);
    }



}
