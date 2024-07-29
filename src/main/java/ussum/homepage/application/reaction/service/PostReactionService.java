package ussum.homepage.application.reaction.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.application.reaction.service.dto.request.CreatePostReactionReq;
import ussum.homepage.domain.postlike.PostReaction;
import ussum.homepage.domain.postlike.exception.PostReactionException;
import ussum.homepage.domain.postlike.service.PostReactionAppender;
import ussum.homepage.domain.postlike.service.PostReactionReader;
import ussum.homepage.domain.reaction.service.PostReactionModifier;


import java.util.Optional;

import static ussum.homepage.global.error.status.ErrorStatus.POST_REACTION_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostReactionService {

    private final PostReactionAppender postReactionAppender;
    private final PostReactionReader postReactionReader;
    private final PostReactionModifier postReactionModifier;

    @Transactional
    public void postReactionToggle(Long userId, Long postId, CreatePostReactionReq createPostReactionReq) {
        PostReaction newReaction = createPostReactionReq.toDomain(postId, userId);
        Optional<PostReaction> existingReaction = postReactionReader.getPostReactionByUserIdAndPostId(userId, postId);

        existingReaction.ifPresentOrElse(
                reaction -> handleExistingReaction(reaction, newReaction),
                () -> createNewReaction(newReaction)
        );
    }

    //기존 반응이 있고 같은 종류면 삭제
    //기존 반응이 있고 다른 종류면 업데이트
    private void handleExistingReaction(PostReaction existingReaction, PostReaction newReaction) {
        if (existingReaction.getReaction().equals(newReaction.getReaction())) {
            postReactionModifier.deletePostReaction(existingReaction);
        } else {
            postReactionModifier.updatePostReaction(existingReaction, newReaction);
        }
    }

    private void createNewReaction(PostReaction newReaction) {
        postReactionAppender.createPostReaction(newReaction);
    }
}
