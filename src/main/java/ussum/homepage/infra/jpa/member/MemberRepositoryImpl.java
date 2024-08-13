package ussum.homepage.infra.jpa.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ussum.homepage.domain.member.Member;
import ussum.homepage.domain.member.MemberRepository;
import ussum.homepage.infra.jpa.member.repository.MemberJpaRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {
    private final MemberJpaRepository memberJpaRepository;
    private final MemberMapper memberMapper;


    @Override
    public Optional<Member> findByUserId(Long userId) {
        return memberJpaRepository.findByUserId(userId).map(memberMapper::toDomain);
    }
}
