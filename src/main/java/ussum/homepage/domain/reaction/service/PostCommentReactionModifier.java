package ussum.homepage.domain.reaction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.reaction.PostCommentReaction;
import ussum.homepage.domain.reaction.PostCommentReactionRepository;

@Service
@RequiredArgsConstructor
public class PostCommentReactionModifier {
    private final PostCommentReactionRepository postCommentReactionRepository;

    public void deletePostCommentReaction(PostCommentReaction postCommentReaction) {
        postCommentReactionRepository.delete(postCommentReaction);
    }
}
