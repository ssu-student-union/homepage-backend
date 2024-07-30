package ussum.homepage.domain.comment.service.formatter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.application.comment.service.dto.PostCommentResponse;
import ussum.homepage.domain.comment.PostComment;
import ussum.homepage.domain.comment.service.PostCommentReader;
import ussum.homepage.domain.post.service.formatter.PostFormatter;
import ussum.homepage.domain.reaction.service.PostCommentReactionManager;
import ussum.homepage.domain.reaction.service.PostCommentReactionReader;
import ussum.homepage.domain.user.service.formatter.UserFormatter;

@Service
@RequiredArgsConstructor
public class PostCommentFormatter implements ussum.homepage.domain.comment.service.PostCommentFormatter {
    private final PostCommentReader postCommentReader;
    private final PostCommentReactionReader postCommentReactionReader;
    private final PostCommentReactionManager postCommentReactionManager;
    private final PostFormatter postFormatter;
    private final UserFormatter userFormatter;
    @Override
    public PostCommentResponse format(Long postId, Long userId, String type){
        userId = 1L; //테스트를 위한 임시 userId
        final PostComment postComment = postCommentReader.getPostCommentWithPostIdAndUserId(postId, userId);
        Integer likeCountOfPostComment = postCommentReactionManager.getLikeCountOfPostComment(postComment.getId());
        return PostCommentResponse.of(postComment, postFormatter.format(postComment.getPostId()), userFormatter.format(postComment.getUserId()), type, likeCountOfPostComment);
    }
}
