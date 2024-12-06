package ussum.homepage.domain.post.service.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.service.PostReader;
import ussum.homepage.infra.jpa.group.entity.GroupCode;
import ussum.homepage.infra.jpa.member.entity.MemberCode;
import ussum.homepage.infra.jpa.post.entity.Category;
import ussum.homepage.infra.jpa.post.entity.SuggestionTarget;

@RequiredArgsConstructor
public class SuggestionBoardImpl implements BoardImpl {
    private final Long id;

    @Override
    public Page<Post> getPostList(PostReader postReader, GroupCode groupCode, MemberCode memberCode, Category category, SuggestionTarget suggestionTarget, Pageable pageable) {
        return postReader.getPostListByBoardIdAndCategoryAndSuggestionTarget(this.id, category, suggestionTarget, pageable);
    }

    @Override
    public Page<Post> getPostListByUserId(PostReader postReader, GroupCode groupCode, MemberCode memberCode,
                                          Category category, SuggestionTarget suggestionTarget, Long userId,
                                          Pageable pageable) {
        return postReader.getPostListByBoardIdAndCategoryAndUserId(this.id, category, pageable,userId);
    }

    @Override
    public Page<Post> searchPostList(String q, PostReader postReader, GroupCode groupCode, MemberCode memberCode, Category category, Pageable pageable) {
        return postReader.searchPostListByBoardIdAndCategory(this.id, q, category, pageable);
    }

    @Override
    public Page<Post> searchPostListByUserId(String q, PostReader postReader, GroupCode groupCode,
                                             MemberCode memberCode, Category category,
                                             Long userId, Pageable pageable) {
        return postReader.searchPostListByBoardIdAndCategoryAndUserId(this.id, q, category, pageable, userId);
    }

}
