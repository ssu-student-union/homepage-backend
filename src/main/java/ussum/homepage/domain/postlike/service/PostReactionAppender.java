package ussum.homepage.domain.postlike.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.postlike.PostReaction;
import ussum.homepage.domain.postlike.PostReactionRepository;

@Service
@RequiredArgsConstructor
public class PostReactionAppender {
    private final PostReactionRepository postReactionRepository;

    public PostReaction createPostReaction(PostReaction postReaction) {
        return postReactionRepository.save(postReaction);
    }
}
