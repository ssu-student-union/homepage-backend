package ussum.homepage.domain.comment.service.formatter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.application.comment.service.dto.response.PostCommentResponse;
import ussum.homepage.application.comment.service.dto.response.PostReplyCommentResponse;
import ussum.homepage.domain.comment.PostComment;
import ussum.homepage.domain.comment.PostReplyComment;
import ussum.homepage.domain.comment.service.PostCommentReader;
import ussum.homepage.domain.comment.service.PostReplyCommentReader;
import ussum.homepage.domain.member.Member;
import ussum.homepage.domain.member.exception.MemberNotFoundException;
import ussum.homepage.domain.member.service.MemberReader;
import ussum.homepage.domain.reaction.service.PostCommentReactionManager;
import ussum.homepage.domain.reaction.service.PostCommentReactionReader;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.service.UserReader;
import ussum.homepage.domain.user.service.formatter.UserFormatter;

import java.util.List;
import java.util.Optional;

import static ussum.homepage.global.error.status.ErrorStatus.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PostCommentFormatter implements ussum.homepage.domain.comment.service.PostCommentFormatter {
    private final UserReader userReader;
    private final MemberReader memberReader;
    private final PostCommentReactionManager postCommentReactionManager;
    private final PostReplyCommentFormatter postReplyCommentFormatter;
    private final PostReplyCommentReader postReplyCommentReader;
    private final PostCommentReader postCommentReader;

    @Override
    public PostCommentResponse format(Long postId, Long userId, String commentType) {
        PostComment postComment = postCommentReader.getPostCommentWithPostIdAndUserId(postId, userId);
        return format(postComment, userId);
    }

    @Override
    public PostCommentResponse format(PostComment postComment, Long userId) {
        Integer likeCountOfPostComment = postCommentReactionManager.getLikeCountOfPostComment(postComment.getId());
        User user = userReader.getUserWithId(postComment.getUserId());
        Boolean isLiked = (userId != null && postCommentReactionManager.validatePostCommentReactionByCommentIdAndUserId(postComment.getId(),
                userId, "like"));

        List<PostReplyComment> postReplyComments = postReplyCommentReader.getPostReplyCommentListWithCommentId(postComment.getId());
        List<PostReplyCommentResponse> postReplyCommentResponses = postReplyComments.stream()
                .map(replyComment -> postReplyCommentFormatter.format(replyComment, userId))
                .toList();

        Boolean isAuthor = userId != null && userId.equals(postComment.getUserId());

        List<Member> members = memberReader.getMembersWithUserId(userId);
        Optional<Member> firstMember = members.stream().findFirst();
        if (firstMember.isEmpty()) {
            throw new MemberNotFoundException(MEMBER_NOT_FOUND);
        }

        return PostCommentResponse.of(postComment, user, firstMember.get(), postComment.getCommentType(), likeCountOfPostComment, isAuthor, isLiked, postReplyCommentResponses);
    }
}
