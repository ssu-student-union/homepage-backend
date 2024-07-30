package ussum.homepage.domain.postlike.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.postlike.PostReactionRepository;
import ussum.homepage.domain.postlike.service.exception.PostReactionException;

import static ussum.homepage.global.error.status.ErrorStatus.POST_REACTION_IS_ALREADY_EXIST;

@Service
@RequiredArgsConstructor
public class PostReactionManager {
    private final PostReactionRepository postReactionRepository;

    public void validatePostReactionByCommentIdAndUserId(Long postId, Long userId, String reaction) {
        postReactionRepository.findByPostIdAndUserId(postId, userId, reaction)
                .ifPresent(postReaction -> {
                    throw new PostReactionException(POST_REACTION_IS_ALREADY_EXIST);
                });
    }
}
