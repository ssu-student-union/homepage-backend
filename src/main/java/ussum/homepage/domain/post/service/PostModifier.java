package ussum.homepage.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.application.post.service.dto.request.PostUpdateRequest;
import ussum.homepage.domain.acl.service.AclModifier;
import ussum.homepage.domain.acl.service.AclReader;
import ussum.homepage.domain.post.*;
import ussum.homepage.infra.jpa.post.entity.Category;

@Service
@RequiredArgsConstructor
public class PostModifier {
    private final PostRepository postRepository;
    private final PostReader postReader;
    private final BoardReader boardReader;
    private final AclModifier aclModifier;
    private final AclReader aclReader;

    public Post updatePost(String boardCode,Long postId, PostUpdateRequest postUpdateRequest){
        Board board = boardReader.getBoardWithBoardCode(boardCode);
//        Post postWithBoardCode = postReader.getPostWithBoardCodeForEditAndDelete(boardCode, postId);

        return postRepository.save(postUpdateRequest.toDomain(
                postReader.getPostWithBoardCodeForEditAndDelete(boardCode, postId), board, Category.getEnumCategoryCodeFromStringCategoryCode(postUpdateRequest.categoryCode()))
        );
    }

    public void deletePost(String boardCode, Long postId) {
        postRepository.delete(postReader.getPostWithBoardCodeForEditAndDelete(boardCode, postId));
    }

}
