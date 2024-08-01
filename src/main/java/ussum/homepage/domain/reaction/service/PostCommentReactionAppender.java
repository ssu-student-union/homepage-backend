package ussum.homepage.domain.reaction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.domain.reaction.PostCommentReaction;
import ussum.homepage.domain.reaction.PostCommentReactionRepository;

@Service
@RequiredArgsConstructor
public class PostCommentReactionAppender {
    private final PostCommentReactionRepository postCommentReactionRepository;

    public PostCommentReaction createPostCommentReaction(PostCommentReaction postCommentReaction) {
        return postCommentReactionRepository.save(postCommentReaction);
    }

}
