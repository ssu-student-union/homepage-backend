package ussum.homepage.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.member.MemberRepository;
import ussum.homepage.infra.jpa.comment.entity.CommentType;


@Service
@RequiredArgsConstructor
public class MemberManager {
    private final MemberRepository memberRepository;

    public Boolean validMemberWithUserId(Long userId) {
        return memberRepository.findByUserId(userId)
                .map(member -> "CENTRAL_OPERATION_COMMITTEE".equals(member.getMemberCode()))
                .orElse(false);
    }

    public String getCommentType(Long userId) {
        return memberRepository.findCentralOperationCommitteeMember(userId)
                .map(member -> CommentType.OFFICIAL.getStringCommentType())
                .orElse(CommentType.GENERAL.getStringCommentType());
    }

}
