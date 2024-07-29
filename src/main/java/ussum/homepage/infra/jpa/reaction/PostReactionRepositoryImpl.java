package ussum.homepage.infra.jpa.reaction;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ussum.homepage.domain.postlike.PostReaction;
import ussum.homepage.domain.postlike.PostReactionRepository;
import ussum.homepage.infra.jpa.postlike.entity.PostReactionEntity;
import ussum.homepage.infra.jpa.reaction.repository.PostReactionJpaRepository;

import java.util.Objects;
import java.util.Optional;

import static ussum.homepage.infra.jpa.post.entity.QPostEntity.postEntity;
import static ussum.homepage.infra.jpa.postlike.entity.QPostReactionEntity.postReactionEntity;
import static ussum.homepage.infra.jpa.user.entity.QUserEntity.userEntity;

@Repository
@RequiredArgsConstructor
public class PostReactionRepositoryImpl implements PostReactionRepository {
    private final PostReactionMapper postReactionMapper;
    private final PostReactionJpaRepository postReactionJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public PostReaction save(PostReaction postReaction) {
        return postReactionMapper.toDomain(
                postReactionJpaRepository.save(postReactionMapper.toEntity(postReaction))
        );
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
    @Override
    public void delete(PostReaction postReaction){
        postReactionJpaRepository.delete(postReactionMapper.toEntity(postReaction));
    }

    private BooleanExpression eqUserId(Long userId) {
            return userId != null ? userEntity.id.eq(userId) : null;
    }
    private BooleanExpression eqPostId(Long postId) {
        return postId != null ? postEntity.id.eq(postId) : null;
    }
}
