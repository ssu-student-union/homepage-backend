package ussum.homepage.domain.postlike.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.postlike.PostReactionRepository;

@Service
@RequiredArgsConstructor
public class PostReactionModifier {
    private final PostReactionRepository postReactionRepository;
    private final PostReactionReader postReactionReader;

    public void deletePostReaction(Long postId, Long userId, String reaction) {
        postReactionRepository.delete(
                postReactionReader.getPostReactionWithPostIdAndUserId(postId, userId, reaction)
        );

    }

}
