package ussum.homepage.domain.reaction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.reaction.PostCommentReactionRepository;
import ussum.homepage.domain.reaction.PostReplyCommentReactionRepository;

@Service
@RequiredArgsConstructor
public class PostReplyCommentReactionManager {
    private final PostReplyCommentReactionRepository postReplyCommentReactionRepository;

    public Integer getLikeCountOfPostReplyComment(Long replyCommentId) {
        return postReplyCommentReactionRepository.findAllByReplyCommentId(replyCommentId).size();
    }
}
