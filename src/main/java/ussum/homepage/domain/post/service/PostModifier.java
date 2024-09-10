package ussum.homepage.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;;
import org.springframework.util.StringUtils;
import ussum.homepage.application.post.service.dto.request.PostUpdateRequest;
import ussum.homepage.domain.post.*;
import ussum.homepage.infra.jpa.post.entity.Category;

@Service
@RequiredArgsConstructor
public class PostModifier {
    private final PostRepository postRepository;
    private final PostReader postReader;
    private final BoardReader boardReader;

    public Post updatePost(String boardCode,Long postId, PostUpdateRequest postUpdateRequest){
        Board board = boardReader.getBoardWithBoardCode(boardCode);
        Category category = StringUtils.hasText(postUpdateRequest.categoryCode()) ? Category.getEnumCategoryCodeFromStringCategoryCode(postUpdateRequest.categoryCode()) : null;

        return postRepository.save(postUpdateRequest.toDomain(postReader.getPostWithBoardCodeForEditAndDelete(boardCode, postId), board, category));
    }

    public void deletePost(String boardCode, Long postId) {
        postRepository.delete(postReader.getPostWithBoardCodeForEditAndDelete(boardCode, postId));
    }

}
