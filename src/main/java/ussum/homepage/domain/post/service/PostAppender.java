package ussum.homepage.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.domain.acl.BoardAcl;
import ussum.homepage.domain.acl.BoardAclRepository;
import ussum.homepage.domain.acl.PostAclRepository;
import ussum.homepage.domain.post.Board;
import ussum.homepage.domain.post.BoardRepository;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.PostRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostAppender {
    private final PostRepository postRepository;
    private final PostAclRepository postAclRepository;

    @Transactional
    public Post createPost(Post post) {
        return postRepository.save(post);
    }
}
