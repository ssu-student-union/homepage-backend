package ussum.homepage.domain.post.service;

import static ussum.homepage.global.error.status.ErrorStatus.RIGHTS_DETAIL_NOT_FOUND;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.RightsDetail;
import ussum.homepage.domain.post.RightsDetailRepository;
import ussum.homepage.global.error.exception.GeneralException;

@Service
@RequiredArgsConstructor
public class PostAdditionalReader {
    private final RightsDetailRepository rightsDetailRepository;

    @Transactional
    public List<RightsDetail> getRightsDetailByPostId(Long postId) {
        return rightsDetailRepository.findAllByPostId(postId);
    }

    @Transactional
    public RightsDetail getRightsDetailById(Long rightsDetailId) {
        return rightsDetailRepository.findById(rightsDetailId).orElseThrow(()->new GeneralException(RIGHTS_DETAIL_NOT_FOUND));
    }
}
