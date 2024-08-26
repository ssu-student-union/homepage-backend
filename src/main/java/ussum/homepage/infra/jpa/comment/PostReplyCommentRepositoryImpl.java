package ussum.homepage.infra.jpa.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ussum.homepage.domain.comment.PostReplyComment;
import ussum.homepage.domain.comment.PostReplyCommentRepository;
import ussum.homepage.domain.reaction.service.PostReplyCommentReactionReader;
import ussum.homepage.infra.jpa.comment.entity.PostCommentEntity;
import ussum.homepage.infra.jpa.comment.entity.PostReplyCommentEntity;
import ussum.homepage.infra.jpa.comment.repository.PostReplyCommentJpaRepository;

import java.util.List;
import java.util.Optional;

import static ussum.homepage.infra.jpa.comment.entity.PostReplyCommentEntity.updateDeletedAt;
import static ussum.homepage.infra.jpa.comment.entity.PostReplyCommentEntity.updateLastEditedAt;

@Repository
@RequiredArgsConstructor
public class PostReplyCommentRepositoryImpl implements PostReplyCommentRepository {
    private final PostReplyCommentJpaRepository postReplyCommentJpaRepository;
    private final PostReplyCommentMapper postReplyCommentMapper;
    private final PostReplyCommentReactionReader postReplyCommentReactionReader;

    @Override
    public List<PostReplyComment> findAllByCommentId(Long commentId) {
        return postReplyCommentJpaRepository.findAllByCommentId(commentId).stream()
                .map(postReplyCommentMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<PostReplyComment> findById(Long replyCommentId) {
        return postReplyCommentJpaRepository.findById(replyCommentId).map(postReplyCommentMapper::toDomain);
    }

    @Override
    public PostReplyComment save(PostReplyComment postReplyComment) {
        return postReplyCommentMapper.toDomain(postReplyCommentJpaRepository.save(postReplyCommentMapper.toEntity(postReplyComment)));
    }

    @Override
    public PostReplyComment update(PostReplyComment postReplyComment) {
        return postReplyCommentMapper.toDomain(postReplyCommentJpaRepository.save(postReplyCommentMapper.toEntity(postReplyComment)));
    }

    @Override
    public void delete(PostReplyComment postReplyComment) {
        PostReplyCommentEntity postReplyCommentEntity = postReplyCommentMapper.toEntity(postReplyComment);
        updateDeletedAt(postReplyCommentEntity);
        postReplyCommentJpaRepository.save(postReplyCommentEntity);
    }
}
