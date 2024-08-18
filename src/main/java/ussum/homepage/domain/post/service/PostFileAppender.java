package ussum.homepage.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.PostFile;
import ussum.homepage.domain.post.PostFileRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostFileAppender {
    private final PostFileRepository postFileRepository;

    @Transactional
    public List<PostFile> saveAllPostFile(List<PostFile> fileList) {
        return postFileRepository.saveAll(fileList);
    }

    @Transactional
    public void updatePostIdForIds(List<Long> postFileIds, Long postId) {
        postFileRepository.updatePostIdForIds(postFileIds, postId);
    }

    @Transactional
    public void updatePostIdAndSubCategoryForIds(List<Long> postFileIds, Long postId, String subCategory){
        postFileRepository.updatePostIdAndSubCategoryForIds(postFileIds, postId, subCategory);
    }
}
