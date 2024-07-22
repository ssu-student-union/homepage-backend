package ussum.homepage.domain.postlike.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.application.reaction.service.dto.request.PostReactionCreateRequest;
import ussum.homepage.domain.postlike.PostReaction;
import ussum.homepage.domain.postlike.PostReactionRepository;

@Service
@RequiredArgsConstructor
public class PostReactionAppender {
    private final PostReactionRepository postReactionRepository;

    @Transactional
    public PostReaction createPostReaction(PostReaction postReaction) {
        return postReactionRepository.save(postReaction);
    }
}
