package ussum.homepage.domain.post.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.RightsDetail;
import ussum.homepage.domain.post.RightsDetailRepository;

@Service
@RequiredArgsConstructor
public class PostAdditionalReader {
    private final RightsDetailRepository rightsDetailRepository;

    @Transactional
    public List<RightsDetail> getRightsDetailByPostId(Long postId) {
        List<RightsDetail> rightsDetails = rightsDetailRepository.findAllByPostId(postId);
        return rightsDetails;
    }
}
