package ussum.homepage.domain.post;

import java.util.List;

public interface PostFileRepository {
    List<PostFile> findAllByPostId(Long postId);
}
