package ussum.homepage.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.PostFile;
import ussum.homepage.domain.post.PostFileRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostFileAppender {
    private final PostFileRepository postFileRepository;

    @Transactional
    public List<PostFile> saveAllPostFile(List<PostFile> fileList) {
        return postFileRepository.saveAll(fileList);
    }
}
