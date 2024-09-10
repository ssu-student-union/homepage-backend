package ussum.homepage.application.acl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.application.acl.service.factory.AclStrategy;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.user.User;
import ussum.homepage.infra.jpa.acl.entity.*;
import ussum.homepage.infra.jpa.acl.repository.PostAclJpaRepository;

import java.util.List;

// AclService.java
@Service
@RequiredArgsConstructor
public class AclManageService {
    private final List<AclStrategy> aclStrategies;
    private final PostAclJpaRepository postAclRepository;

    public boolean hasPermission(Post post, User user, Action action) {
        List<PostAclEntity> acls = postAclRepository.findByPostId(post.getId());

        for (PostAclEntity acl : acls) {
            for (AclStrategy strategy : aclStrategies) {
                if (strategy.supports(acl.getTarget())) {
                    if (strategy.matches(acl, user) && acl.getAction() == action) {
                        return acl.getType() == Type.ALLOW;
                    }
                    break;
                }
            }
        }

        return false;
    }
}

