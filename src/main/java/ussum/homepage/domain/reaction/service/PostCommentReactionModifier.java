package ussum.homepage.domain.reaction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.postlike.PostReaction;
import ussum.homepage.domain.reaction.PostCommentReaction;
import ussum.homepage.domain.reaction.PostCommentReactionRepository;

@Service
@RequiredArgsConstructor
public class PostCommentReactionModifier {
    private final PostCommentReactionRepository postCommentReactionRepository;

    public void deletePostCommentReaction(PostCommentReaction postCommentReaction) {
        postCommentReactionRepository.delete(postCommentReaction);
    }

    public void updatePostCommentReaction(PostCommentReaction existingReaction, PostCommentReaction newReaction){
        existingReaction.updateCommentReaction(newReaction);
        postCommentReactionRepository.save(existingReaction);
    }
}
