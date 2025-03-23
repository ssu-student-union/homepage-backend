package ussum.homepage.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.PostFile;
import ussum.homepage.domain.post.PostFileRepository;
import ussum.homepage.infra.jpa.post.entity.FileCategory;

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
    public void updatePostIdForIds(List<Long> postFileIds, Long postId, String fileCategory) {
        postFileRepository.updatePostIdForIds(postFileIds, postId, FileCategory.getEnumFileCategoryFromString(fileCategory));
    }

    @Transactional
    public void updatePostIdAndFileCategoryForIds(List<Long> postFileIds, Long postId, String fileCategory){
        postFileRepository.updatePostIdAndFileCategoryForIds(postFileIds, postId, FileCategory.getEnumFileCategoryFromString(fileCategory));
    }
}
