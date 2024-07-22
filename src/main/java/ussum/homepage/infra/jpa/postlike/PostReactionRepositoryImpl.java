package ussum.homepage.infra.jpa.postlike;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ussum.homepage.domain.post.exception.PostException;
import ussum.homepage.domain.postlike.PostReaction;
import ussum.homepage.domain.postlike.PostReactionRepository;
import ussum.homepage.domain.user.exception.UserNotFoundException;
import ussum.homepage.infra.jpa.post.entity.PostEntity;
import ussum.homepage.infra.jpa.post.repository.PostJpaRepository;
import ussum.homepage.infra.jpa.postlike.entity.Reaction;
import ussum.homepage.infra.jpa.postlike.repository.PostReactionJpaRepository;
import ussum.homepage.infra.jpa.user.entity.UserEntity;
import ussum.homepage.infra.jpa.user.repository.UserJpaRepository;

import java.util.Optional;

import static ussum.homepage.global.error.status.ErrorStatus.POST_NOT_FOUND;
import static ussum.homepage.global.error.status.ErrorStatus.USER_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class PostReactionRepositoryImpl implements PostReactionRepository {
    private final PostReactionJpaRepository postReactionJpaRepository;
    private final PostJpaRepository postJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final PostReactionMapper postReactionMapper;

    @Override
    public PostReaction save(PostReaction postReaction) {
        return postReactionMapper.toDomain(
                postReactionJpaRepository.save(postReactionMapper.toEntity(postReaction))
        );
    }

    @Override
    public void delete(PostReaction postReaction) {
        postReactionJpaRepository.delete(
                postReactionMapper.toEntity(postReaction)
        );
    }

    @Override
    public Optional<PostReaction> findByPostIdAndUserId(Long postId, Long userId, String reaction) {
        PostEntity postEntity = postJpaRepository.findById(postId)
                .orElseThrow(() -> new PostException(POST_NOT_FOUND));

        UserEntity userEntity = userJpaRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        return postReactionJpaRepository.findByPostEntityAndUserEntityAndReaction(postEntity, userEntity, Reaction.getEnumReactionFromStringReaction(reaction))
                .map(postReactionMapper::toDomain);
    }

}
