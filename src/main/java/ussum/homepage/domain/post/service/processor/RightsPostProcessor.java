package ussum.homepage.domain.post.service.processor;

import org.springframework.stereotype.Service;
import ussum.homepage.domain.comment.service.PostCommentReader;
import ussum.homepage.domain.member.service.MemberManager;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.PostRepository;
import ussum.homepage.infra.jpa.post.entity.Category;

@Service
public class RightsPostProcessor extends BasePostProcessor {

    public RightsPostProcessor(PostRepository postRepository, PostCommentReader postCommentReader,
                               MemberManager memberManager) {
        super(postRepository, postCommentReader, memberManager);
    }

    @Override
    protected boolean shouldHandlePost(Post post) {
        return post.getCategory().equals("접수대기");
    }

    @Override
    protected String getTargetCategory() {
        return Category.RECEIVED.getStringCategoryCode();
    }

    @Override
    public String getBoardType() {
        return "RIGHTS";
    }
}
