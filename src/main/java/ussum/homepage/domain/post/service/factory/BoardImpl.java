package ussum.homepage.domain.post.service.factory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.service.PostReader;
import ussum.homepage.infra.jpa.group.entity.GroupCode;
import ussum.homepage.infra.jpa.member.entity.MemberCode;
import ussum.homepage.infra.jpa.post.entity.Category;
import ussum.homepage.infra.jpa.post.entity.SuggestionTarget;

public interface BoardImpl {
    Page<Post> getPostList(PostReader postReader, GroupCode groupCode, MemberCode memberCode, Category category, SuggestionTarget suggestionTarget, Pageable pageable);
    Page<Post> getPostListByUserId(PostReader postReader, GroupCode groupCode, MemberCode memberCode, Category category,SuggestionTarget suggestionTarget,Long userId, Pageable pageable);
    Page<Post> searchPostList(String q, PostReader postReader, GroupCode groupCode, MemberCode memberCode, Category category, Pageable pageable);
}
