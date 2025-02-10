package ussum.homepage.domain.post.service.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.service.PostReader;
import ussum.homepage.infra.jpa.group.entity.GroupCode;
import ussum.homepage.infra.jpa.member.entity.MajorCode;
import ussum.homepage.infra.jpa.member.entity.MemberCode;
import ussum.homepage.infra.jpa.post.entity.Category;
import ussum.homepage.infra.jpa.post.entity.SuggestionTarget;

@RequiredArgsConstructor
public class QnABoardImpl implements BoardImpl {
    private final Long id;

    @Override
    public Page<Post> getPostList(PostReader postReader, GroupCode groupCode, MemberCode memberCode, Category category, SuggestionTarget suggestionTarget, MajorCode qnaMajorCode, MemberCode qnaMemberCode, Pageable pageable) {
        return postReader.getPostListByBoardIdAndQnAMajorCodeAndQnAMemberCode(this.id, qnaMajorCode, qnaMemberCode, pageable);
    }

    @Override
    public Page<Post> getPostListByUserId(PostReader postReader, GroupCode groupCode, MemberCode memberCode, Category category, SuggestionTarget suggestionTarget, Long userId, Pageable pageable) {
        return postReader.getPostListByBoardIdAndCategoryAndUserId(this.id, category, pageable,userId);
    }

    @Override
    public Page<Post> searchPostList(String q, PostReader postReader, GroupCode groupCode, MemberCode memberCode, Category category, MajorCode qnaMajorCode, MemberCode qnaMemberCode, Pageable pageable) {
        return postReader.searchPostListByBoardIdAndCategoryAndQnAMajorCodeAndQnAMemberCode(this.id, q, category, qnaMajorCode, qnaMemberCode, pageable);
    }

    @Override
    public Page<Post> searchPostListByUserId(String q, PostReader postReader, GroupCode groupCode, MemberCode memberCode, Category category, Long userId, Pageable pageable) {
        return postReader.searchPostListByBoardIdAndCategoryAndUserId(this.id, q, category, pageable, userId);
    }
}
