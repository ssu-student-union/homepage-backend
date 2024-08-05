package ussum.homepage.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.post.PostFile;
import ussum.homepage.domain.post.PostFileRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostFileReader {
    private final PostFileRepository postFileRepository;

    public List<PostFile> getPostFileListByPostId(Long postId) {
        return postFileRepository.findAllByPostId(postId);
    }

}
