package ussum.homepage.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.application.comment.service.dto.request.PostReplyCommentUpdateRequest;
import ussum.homepage.domain.comment.PostReplyComment;
import ussum.homepage.domain.comment.PostReplyCommentRepository;

@Service
@RequiredArgsConstructor
public class PostReplyCommentModifier {
    private final PostReplyCommentRepository postReplyCommentRepository;
    private final PostReplyCommentReader postReplyCommentReader;

    public PostReplyComment updatePostReplyComment(PostReplyComment postReplyComment, Long userId, PostReplyCommentUpdateRequest postReplyCommentUpdateRequest) {
        return postReplyCommentRepository.update(postReplyCommentUpdateRequest.toDomain(postReplyComment, userId));
    }

    public void deletePostReplyComment(Long replyCommentId) {
        postReplyCommentRepository.delete(postReplyCommentReader.getPostReplyComment(replyCommentId));
    }
}