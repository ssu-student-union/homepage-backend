package ussum.homepage.domain.member.service;

import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.member.Member;
import ussum.homepage.domain.member.MemberRepository;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.PostRepository;
import ussum.homepage.infra.jpa.comment.entity.CommentType;
import ussum.homepage.infra.jpa.post.entity.BoardCode;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MemberManager {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

//    public Boolean validMemberWithUserId(Long userId) {
//        return memberRepository.findAllByUserId(userId)
//                .stream()
//                .anyMatch(member -> "CENTRAL_OPERATION_COMMITTEE".equals(member.getMemberCode()));
//    }

    public String getCommentType(Long userId) {
        List<Member> committeeMembers = memberRepository.findCentralOperationCommitteeMember(userId);
        //List<Member> committeeMembers = memberRepository.findOfficialCommentMember(userId,postId);
        if (committeeMembers.isEmpty()) {
            return CommentType.GENERAL.getStringCommentType();
        } else return CommentType.OFFICIAL.getStringCommentType();
    }

    /** 기존 공식 댓글이 청원게시판의 중앙운영위원회 처리밖에 없어 이를 확장함.
       게시판별 공식 댓글이 달릴때 마다 분기문 처리에 대한 의문.
     **/
    public String getCommentType(Long userId, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        List<Member> committeeMembers = getCommitteeMembers(userId, post.getBoardId());
        return committeeMembers.isEmpty()
                ? CommentType.GENERAL.getStringCommentType()
                : CommentType.OFFICIAL.getStringCommentType();
    }

    private List<Member> getCommitteeMembers(Long userId, Long boardId) {
        return switch (BoardCode.getEnumBoardCodeFromBoardId(boardId.intValue())) {
            case PETITION -> memberRepository.findCentralOperationCommitteeMember(userId);
            case SUGGESTION -> memberRepository.findStudentHumanRightsCommitteeMember(userId);
            case RIGHTS -> memberRepository.findSuggestionCommitteeMember(userId);
            default -> new ArrayList<>();
        };
    }
}
