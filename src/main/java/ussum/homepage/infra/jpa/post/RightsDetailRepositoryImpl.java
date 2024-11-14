package ussum.homepage.infra.jpa.post;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ussum.homepage.domain.post.RightsDetail;
import ussum.homepage.domain.post.RightsDetailRepository;
import ussum.homepage.infra.jpa.post.repository.RightsDetailJpaRepository;

@Repository
@RequiredArgsConstructor
public class RightsDetailRepositoryImpl implements RightsDetailRepository {

    private final RightsDetailJpaRepository rightsDetailJpaRepository;
    private final RightsDetailMapper rightsDetailMapper;

    @Override
    public void saveAll(List<RightsDetail> rightsDetails) {
        rightsDetailJpaRepository.saveAll(rightsDetails.stream()
                                .map(rightsDetailMapper::toEntity)
                                .toList());
    }

    @Override
    public void deleteAll(Long postId) {
        rightsDetailJpaRepository.deleteByPostEntityId(postId);
    }
}
