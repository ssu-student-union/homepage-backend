package ussum.homepage.domain.post.service.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.service.PostReader;
import ussum.homepage.infra.jpa.group.entity.GroupCode;
import ussum.homepage.infra.jpa.member.entity.MemberCode;
import ussum.homepage.infra.jpa.post.entity.Category;

@RequiredArgsConstructor
public class NoticeBoardImpl implements BoardImpl {
    private final Long id;

    @Override
    public Page<Post> getPostList(PostReader postReader, GroupCode groupCode, MemberCode memberCode, Category category, Pageable pageable) {
        return postReader.getPostListByBoardIdAndGroupCodeAndMemberCode(this.id, groupCode, memberCode, pageable);
    }

    @Override
    public Page<Post> searchPostList(String q, PostReader postReader, GroupCode groupCode, MemberCode memberCode, Category category, Pageable pageable) {
        return postReader.searchPostListByBoardIdAndGroupCodeAndMemberCode(this.id, q, groupCode, memberCode, pageable);
    }

}
