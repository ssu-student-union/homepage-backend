package ussum.homepage.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.member.Member;
import ussum.homepage.domain.member.MemberRepository;
import ussum.homepage.domain.member.exception.MemberNotFoundException;

import java.util.Optional;

import static ussum.homepage.global.error.status.ErrorStatus.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MemberReader {
    private final MemberRepository memberRepository;

    public Member getMemberWithUserId(Long userId) {
        return memberRepository.findByUserId(userId).orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
    }

    public Member getMemberWithUserIdAndGroupId(Long userId, Long groupId) {
        return memberRepository.findByUserIdAndGroupId(userId, groupId).orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
    }

}
