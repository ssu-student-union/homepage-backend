package ussum.homepage.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.UserRepository;

@Service
@RequiredArgsConstructor
public class UserModifier {
    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }
}