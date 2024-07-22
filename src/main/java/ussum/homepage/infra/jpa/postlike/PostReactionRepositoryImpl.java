package ussum.homepage.infra.jpa.postlike;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ussum.homepage.domain.postlike.PostReaction;
import ussum.homepage.domain.postlike.PostReactionRepository;
import ussum.homepage.infra.jpa.postlike.repository.PostReactionJpaRepository;

@Repository
@RequiredArgsConstructor
public class PostReactionRepositoryImpl implements PostReactionRepository {
    private final PostReactionJpaRepository postReactionJpaRepository;
    private final PostReactionMapper postReactionMapper;

    @Override
    public PostReaction save(PostReaction postReaction) {
        return postReactionMapper.toDomain(
                postReactionJpaRepository.save(postReactionMapper.toEntity(postReaction))
        );
    }
}
