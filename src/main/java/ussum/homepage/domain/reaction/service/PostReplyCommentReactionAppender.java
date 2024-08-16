package ussum.homepage.domain.reaction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.reaction.PostReplyCommentReaction;
import ussum.homepage.domain.reaction.PostReplyCommentReactionRepository;

@Service
@RequiredArgsConstructor
public class PostReplyCommentReactionAppender {
    private final PostReplyCommentReactionRepository postReplyCommentReactionRepository;

    public PostReplyCommentReaction createPostReplyCommentReaction(PostReplyCommentReaction postReplyCommentReaction) {
        return postReplyCommentReactionRepository.save(postReplyCommentReaction);
    }
}
