package ussum.homepage.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.application.comment.service.dto.request.PostCommentUpdateRequest;
import ussum.homepage.domain.comment.PostComment;
import ussum.homepage.domain.comment.PostCommentRepository;

@Service
@RequiredArgsConstructor
public class PostCommentModifier {
    private final PostCommentRepository postCommentRepository;
    private final PostCommentReader postCommentReader;

    //    public PostComment updateComment(Long userId, Long postId, Long commentId, String commentType, PostCommentUpdateRequest postCommentUpdateRequest) {
//        return postCommentRepository.update(postCommentUpdateRequest.toDomain(commentId, postId, userId, commentType));
//    }
    public PostComment updateComment(PostComment postComment, Long userId, Long commentId, PostCommentUpdateRequest postCommentUpdateRequest) {
        return postCommentRepository.update(postCommentUpdateRequest.toDomain(postComment, commentId, userId));
    }
    public void deleteComment(Long commentId) {
        postCommentRepository.delete(postCommentReader.getPostComment(commentId));
    }

    public void deleteCommentWithoutCommentType(Long commentId) {
        postCommentRepository.deleteWithoutCommentType(postCommentReader.getPostComment(commentId));
    }

    public void deleteCommentWithUserId(Long userId) {
        postCommentRepository.deleteAllByUserId(userId);
    }
}