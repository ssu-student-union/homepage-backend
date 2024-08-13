package ussum.homepage.domain.reaction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.reaction.PostReplyCommentReaction;
import ussum.homepage.domain.reaction.PostReplyCommentReactionRepository;

@Service
@RequiredArgsConstructor
public class PostReplyCommentReactionModifier {
    private final PostReplyCommentReactionRepository postReplyCommentReactionRepository;

    public void deletePostReplyCommentReaction(PostReplyCommentReaction postReplyCommentReaction) {
        postReplyCommentReactionRepository.delete(postReplyCommentReaction);
    }

    public void updatePostReplyCommentReaction(PostReplyCommentReaction existingReaction, PostReplyCommentReaction newReaction){
        existingReaction.updateReplyCommentReaction(newReaction);
        postReplyCommentReactionRepository.save(existingReaction);
    }
}
