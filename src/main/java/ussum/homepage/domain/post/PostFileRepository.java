package ussum.homepage.domain.post;

import ussum.homepage.infra.jpa.post.entity.FileCategory;

import java.util.List;
import java.util.Optional;

public interface PostFileRepository {
    List<PostFile> findAllByPostId(Long postId);
    Long deleteAllByUrl(List<String> urlList);
    Optional<PostFile> findById(Long id);
    List<PostFile> saveAll(List<PostFile> postFiles);
    void updatePostIdForIds(List<Long> postFileIds, Long postId);
    void updatePostIdAndFileCategoryForIds(List<Long> postFileIds, Long postId, FileCategory fileCategory, String fileType);
}
