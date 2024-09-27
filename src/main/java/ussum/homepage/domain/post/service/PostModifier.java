package ussum.homepage.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;;
import ussum.homepage.application.post.service.dto.request.PostUpdateRequest;
import ussum.homepage.domain.post.*;
import ussum.homepage.infra.jpa.post.entity.Category;

@Service
@RequiredArgsConstructor
public class PostModifier {
    private final PostRepository postRepository;
    private final PostReader postReader;
    private final BoardReader boardReader;

    public Post updatePost(Post post){
        return postRepository.save(post);
    }

    public void deletePost(String boardCode, Long postId) {
        postRepository.delete(postReader.getPostWithBoardCodeForEditAndDelete(boardCode, postId));
    }

}
