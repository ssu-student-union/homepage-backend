package ussum.homepage.infra.jpa.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ussum.homepage.application.user.service.dto.request.OnBoardingRequest;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.UserRepository;
import ussum.homepage.infra.jpa.user.repository.UserJpaRepository;

import java.util.Optional;

import static ussum.homepage.infra.jpa.user.entity.QUserEntity.userEntity;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final JPAQueryFactory queryFactory;
    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;
    @Override
    public Optional<User> findById(Long userId) {
        return userJpaRepository.findById(userId).map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByStudentId(String studentId) { return userJpaRepository.findByStudentId(studentId).map(userMapper::toDomain); }

    @Override
    public Optional<User> findBykakaoId(String kakaoId){
        return userJpaRepository.findByKakaoId(kakaoId).map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByAccountId(String accountId) {
        return userJpaRepository.findByAccountId(accountId).map(userMapper::toDomain);
    }

    @Override
    public User save(User user) {
        return userMapper.toDomain(userJpaRepository.save(userMapper.toEntity(user)));
    }

    @Override
    public void updateOnBoardingUser(Long userId, OnBoardingRequest request) {
        queryFactory
                .update(userEntity)
                .set(userEntity.studentId, request.getStudentId())
                .set(userEntity.name, request.getName())
                .where(userEntity.id.eq(userId))
                .execute();
    }

    @Override
    public void deleteUser(Long userId) {
        userJpaRepository.deleteById(userId);
    }
}
