package ussum.homepage.domain.member;

import ussum.homepage.infra.jpa.member.entity.MemberEntity;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Optional<Member> findByUserId(Long userId);
    Optional<Member> findCentralOperationCommitteeMember(Long userId);
    void save(Member member);
    List<Member> findAllByUserId(Long userId);
}
