package ussum.homepage.infra.jpa.reaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.reaction.PostCommentReaction;
import ussum.homepage.domain.reaction.PostCommentReactionRepository;
import ussum.homepage.infra.jpa.reaction.repository.PostCommentReactionJpaRepository;

@Repository
@RequiredArgsConstructor
public class PostCommentReactionRepositoryImpl implements PostCommentReactionRepository {
    private final PostCommentReactionJpaRepository postCommentReactionJpaRepository;
    private final PostCommentReactionMapper postCommentReactionMapper;

    @Override
    public PostCommentReaction save(PostCommentReaction postCommentReaction) {
        return postCommentReactionMapper.toDomain(
                postCommentReactionJpaRepository.save(postCommentReactionMapper.toEntity(postCommentReaction))
        );
    }

}
