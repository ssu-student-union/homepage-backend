package ussum.homepage.infra.jpa.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ussum.homepage.domain.member.Member;
import ussum.homepage.domain.member.MemberRepository;
import ussum.homepage.infra.jpa.member.repository.MemberJpaRepository;

import java.util.List;
import java.util.Optional;

import static ussum.homepage.infra.jpa.member.entity.MemberCode.CENTRAL_OPERATION_COMMITTEE;
import static ussum.homepage.infra.jpa.member.entity.MemberCode.STUDENT_HUMAN_RIGHTS_COMMITTEE;
import static ussum.homepage.infra.jpa.group.entity.GroupCode.STUDENT_GOVERNMENT_ORGANIZATION;

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
    public Optional<Member> findByUserIdAndGroupId(Long userId, Long groupId) {
        return memberJpaRepository.findByUserIdAndGroupId(userId, groupId).map(memberMapper::toDomain);
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
    public List<Member> findStudentHumanRightsCommitteeMember(Long userId) {
        return memberJpaRepository.findAllByUserId(userId)
                .stream()
                .filter(memberEntity -> memberEntity.getMemberCode().equals(STUDENT_HUMAN_RIGHTS_COMMITTEE))
                .map(memberMapper::toDomain)
                .toList();
    }

    @Override
    public List<Member> findSuggestionCommitteeMember(Long userId) {
        return memberJpaRepository.findAllByUserId(userId)
                .stream()
                .filter(memberEntity -> {
                    if (memberEntity.getGroupEntity() == null || memberEntity.getGroupEntity().getGroupCode() == null) {
                        return false;
                    }
                    return memberEntity.getGroupEntity().getGroupCode().equals(STUDENT_GOVERNMENT_ORGANIZATION);
                })
                .map(memberMapper::toDomain)
                .toList();
    }

    @Override
    public void save(Member member) {
        memberJpaRepository.save(memberMapper.toEntity(member));
    }
}
