package ussum.homepage.domain.post.service.factory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.service.PostReader;
import ussum.homepage.infra.jpa.group.entity.GroupCode;
import ussum.homepage.infra.jpa.member.entity.MajorCode;
import ussum.homepage.infra.jpa.member.entity.MemberCode;
import ussum.homepage.infra.jpa.post.entity.Category;
import ussum.homepage.infra.jpa.post.entity.SuggestionTarget;

public interface BoardImpl {
    Page<Post> getPostList(PostReader postReader, GroupCode groupCode, MemberCode memberCode, Category category, SuggestionTarget suggestionTarget, MajorCode qnaMajorCode, MemberCode qnaMemberCode, Pageable pageable);
    Page<Post> getPostListByUserId(PostReader postReader, GroupCode groupCode, MemberCode memberCode, Category category,SuggestionTarget suggestionTarget,Long userId, Pageable pageable);
    Page<Post> searchPostList(String q, PostReader postReader, GroupCode groupCode, MemberCode memberCode, Category category, MajorCode qnaMajorCode, MemberCode qnaMemberCode, Pageable pageable);
    Page<Post> searchPostListByUserId(String q,PostReader postReader, GroupCode groupCode, MemberCode memberCode, Category category,Long userId, Pageable pageable);
}
