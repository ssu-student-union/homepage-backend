package ussum.homepage.application.reaction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.application.reaction.service.dto.request.CreatePostReplyCommentReactionReq;
import ussum.homepage.domain.reaction.PostCommentReaction;
import ussum.homepage.domain.reaction.PostReplyCommentReaction;
import ussum.homepage.domain.reaction.service.PostReplyCommentReactionAppender;
import ussum.homepage.domain.reaction.service.PostReplyCommentReactionModifier;
import ussum.homepage.domain.reaction.service.PostReplyCommentReactionReader;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostReplyCommentReactionService {
    private final PostReplyCommentReactionReader postReplyCommentReactionReader;
    private final PostReplyCommentReactionAppender postReplyCommentReactionAppender;
    private final PostReplyCommentReactionModifier postReplyCommentReactionModifier;

    @Transactional
    public void postReplyCommentReactionToggle(Long userId, Long replyCommentId, CreatePostReplyCommentReactionReq createPostReplyCommentReactionReq) {
        PostReplyCommentReaction newPostReplyCommentReaction = createPostReplyCommentReactionReq.toDomain(replyCommentId, userId);
        Optional<PostReplyCommentReaction> existingPostReplyCommentReaction = postReplyCommentReactionReader.getPostReplyCommentReactionByUserIdAndReplyCommentId(userId, replyCommentId);


        existingPostReplyCommentReaction.ifPresentOrElse(
                replyCommentReaction -> handleExistingReplyCommentReaction(replyCommentReaction, newPostReplyCommentReaction),
                () -> createNewReplyCommentReaction(newPostReplyCommentReaction)
        );
    }

    private void handleExistingReplyCommentReaction(PostReplyCommentReaction existingReaction, PostReplyCommentReaction newReaction) {
        if (existingReaction.getReaction().equals(newReaction.getReaction())) {
            postReplyCommentReactionModifier.deletePostReplyCommentReaction(existingReaction);
        } else {
            postReplyCommentReactionModifier.updatePostReplyCommentReaction(existingReaction, newReaction);
        }
    }

    private void createNewReplyCommentReaction(PostReplyCommentReaction newReplyCommentReaction) {
        postReplyCommentReactionAppender.createPostReplyCommentReaction(newReplyCommentReaction);
    }

}
