package ussum.homepage.domain.comment.service.formatter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.application.comment.service.dto.response.PostReplyCommentResponse;
import ussum.homepage.domain.comment.PostReplyComment;
import ussum.homepage.domain.comment.service.PostReplyCommentReader;
import ussum.homepage.domain.member.Member;
import ussum.homepage.domain.member.exception.MemberNotFoundException;
import ussum.homepage.domain.member.service.MemberReader;
import ussum.homepage.domain.reaction.service.PostReplyCommentReactionManager;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.service.UserReader;

import java.util.List;
import java.util.Optional;

import static ussum.homepage.global.error.status.ErrorStatus.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PostReplyCommentFormatter implements ussum.homepage.domain.comment.service.PostReplyCommentFormatter {
    private final UserReader userReader;
    private final MemberReader memberReader;
    private final PostReplyCommentReader postReplyCommentReader;
    private final PostReplyCommentReactionManager postReplyCommentReactionManager;


//    @Override
//    public PostReplyCommentResponse format(Long commentId, Long userId) {
//        PostReplyComment postReplyComment = postReplyCommentReader.getPostReplyComment(commentId);
//        User user = userReader.getUserWithId(postReplyComment.getUserId());
//        Integer likeCount = postReplyCommentReactionManager.getLikeCountOfPostReplyComment(postReplyComment.getId());
//        return PostReplyCommentResponse.of(postReplyComment, user.getName(), likeCount);
//    }

    @Override
    public PostReplyCommentResponse format(PostReplyComment postReplyComment, Long userId) {
//        PostReplyComment postReplyComment = postReplyCommentReader.getPostReplyComment(commentId);
        User user = userReader.getUserWithId(postReplyComment.getUserId());
        Boolean isLiked = (userId != null && postReplyCommentReactionManager.validatePostReplyCommentReactionByReplyCommentIdAndUserId(postReplyComment.getId(),
                userId, "like"));
        Integer likeCount = postReplyCommentReactionManager.getLikeCountOfPostReplyComment(postReplyComment.getId());
        Boolean isAuthor = userId != null && userId.equals(postReplyComment.getUserId());

        List<Member> members = memberReader.getMembersWithUserId(userId);
        Optional<Member> firstMember = members.stream().findFirst();
        if (firstMember.isEmpty()) {
            throw new MemberNotFoundException(MEMBER_NOT_FOUND);
        }

        return PostReplyCommentResponse.of(postReplyComment, user, firstMember.get(), likeCount, isAuthor, isLiked);
    }
}
