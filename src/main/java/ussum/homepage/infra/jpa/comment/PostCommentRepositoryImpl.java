package ussum.homepage.infra.jpa.comment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ussum.homepage.domain.comment.PostComment;
import ussum.homepage.domain.comment.PostCommentRepository;
import ussum.homepage.domain.reaction.service.PostCommentReactionReader;
import ussum.homepage.infra.jpa.comment.entity.CommentType;
import ussum.homepage.infra.jpa.comment.entity.PostCommentEntity;
import ussum.homepage.infra.jpa.comment.repository.PostCommentJpaRepository;

import java.util.List;
import java.util.Optional;

import static ussum.homepage.infra.jpa.comment.entity.PostCommentEntity.updateLastEditedAt;
import static ussum.homepage.infra.jpa.comment.entity.QPostCommentEntity.postCommentEntity;

@Repository
@RequiredArgsConstructor
public class PostCommentRepositoryImpl implements PostCommentRepository {
    private final PostCommentJpaRepository postCommentJpaRepository;
    private final PostCommentMapper postCommentMapper;
    private final PostCommentReactionReader postCommentReactionReader;
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PostComment> findAllByPostId(Pageable pageable, Long postId){
        return postCommentJpaRepository.findAllByPostId(pageable, postId).map(postCommentMapper::toDomain);
    }

    @Override
    public List<PostComment> findAllByPostId(Long postId) {
        return postCommentJpaRepository.findAllByPostId(postId)
                .stream()
                .map(postCommentMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<PostComment> findById(Long id){
        return postCommentJpaRepository.findById(id).map(postCommentMapper::toDomain);
    }

    @Override
    public Optional<PostComment> findByPostIdAndUserId(Long postId, Long userId){
        return postCommentJpaRepository.findByPostIdAndUserId(postId, userId).map(postCommentMapper::toDomain);
    }

    // 좋아요 순으로 정렬된 댓글 목록 반환
    @Override
    public List<PostComment> findAllByPostIdOrderByLikesDesc(Long postId) {
        List<PostCommentEntity> commentEntities = postCommentJpaRepository.findAllByPostIdOrderByLikesDesc(postId);
        return commentEntities.stream()
                .map(postCommentMapper::toDomain)
                .toList();
    }

    // 최신순으로 정렬된 댓글 목록 반환
    @Override
    public List<PostComment> findAllByPostIdOrderByCreatedAtDesc(Long postId) {
        List<PostCommentEntity> commentEntities = postCommentJpaRepository.findAllByPostEntityIdOrderByCreatedAtDesc(postId);
        return commentEntities.stream()
                .map(postCommentMapper::toDomain)
                .toList();
    }

    @Override
    public List<PostComment> findAllByPostIdAndCommentType(Long postId, String commentType) {
        return queryFactory
                .selectFrom(postCommentEntity)
                .where(postCommentEntity.postEntity.id.eq(postId)
                        .and(postCommentEntity.commentType.eq(CommentType.getEnumCommentTypeFromStringCommentType(commentType))))
                .fetch()
                .stream()
                .map(postCommentMapper::toDomain)
                .toList();
    }

    @Override
    public PostComment save(PostComment postComment){
        return postCommentMapper.toDomain(postCommentJpaRepository.save(postCommentMapper.toEntity(postComment)));
    }

    @Override
    public PostComment update(PostComment postComment){
        PostCommentEntity postCommentEntity = postCommentMapper.toEntity(postComment);
        updateLastEditedAt(postCommentEntity);
        return postCommentMapper.toDomain(postCommentJpaRepository.save(postCommentEntity));
    }

    @Override
    public void delete(PostComment postComment){
        postCommentJpaRepository.delete(postCommentMapper.toEntity(postComment));
    }
}
