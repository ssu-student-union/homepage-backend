package ussum.homepage.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.comment.PostReplyComment;
import ussum.homepage.domain.comment.PostReplyCommentRepository;

@Service
@RequiredArgsConstructor
public class PostReplyCommentAppender {
    private final PostReplyCommentRepository postReplyCommentRepository;

    public PostReplyComment createPostReplyComment(PostReplyComment postReplyComment) {
        return postReplyCommentRepository.save(postReplyComment);
    }
}
