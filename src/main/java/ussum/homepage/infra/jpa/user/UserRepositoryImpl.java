package ussum.homepage.infra.jpa.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.UserRepository;
import ussum.homepage.infra.jpa.user.repository.UserJpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;
    @Override
    public Optional<User> findById(Long userId) {
        return userJpaRepository.findById(userId).map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByStudentId(Long studentId) { return userJpaRepository.findByStudentId(studentId).map(userMapper::toDomain); }

    @Override
    public User save(User user) {
        return userMapper.toDomain(userJpaRepository.save(userMapper.toEntity(user)));
    }


    @Override
    public Optional<User> findBykakaoId(String kakaoId){
        return userJpaRepository.findByKakaoId(kakaoId).map(userMapper::toDomain);
    }
}
