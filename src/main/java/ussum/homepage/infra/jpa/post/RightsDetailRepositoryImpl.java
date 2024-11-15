package ussum.homepage.infra.jpa.post;

import static ussum.homepage.infra.jpa.post.entity.QPostFileEntity.postFileEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ussum.homepage.domain.post.PostFile;
import ussum.homepage.domain.post.RightsDetail;
import ussum.homepage.domain.post.RightsDetailRepository;
import ussum.homepage.infra.jpa.post.repository.RightsDetailJpaRepository;

@Repository
@RequiredArgsConstructor
public class RightsDetailRepositoryImpl implements RightsDetailRepository {

    private final RightsDetailJpaRepository rightsDetailJpaRepository;
    private final RightsDetailMapper rightsDetailMapper;
    private final JPAQueryFactory queryFactory;

    @Override
    public void saveAll(List<RightsDetail> rightsDetails) {
        rightsDetailJpaRepository.saveAll(rightsDetails.stream()
                                .map(rightsDetailMapper::toEntity)
                                .toList());
    }

    @Override
    public List<RightsDetail> findAllByPostId(Long postId) {
        return rightsDetailJpaRepository.findByPostEntityId(postId).stream()
                .map(rightsDetailMapper::toDomain)
                .toList();

    }

    @Override
    public void deleteAll(Long postId) {
        rightsDetailJpaRepository.deleteByPostEntityId(postId);
    }
}

