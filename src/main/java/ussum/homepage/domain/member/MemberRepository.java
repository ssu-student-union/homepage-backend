package ussum.homepage.domain.member;

import java.util.Optional;

public interface MemberRepository {
    Optional<Member> findByUserId(Long userId);
    Optional<Member> findCentralOperationCommitteeMember(Long userId);

}
