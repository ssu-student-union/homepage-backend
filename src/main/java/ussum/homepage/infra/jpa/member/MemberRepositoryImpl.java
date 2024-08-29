package ussum.homepage.infra.jpa.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ussum.homepage.domain.member.Member;
import ussum.homepage.domain.member.MemberRepository;
import ussum.homepage.infra.jpa.member.repository.MemberJpaRepository;

import java.util.List;
import java.util.Optional;

import static ussum.homepage.infra.jpa.member.entity.MemberCode.CENTRAL_OPERATION_COMMITTEE;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {
    private final MemberJpaRepository memberJpaRepository;
    private final MemberMapper memberMapper;

    @Override
    public Optional<Member> findByUserId(Long userId) {
        return memberJpaRepository.findByUserId(userId).map(memberMapper::toDomain);
    }

    @Override
    public List<Member> findAllByUserId(Long userId) {
        return memberJpaRepository.findAllByUserId(userId).stream().map(memberMapper::toDomain).toList();
    }

    @Override
    // MemberCode가 CENTRAL_OPERATION_COMMITTEE인 경우의 Member를 반환
    public List<Member> findCentralOperationCommitteeMember(Long userId) {
        return memberJpaRepository.findAllByUserId(userId)
                .stream()
                .filter(memberEntity -> memberEntity.getMemberCode().equals(CENTRAL_OPERATION_COMMITTEE))
                .map(memberMapper::toDomain)
                .toList();
    }

    @Override
    public void save(Member member) {
        memberJpaRepository.save(memberMapper.toEntity(member));
    }
}
