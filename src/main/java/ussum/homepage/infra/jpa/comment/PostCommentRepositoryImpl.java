package ussum.homepage.infra.jpa.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ussum.homepage.domain.comment.PostComment;
import ussum.homepage.domain.comment.PostCommentRepository;
import ussum.homepage.global.error.exception.GeneralException;
import ussum.homepage.infra.jpa.comment.repository.PostCommentJpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static ussum.homepage.global.error.status.ErrorStatus.POST_COMMENT_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class PostCommentRepositoryImpl implements PostCommentRepository {
    private final PostCommentJpaRepository postCommentJpaRepository;
    private final PostCommentMapper postCommentMapper;
    @Override
    public Page<PostComment> findAllByPostId(Pageable pageable, Long postId){
        return postCommentJpaRepository.findAllByPostId(pageable, postId).map(postCommentMapper::toDomain);
    }
    @Override
    public PostComment findByPostIdAndUserId(Long postId, Long userId){
        return postCommentMapper.toDomain(postCommentJpaRepository.findByPostIdAndUserId(postId, userId));
    }
    @Override
    public PostComment save(PostComment postComment){
        return postCommentMapper.toDomain(postCommentJpaRepository.save(postCommentMapper.toEntity(postComment)));
    }
    @Override
    public PostComment update(PostComment postComment){
        return postCommentMapper.toDomain(postCommentJpaRepository.save(postCommentMapper.toEntity(postComment)));
    }
    @Override
    public PostComment findById(Long id){
        return postCommentMapper.toDomain(postCommentJpaRepository.findById(id).orElseThrow(() -> new GeneralException(POST_COMMENT_NOT_FOUND)));
    }
    @Override
    public void delete(PostComment postComment){
        postCommentJpaRepository.delete(postCommentMapper.toEntity(postComment));
    }
}
