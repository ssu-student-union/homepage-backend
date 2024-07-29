package ussum.homepage.domain.postlike.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.postlike.PostReaction;
import ussum.homepage.domain.postlike.PostReactionRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostReactionReader {
    private final PostReactionRepository postReactionRepository;

    public Optional<PostReaction> getPostReactionByUserIdAndPostId(Long userId, Long postId){
        return postReactionRepository.findByUserIdAndPostId(userId,postId);
    }
}
