package ussum.homepage.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.domain.comment.PostComment;
import ussum.homepage.domain.comment.PostCommentRepository;

@Service
@RequiredArgsConstructor
public class PostCommentAppender {
    private final PostCommentRepository postCommentRepository;

    public PostComment createPostComment(PostComment postComment){
        return postCommentRepository.save(postComment);
    }
}
