package ussum.homepage.domain.post.service.factory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.service.PostReader;

public interface BoardImpl {
    Page<Post> getPostList(PostReader postReader, String groupCode, String memberCode, Pageable pageable);
}
