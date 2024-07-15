package ussum.homepage.infra.jpa.reaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ussum.homepage.domain.reaction.PostCommentReaction;
import ussum.homepage.domain.reaction.PostCommentReactionRepository;
import ussum.homepage.domain.reaction.exception.PostCommentReactionException;
import ussum.homepage.domain.user.exception.UserNotFoundException;
import ussum.homepage.infra.jpa.comment.entity.PostCommentEntity;
import ussum.homepage.infra.jpa.comment.repository.PostCommentJpaRepository;
import ussum.homepage.infra.jpa.postlike.entity.Reaction;
import ussum.homepage.infra.jpa.reaction.repository.PostCommentReactionJpaRepository;
import ussum.homepage.infra.jpa.user.entity.UserEntity;
import ussum.homepage.infra.jpa.user.repository.UserJpaRepository;

import java.util.Optional;

import static ussum.homepage.global.error.status.ErrorStatus.POST_COMMENT_NOT_FOUND;
import static ussum.homepage.global.error.status.ErrorStatus.USER_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class PostCommentReactionRepositoryImpl implements PostCommentReactionRepository {
    private final PostCommentReactionJpaRepository postCommentReactionJpaRepository;
    private final PostCommentJpaRepository postCommentJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final PostCommentReactionMapper postCommentReactionMapper;

    @Override
    public PostCommentReaction save(PostCommentReaction postCommentReaction) {
        return postCommentReactionMapper.toDomain(
                postCommentReactionJpaRepository.save(postCommentReactionMapper.toEntity(postCommentReaction))
        );
    }

    @Override
    public void delete(PostCommentReaction postCommentReaction) {
        postCommentReactionJpaRepository.delete(
                postCommentReactionMapper.toEntity(postCommentReaction)
        );
    }

    @Override
    public Optional<PostCommentReaction> findByCommentIdAndUserId(Long commentId, Long userId, String reaction) {
        PostCommentEntity postCommentEntity = postCommentJpaRepository.findById(commentId)
                .orElseThrow(() -> new PostCommentReactionException(POST_COMMENT_NOT_FOUND));

        UserEntity userEntity = userJpaRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        return postCommentReactionJpaRepository.findByPostCommentEntityAndUserEntityAndReaction(postCommentEntity, userEntity, Reaction.getEnumReactionFromStringReaction(reaction))
                .map(postCommentReactionMapper::toDomain);
    }

}
