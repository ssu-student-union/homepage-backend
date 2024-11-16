package ussum.homepage.domain.post.service.processor;

import static ussum.homepage.global.error.status.ErrorStatus.POST_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.domain.comment.service.PostCommentReader;
import ussum.homepage.domain.member.service.MemberManager;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.PostRepository;
import ussum.homepage.domain.post.exception.PostException;
import ussum.homepage.infra.jpa.post.entity.Category;

@RequiredArgsConstructor
public abstract class BasePostProcessor implements PostProcessor {
    private final PostRepository postRepository;
    private final PostCommentReader postCommentReader;
    private final MemberManager memberManager;

    @Override
    @Transactional
    public void onAdminCommentPosted(Long postId, String commentType) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(POST_NOT_FOUND));

        if (commentType.equals("OFFICIAL") && shouldHandlePost(post)) {
            handleReceivedStatus(post);
        }
    }

    @Override
    public void handleReceivedStatus(Post post) {
        if (isAnsweredByAdmin(post)) {
            updatePostCategoryAndOngoingStatus(post, getTargetCategory());
        }
    }

    @Override
    public Boolean isAnsweredByAdmin(Post post) {
        return postCommentReader.getCommentListWithPostId(post.getId())
                .stream()
                .anyMatch(postComment -> memberManager.getCommentType(postComment.getUserId(), post.getId())
                        .equals("OFFICIAL"));
    }

    @Override
    public void updatePostCategoryAndOngoingStatus(Post post, String category) {
        postRepository.updatePostCategory(post, Category.getEnumCategoryCodeFromStringCategoryCode(category));
    }

    protected abstract boolean shouldHandlePost(Post post); // 특정 조건에 따라 동작하도록 분리

    protected abstract String getTargetCategory();// 카테고리 코드 반환

}

