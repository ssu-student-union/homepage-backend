package ussum.homepage.domain.post;

import java.util.List;
import java.util.Optional;

public interface RightsDetailRepository {
    void saveAll(List<RightsDetail> rightsDetails);
    List<RightsDetail> findAllByPostId(Long postId);
    void deleteAll(Long postId);
}
