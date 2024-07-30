package ussum.homepage.domain.postlike.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.postlike.PostReaction;
import ussum.homepage.domain.postlike.PostReactionRepository;
import ussum.homepage.domain.postlike.service.exception.PostReactionException;

import static ussum.homepage.global.error.status.ErrorStatus.POST_REACTION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PostReactionReader {
    private final PostReactionRepository postReactionRepository;

    public PostReaction getPostReactionWithPostIdAndUserId(Long postId, Long userId, String reaction) {
        return postReactionRepository.findByPostIdAndUserId(postId, userId, reaction)
                .orElseThrow(() -> new PostReactionException(POST_REACTION_NOT_FOUND));
    }

}