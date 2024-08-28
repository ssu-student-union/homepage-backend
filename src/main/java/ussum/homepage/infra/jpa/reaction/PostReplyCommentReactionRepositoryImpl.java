package ussum.homepage.infra.jpa.reaction;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ussum.homepage.domain.reaction.PostReplyCommentReaction;
import ussum.homepage.domain.reaction.PostReplyCommentReactionRepository;
import ussum.homepage.domain.reaction.exception.PostCommentException;
import ussum.homepage.domain.user.exception.UserNotFoundException;
import ussum.homepage.infra.jpa.comment.entity.PostReplyCommentEntity;
import ussum.homepage.infra.jpa.comment.repository.PostReplyCommentJpaRepository;
import ussum.homepage.infra.jpa.postlike.entity.Reaction;
import ussum.homepage.infra.jpa.reaction.entity.PostCommentReactionEntity;
import ussum.homepage.infra.jpa.reaction.entity.PostReplyCommentReactionEntity;
import ussum.homepage.infra.jpa.reaction.repository.PostReplyCommentReactionJpaRepository;
import ussum.homepage.infra.jpa.user.entity.UserEntity;
import ussum.homepage.infra.jpa.user.repository.UserJpaRepository;

import java.util.List;
import java.util.Optional;

import static ussum.homepage.global.error.status.ErrorStatus.POST_REPLY_COMMENT_NOT_FOUND;
import static ussum.homepage.global.error.status.ErrorStatus.USER_NOT_FOUND;
import static ussum.homepage.infra.jpa.comment.entity.QPostCommentEntity.postCommentEntity;
import static ussum.homepage.infra.jpa.comment.entity.QPostReplyCommentEntity.postReplyCommentEntity;
import static ussum.homepage.infra.jpa.reaction.entity.QPostCommentReactionEntity.postCommentReactionEntity;
import static ussum.homepage.infra.jpa.reaction.entity.QPostReplyCommentReactionEntity.postReplyCommentReactionEntity;
import static ussum.homepage.infra.jpa.user.entity.QUserEntity.userEntity;


@Repository
@RequiredArgsConstructor
public class PostReplyCommentReactionRepositoryImpl implements PostReplyCommentReactionRepository {
    private final PostReplyCommentReactionJpaRepository postReplyCommentReactionJpaRepository;
    private final PostReplyCommentReactionMapper postReplyCommentReactionMapper;
    private final UserJpaRepository userJpaRepository;
    private final PostReplyCommentJpaRepository postReplyCommentJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<PostReplyCommentReaction> findAllByReplyCommentId(Long replyCommentId) {
        return postReplyCommentReactionJpaRepository.findAllByPostReplyCommentId(replyCommentId)
                .stream().map(postReplyCommentReactionMapper::toDomain)
                .filter(postReplyCommentReaction -> postReplyCommentReaction.getReaction().equals("like"))
                .toList();
    }

    @Override
    public Optional<PostReplyCommentReaction> findByUserIdAndReplyCommentId(Long userId, Long replyCommentId) {
//        PostReplyCommentReactionEntity result = queryFactory
//                .selectFrom(postReplyCommentReactionEntity)
//                .leftJoin(postReplyCommentReactionEntity.userEntity, userEntity)
//                .leftJoin(postReplyCommentReactionEntity.postReplyCommentEntity, postReplyCommentEntity)
//                .where(
//                        eqUserId(userId),
//                        eqReplyCommentId(replyCommentId)
//                ).fetchOne();
//
//        return Optional.ofNullable(result)
//                .map(postReplyCommentReactionMapper::toDomain);
        UserEntity userEntity = userJpaRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        PostReplyCommentEntity postReplyCommentEntity = postReplyCommentJpaRepository.findById(replyCommentId)
                .orElseThrow(() -> new PostCommentException(POST_REPLY_COMMENT_NOT_FOUND));

        return postReplyCommentReactionJpaRepository.findByPostReplyCommentEntityAndUserEntity(postReplyCommentEntity, userEntity)
                .map(postReplyCommentReactionMapper::toDomain);
    }

    @Override
    public Optional<PostReplyCommentReaction> findByReplyCommentIdAndUserIdAndReaction(Long userId, Long replyCommentId, String reaction) {
//        UserEntity userEntity = userJpaRepository.findById(userId)
//                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
//
//        PostReplyCommentEntity postReplyCommentEntity = postReplyCommentJpaRepository.findById(replyCommentId)
//                .orElseThrow(() -> new PostCommentException(POST_REPLY_COMMENT_NOT_FOUND));

        return postReplyCommentReactionJpaRepository.findByPostReplyCommentIdAndUserIdAndReaction(userId, replyCommentId, Reaction.getEnumReactionFromStringReaction(reaction) )
                .map(postReplyCommentReactionMapper::toDomain);

    }

    @Override
    public PostReplyCommentReaction save(PostReplyCommentReaction postReplyCommentReaction) {
        return postReplyCommentReactionMapper.toDomain(
                postReplyCommentReactionJpaRepository.save(postReplyCommentReactionMapper.toEntity(postReplyCommentReaction))
        );
    }

    @Override
    public void delete(PostReplyCommentReaction postReplyCommentReaction) {
        postReplyCommentReactionJpaRepository.delete(postReplyCommentReactionMapper.toEntity(postReplyCommentReaction));
    }

    private BooleanExpression eqUserId(Long userId) {
        return userId != null ? userEntity.id.eq(userId) : null;
    }
    private BooleanExpression eqReplyCommentId(Long replyCommentId) {
        return replyCommentId != null ? postCommentEntity.id.eq(replyCommentId) : null;
    }
}
