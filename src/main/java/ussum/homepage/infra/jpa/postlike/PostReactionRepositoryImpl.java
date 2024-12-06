package ussum.homepage.infra.jpa.postlike;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ussum.homepage.domain.post.exception.PostException;
import ussum.homepage.domain.postlike.PostReaction;
import ussum.homepage.domain.postlike.PostReactionRepository;
import ussum.homepage.domain.user.exception.UserNotFoundException;
import ussum.homepage.infra.jpa.post.entity.PostEntity;
import ussum.homepage.infra.jpa.post.repository.PostJpaRepository;
import ussum.homepage.infra.jpa.postlike.entity.PostReactionEntity;
import ussum.homepage.infra.jpa.postlike.entity.Reaction;
import ussum.homepage.infra.jpa.postlike.repository.PostReactionJpaRepository;
import ussum.homepage.infra.jpa.user.entity.UserEntity;
import ussum.homepage.infra.jpa.user.repository.UserJpaRepository;

import java.util.Optional;

import static ussum.homepage.global.error.status.ErrorStatus.POST_NOT_FOUND;
import static ussum.homepage.global.error.status.ErrorStatus.USER_NOT_FOUND;
import static ussum.homepage.infra.jpa.post.entity.QPostEntity.postEntity;
import static ussum.homepage.infra.jpa.postlike.entity.QPostReactionEntity.postReactionEntity;
import static ussum.homepage.infra.jpa.user.entity.QUserEntity.userEntity;

@Repository
@RequiredArgsConstructor
public class PostReactionRepositoryImpl implements PostReactionRepository {
    private final PostReactionJpaRepository postReactionJpaRepository;
    private final PostJpaRepository postJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final PostReactionMapper postReactionMapper;
    private final JPAQueryFactory queryFactory;

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
    public Integer countByPostIdAndReactionType(Long postId, String reaction) {
        Long count = queryFactory
                .select(postReactionEntity.count())
                .from(postReactionEntity)
                .where(
                        postReactionEntity.postEntity.id.eq(postId),
                        postReactionEntity.reaction.eq(Reaction.getEnumReactionFromStringReaction(reaction))
                )
                .fetchOne();

        return Optional.ofNullable(count).map(Long::intValue).orElse(0);
    }

    @Override
    public Optional<PostReaction> findByPostIdAndUserId(Long postId, Long userId, String reaction) {
//        PostEntity postEntity = postJpaRepository.findById(postId)
//                .orElseThrow(() -> new PostException(POST_NOT_FOUND));
//
//        UserEntity userEntity = userJpaRepository.findById(userId)
//                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        return postReactionJpaRepository.findByPostIdAndUserIdAndReaction(postId, userId, Reaction.getEnumReactionFromStringReaction(reaction))
                .map(postReactionMapper::toDomain);
    }

    @Override
    public Optional<PostReaction> findByUserIdAndPostId(Long userId, Long postId) {
        PostReactionEntity result = queryFactory
                .select(postReactionEntity)
                .from(postReactionEntity)
                .leftJoin(postReactionEntity.userEntity, userEntity)
                .leftJoin(postReactionEntity.postEntity, postEntity)
                .where(
                        eqUserId(userId),
                        eqPostId(postId)
                )
                .fetchOne();

        return Optional.ofNullable(result)
                .map(postReactionMapper::toDomain);
    }
    private BooleanExpression eqUserId(Long userId) {
        return userId != null ? userEntity.id.eq(userId) : null;
    }
    private BooleanExpression eqPostId(Long postId) {
        return postId != null ? postEntity.id.eq(postId) : null;
    }
}
