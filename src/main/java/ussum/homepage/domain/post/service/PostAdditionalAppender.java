package ussum.homepage.domain.post.service;

import static ussum.homepage.global.error.status.ErrorStatus.RIGHTS_DETAIL_ID_NOT_FOUND;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.application.post.service.dto.request.PostCreateRequest;
import ussum.homepage.application.post.service.dto.request.RightsDetailRequest;
import ussum.homepage.application.post.service.dto.request.RightsPostCreateRequest;
import ussum.homepage.domain.post.RightsDetail;
import ussum.homepage.domain.post.RightsDetailRepository;
import ussum.homepage.global.error.exception.GeneralException;

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
    @Transactional
    public Long modifyAdditional(Long rightsDetailId, RightsDetailRequest rightsDetailRequest) {
       return rightsDetailRepository.updateRightsDetail(rightsDetailId, rightsDetailRequest);
    }
    @Transactional
    public void modifyAdditionalList(List<RightsDetail> rightsDetailList) {
        Map<Long, List<Long>> postToRightsDetailsMap = rightsDetailList.stream()
                .collect(Collectors.groupingBy(
                        RightsDetail::getPostId,
                        Collectors.mapping(RightsDetail::getRightsDetailId, Collectors.toList())
                ));

        //각 postId별로 rightsDetailIds 유효성 검증
        for (Map.Entry<Long, List<Long>> entry : postToRightsDetailsMap.entrySet()) {
            Long postId = entry.getKey();
            List<Long> rightsDetailIds = entry.getValue();

            // DB에서 해당 postId에 속한 실제 rightsDetailId 목록 조회
            List<Long> validRightsDetailIds = rightsDetailRepository.findRightsDetailIdsByPostId(postId);

            // 요청된 rightsDetailId들이 모두 유효한지 검증
            if (!validRightsDetailIds.containsAll(rightsDetailIds)) {
                throw new GeneralException(RIGHTS_DETAIL_ID_NOT_FOUND);
            }
        }
        rightsDetailRepository.updateRightsDetailList(rightsDetailList);

    }
}
