package ussum.homepage.application.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.application.comment.service.dto.request.PostReplyCommentCreateRequest;
import ussum.homepage.application.comment.service.dto.request.PostReplyCommentUpdateRequest;
import ussum.homepage.application.comment.service.dto.response.PostReplyCommentResponse;
import ussum.homepage.domain.comment.PostReplyComment;
import ussum.homepage.domain.comment.service.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostReplyCommentService {
    private final PostReplyCommentAppender postReplyCommentAppender;
    private final PostReplyCommentModifier postReplyCommentModifier;
    private final PostReplyCommentFormatter postReplyCommentFormatter;
    private final PostCommentReader postCommentReader;
    private final PostReplyCommentReader postReplyCommentReader;

    @Transactional
    public PostReplyCommentResponse createReplyComment(Long userId, Long commentId, PostReplyCommentCreateRequest postReplyCommentCreateRequest) {
        PostReplyComment postReplyComment = postReplyCommentAppender.createPostReplyComment(postReplyCommentCreateRequest.toDomain(userId, commentId));
//        return postReplyCommentFormatter.format(postReplyComment.getCommentId(), postReplyComment.getUserId());
        return postReplyCommentFormatter.format(postReplyComment);
    }

    @Transactional
    public PostReplyCommentResponse editReplyComment(Long userId, Long commentId, Long replyCommentId, PostReplyCommentUpdateRequest postReplyCommentUpdateRequest) {
        PostReplyComment postReplyComment = postReplyCommentReader.getPostReplyComment(replyCommentId);
        return postReplyCommentFormatter.format(postReplyCommentModifier.updatePostReplyComment(postReplyComment, userId, commentId, postReplyCommentUpdateRequest));
    }

    @Transactional
    public void deleteReplyComment(Long userId, Long replyCommentId) {
        postReplyCommentModifier.deletePostReplyComment(replyCommentId); //일단은 자기것만 삭제
    }
}
