package ussum.homepage.application.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.application.comment.service.dto.request.PostReplyCommentCreateRequest;
import ussum.homepage.application.comment.service.dto.response.PostReplyCommentResponse;
import ussum.homepage.domain.comment.PostReplyComment;
import ussum.homepage.domain.comment.service.PostReplyCommentAppender;
import ussum.homepage.domain.comment.service.PostReplyCommentFormatter;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostReplyCommentService {
    private final PostReplyCommentAppender postReplyCommentAppender;
    private final PostReplyCommentFormatter postReplyCommentFormatter;

    @Transactional
    public PostReplyCommentResponse createReplyComment(Long userId, Long commentId, PostReplyCommentCreateRequest postReplyCommentCreateRequest) {
        PostReplyComment postReplyComment = postReplyCommentAppender.createPostReplyComment(postReplyCommentCreateRequest.toDomain(userId, commentId));
//        return postReplyCommentFormatter.format(postReplyComment.getCommentId(), postReplyComment.getUserId());
        return postReplyCommentFormatter.format(postReplyComment);
    }
}
