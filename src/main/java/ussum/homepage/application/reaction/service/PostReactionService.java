package ussum.homepage.application.reaction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.application.reaction.service.dto.request.CreatePostReactionReq;
import ussum.homepage.application.reaction.service.dto.request.PostReactionCreateRequest;
import ussum.homepage.domain.post.service.PetitionPostOngoingStatusProcessor;
import ussum.homepage.domain.postlike.PostReaction;
import ussum.homepage.domain.postlike.service.PostReactionAppender;
import ussum.homepage.domain.postlike.service.PostReactionManager;
import ussum.homepage.domain.postlike.service.PostReactionReader;
import ussum.homepage.domain.postlike.service.PostReactionModifier;


import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostReactionService {

    private final PostReactionAppender postReactionAppender;
    private final PostReactionModifier postReactionModifier;
    private final PostReactionManager postReactionManager;
    private final PostReactionReader postReactionReader;
    private final PetitionPostOngoingStatusProcessor petitionPostOngoingStatusProcessor;

    @Transactional
    public void postReactionToggle(Long userId, Long postId, CreatePostReactionReq createPostReactionReq) {
        PostReaction newReaction = createPostReactionReq.toDomain(postId, userId);
        Optional<PostReaction> existingReaction = postReactionReader.getPostReactionByUserIdAndPostId(userId, postId);

        existingReaction.ifPresentOrElse(
                reaction -> handleExistingReaction(reaction, newReaction),
                () -> createNewReaction(newReaction)
        );
        petitionPostOngoingStatusProcessor.onLikeCountChanged(postId);
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

    //일단 반환 값 void말고 responseDto로 해놓고 나중에 없애도 될 것 같음.
//    @Transactional
//    public PostReactionResponse createPostReaction(Long postId, PostReactionCreateRequest postReactionCreateRequest) {
//        Long userId = 1L; //여기에 userId 추출하는 거 추가
//        postReactionManager.validatePostReactionByCommentIdAndUserId(postId, userId, postReactionCreateRequest.reaction());
//        PostReaction postReaction = postReactionAppender.createPostReaction(postReactionCreateRequest.toDomain(postId, userId));
//        return postReactionFormatter.format(
//                postReaction.getId(),
//                postId,
//                userId,
//                postReactionCreateRequest.reaction()
//        );
//    }

    @Transactional
    public void deletePostReaction(Long userId, Long postId, PostReactionCreateRequest postReactionCreateRequest) {
        PostReaction postReaction = postReactionReader.getPostReactionWithPostIdAndUserId(postId, userId, postReactionCreateRequest.reaction());
        postReactionModifier.deletePostReaction(postReaction);

    }
}
