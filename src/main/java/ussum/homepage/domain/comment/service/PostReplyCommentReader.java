package ussum.homepage.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.comment.PostReplyComment;
import ussum.homepage.domain.comment.PostReplyCommentRepository;
import ussum.homepage.domain.reaction.exception.PostCommentReactionException;

import java.util.List;

import static ussum.homepage.global.error.status.ErrorStatus.POST_REPLY_COMMENT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PostReplyCommentReader {
    private final PostReplyCommentRepository postReplyCommentRepository;

    public List<PostReplyComment> getPostReplyCommentListWithCommentId(Long commentId) {
        return postReplyCommentRepository.findAllByCommentId(commentId);
    }

    public PostReplyComment getPostReplyComment(Long replyCommentId) {
        return postReplyCommentRepository.findById(replyCommentId)
                .orElseThrow(() -> new PostCommentReactionException(POST_REPLY_COMMENT_NOT_FOUND));
    }

}
