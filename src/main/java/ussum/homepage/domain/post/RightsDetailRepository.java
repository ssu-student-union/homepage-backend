package ussum.homepage.domain.post;

import java.util.List;
import java.util.Optional;
import ussum.homepage.application.post.service.dto.request.RightsDetailRequest;
import ussum.homepage.infra.jpa.post.entity.RightsDetailEntity;

public interface RightsDetailRepository {
    void saveAll(List<RightsDetail> rightsDetails);
    List<RightsDetail> findAllByPostId(Long postId);
    void deleteAll(Long postId);
    Optional<RightsDetail> findById(Long rightsDetailId);

    Long updateRightsDetail(Long rightsDetailId, RightsDetailRequest rightsDetailRequest);

    void updateRightsDetailList(List<RightsDetail> rightsDetailList);
}
