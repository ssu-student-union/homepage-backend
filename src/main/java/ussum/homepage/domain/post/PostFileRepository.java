package ussum.homepage.domain.post;

import java.util.List;
import java.util.Optional;

public interface PostFileRepository {
    List<PostFile> findAllByPostId(Long postId);
    Optional<PostFile> findById(Long id);
    List<PostFile> saveAll(List<PostFile> postFiles);
    void save(PostFile postFile);
}
