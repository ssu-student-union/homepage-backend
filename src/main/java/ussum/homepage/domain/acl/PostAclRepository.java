package ussum.homepage.domain.acl;

import java.util.List;
import java.util.Optional;

public interface PostAclRepository {
//    List<PostAcl> findByPostId(Long postId);
//    void save(PostAcl postAcl);
//    PostAcl update(PostAcl postAcl);
//    Optional<PostAcl> findById(Long postAclId);
//    void delete(PostAcl postAcl);
    List<PostAcl> findAllByBoardCode(String boardCode);
}
