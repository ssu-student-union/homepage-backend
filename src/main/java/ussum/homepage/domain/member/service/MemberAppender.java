package ussum.homepage.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.application.admin.service.dto.request.CouncilSignInRequest;
import ussum.homepage.domain.group.Group;
import ussum.homepage.domain.member.Member;
import ussum.homepage.domain.member.MemberRepository;
import ussum.homepage.domain.user.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberAppender {
    private final MemberRepository memberRepository;

    public void saveMember(Member member) {
        memberRepository.save(member);
    }

    public void saveMemberList(List<Group> groupList, CouncilSignInRequest request, User savedUser) {
        List<Member> memberList = groupList.stream()
                .map(group -> Member.of(
                        null,
                        true,
                        request.memberCode(),
                        request.majorCode(),
                        savedUser.getId(),
                        group.getId()
                ))
                .toList();

        memberList.forEach(this::saveMember);
    }
}
