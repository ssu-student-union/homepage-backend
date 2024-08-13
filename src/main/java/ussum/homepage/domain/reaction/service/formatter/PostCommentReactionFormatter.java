package ussum.homepage.domain.reaction.service.formatter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.application.reaction.service.dto.response.PostCommentReactionResponse;
import ussum.homepage.domain.comment.PostComment;
import ussum.homepage.domain.comment.service.PostCommentFormatter;
import ussum.homepage.domain.post.service.formatter.PostFormatter;
import ussum.homepage.domain.user.service.formatter.UserFormatter;

@Service
@RequiredArgsConstructor
public class PostCommentReactionFormatter implements ussum.homepage.domain.reaction.service.PostCommentReactionFormatter {
    private final UserFormatter userFormatter;
    private final PostCommentFormatter postCommentFormatter;
//    private final PostCommentReactionReader postCommentReactionReader;

    @Override
    public PostCommentReactionResponse format(Long id, Long postCommentId, Long postId, Long userId, String type, String reaction) {
        return PostCommentReactionResponse.of(
                id,
                postCommentFormatter.format(null, userId), //일단 지금은 필요없는 코드, 보류
                userFormatter.format(userId),
                reaction
        );
    }

}
