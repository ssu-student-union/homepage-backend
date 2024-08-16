package ussum.homepage.domain.reaction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.reaction.PostReplyCommentReaction;
import ussum.homepage.domain.reaction.PostReplyCommentReactionRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostReplyCommentReactionReader {
    private final PostReplyCommentReactionRepository postReplyCommentReactionRepository;

    public Optional<PostReplyCommentReaction> getPostReplyCommentReactionByUserIdAndReplyCommentId(Long userId, Long replyCommentId) {
        return postReplyCommentReactionRepository.findByUserIdAndReplyCommentId(userId, replyCommentId);
    }


    public Integer getLikeCountByReplyCommentId(Long replyCommentId) {
        return postReplyCommentReactionRepository.findAllByReplyCommentId(replyCommentId).size();
    }

}
