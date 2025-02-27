package ussum.homepage.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.member.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberModifier {
    private final MemberRepository memberRepository;

    public void deleteMemberWithUserId(Long userId) {
        memberRepository.deleteByUserId(userId);
    }

}
