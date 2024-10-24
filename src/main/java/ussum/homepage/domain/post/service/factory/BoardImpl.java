package ussum.homepage.domain.post.service.factory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.service.PostReader;
import ussum.homepage.infra.jpa.group.entity.GroupCode;
import ussum.homepage.infra.jpa.member.entity.MemberCode;
import ussum.homepage.infra.jpa.post.entity.Category;

public interface BoardImpl {
    Page<Post> getPostList(PostReader postReader, GroupCode groupCode, MemberCode memberCode, Category category, Pageable pageable);
    Page<Post> searchPostList(String q, PostReader postReader, GroupCode groupCode, MemberCode memberCode, Category category, Pageable pageable);
}
