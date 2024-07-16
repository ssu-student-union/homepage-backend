package ussum.homepage.domain.comment.service.formatter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.application.comment.service.dto.PostCommentResponse;
import ussum.homepage.domain.comment.PostComment;
import ussum.homepage.domain.comment.service.PostCommentReader;
import ussum.homepage.domain.post.service.formatter.PostFormatter;
import ussum.homepage.domain.user.service.formatter.UserFormatter;

@Service
@RequiredArgsConstructor
public class PostCommentFormatter implements ussum.homepage.domain.comment.service.PostCommentFormatter {
    private final PostCommentReader postCommentReader;
    private final PostFormatter postFormatter;
    private final UserFormatter userFormatter;
    @Override
    public PostCommentResponse format(Long postId, Long userId, String type){
        final PostComment postComment = postCommentReader.getPostCommentWithPostIdAndUserId(postId, userId);
        return PostCommentResponse.of(postComment, postFormatter.format(postComment.getPostId()), userFormatter.format(postComment.getUserId()), type);
    }
}
