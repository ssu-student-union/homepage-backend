package ussum.homepage.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.domain.comment.PostComment;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.UserRepository;

@Service
@RequiredArgsConstructor
public class UserAppender {
    private final UserRepository userRepository;

    @Transactional
    public User createUser(User user){
        return userRepository.save(user);
    }
}
