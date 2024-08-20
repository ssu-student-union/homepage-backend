package ussum.homepage.domain.post.service.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.service.PostReader;

@RequiredArgsConstructor
public class GeneralBoardImpl implements BoardImpl {
    private final Long id;

    @Override
    public Page<Post> getPostList(PostReader postReader, String groupCode, String memberCode, Pageable pageable) {
        return postReader.getPostListByBoardId(this.id, pageable);
    }
}
