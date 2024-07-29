package ussum.homepage.domain.reaction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.postlike.PostReaction;
import ussum.homepage.domain.postlike.PostReactionRepository;

@Service
@RequiredArgsConstructor
public class PostReactionModifier {

    private final PostReactionRepository postReactionRepository;

    public void deletePostReaction(PostReaction postReaction){
        postReactionRepository.delete(postReaction);
    }

    public void updatePostReaction(PostReaction existingReaction, PostReaction newReaction){
        existingReaction.updateReaction(newReaction);
        postReactionRepository.save(existingReaction);
    }
}
