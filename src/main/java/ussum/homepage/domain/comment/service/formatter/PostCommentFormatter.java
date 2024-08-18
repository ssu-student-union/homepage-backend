package ussum.homepage.domain.comment.service.formatter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.application.comment.service.dto.response.PostCommentResponse;
import ussum.homepage.application.comment.service.dto.response.PostReplyCommentResponse;
import ussum.homepage.domain.comment.PostComment;
import ussum.homepage.domain.comment.PostReplyComment;
import ussum.homepage.domain.comment.service.PostCommentReader;
import ussum.homepage.domain.comment.service.PostReplyCommentReader;
import ussum.homepage.domain.reaction.service.PostCommentReactionManager;
import ussum.homepage.domain.reaction.service.PostCommentReactionReader;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.service.UserReader;
import ussum.homepage.domain.user.service.formatter.UserFormatter;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostCommentFormatter implements ussum.homepage.domain.comment.service.PostCommentFormatter {
    private final UserReader userReader;
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

        List<PostReplyComment> postReplyComments = postReplyCommentReader.getPostReplyCommentListWithCommentId(postComment.getId());
        List<PostReplyCommentResponse> postReplyCommentResponses = postReplyComments.stream()
                .map(replyComment -> postReplyCommentFormatter.format(replyComment, userId))
                .toList();

        Boolean isAuthor = userId != null && userId.equals(postComment.getUserId());

        return PostCommentResponse.of(postComment, user.getName(), postComment.getCommentType(), likeCountOfPostComment, isAuthor, postReplyCommentResponses);
    }
}
