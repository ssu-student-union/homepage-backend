package ussum.homepage.application.acl.service.factory;

import ussum.homepage.domain.user.User;
import ussum.homepage.infra.jpa.acl.entity.PostAclEntity;
import ussum.homepage.infra.jpa.acl.entity.Target;

// AclStrategy.java
public interface AclStrategy {
    boolean supports(Target target);

    boolean matches(PostAclEntity acl, User user);
}
