package ussum.homepage.domain.post.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.application.post.service.dto.request.PostCreateRequest;
import ussum.homepage.application.post.service.dto.request.RightsDetailRequest;
import ussum.homepage.application.post.service.dto.request.RightsPostCreateRequest;
import ussum.homepage.domain.post.RightsDetail;
import ussum.homepage.domain.post.RightsDetailRepository;
import ussum.homepage.infra.jpa.post.repository.RightsDetailJpaRepository;

@Service
@RequiredArgsConstructor
public class PostAdditionalAppender {
    private final RightsDetailRepository rightsDetailRepository;

    @Transactional
    public void createAdditional(PostCreateRequest converPostCreateRequest, Long postId) {
        if (converPostCreateRequest instanceof RightsPostCreateRequest){

            List<RightsDetail> rightsDetailList = new ArrayList<>();

            for (RightsDetailRequest detailRequest : ((RightsPostCreateRequest) converPostCreateRequest).getRightsDetailList()){
                rightsDetailList.add(detailRequest.toDomain(postId));
            }
            rightsDetailRepository.saveAll(rightsDetailList);
        }
    }
}
