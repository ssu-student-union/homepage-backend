package ussum.homepage.domain.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.application.user.service.dto.request.OnBoardingRequest;
import ussum.homepage.domain.member.Member;
import ussum.homepage.domain.member.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberAppender {
    private final MemberRepository memberRepository;

    public void saveMember(Member member) {
        memberRepository.save(member);
    }
}
