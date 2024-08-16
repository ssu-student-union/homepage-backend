package ussum.homepage.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.comment.PostComment;
import ussum.homepage.domain.comment.PostCommentRepository;
import ussum.homepage.domain.comment.service.formatter.PostCommentFormatter;
import ussum.homepage.domain.reaction.exception.PostCommentException;
import ussum.homepage.global.error.exception.InvalidValueException;

import java.util.List;

import static ussum.homepage.global.error.status.ErrorStatus.POST_COMMENT_NOT_FOUND;
import static ussum.homepage.global.error.status.ErrorStatus._BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class PostCommentReader {
    private final PostCommentRepository postCommentRepository;

    public Page<PostComment> getPostCommentList(Pageable pageable, Long postId){
        return postCommentRepository.findAllByPostId(pageable, postId);
    }
    public PostComment getPostCommentWithPostIdAndUserId(Long postId, Long userId){
        return postCommentRepository.findByPostIdAndUserId(postId, userId)
                .orElseThrow(() -> new PostCommentException(POST_COMMENT_NOT_FOUND));
    }
    public PostComment getPostComment(Long commentId){
        return postCommentRepository.findById(commentId)
                .orElseThrow(() -> new PostCommentException(POST_COMMENT_NOT_FOUND));
    }

    public List<PostComment> getCommentListWithPostId(Long postId) {
        return postCommentRepository.findAllByPostId(postId);
    }

    public List<PostComment> getCommentListWithPostIdAndType(Long postId, String type) {
        List<PostComment> comments;
        switch (type) {
            case "인기순":
                comments = postCommentRepository.findAllByPostIdOrderByLikesDesc(postId);
                break;
            case "최신순":
                comments = postCommentRepository.findAllByPostIdOrderByCreatedAtDesc(postId);
                break;
            default:
                throw new InvalidValueException(_BAD_REQUEST);
        }
        if (comments.isEmpty()) {
            throw new PostCommentException(POST_COMMENT_NOT_FOUND);
        }
        return comments;
    }

    public List<PostComment> getCommentListWithPostIdAndCommentType(Long userId, Long postId, String commentType) {
        return postCommentRepository.findAllByPostIdAndCommentType(postId, commentType);
    }

}
