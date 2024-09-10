package ussum.homepage.application.acl.service.factory;

import org.springframework.stereotype.Component;
import ussum.homepage.domain.user.User;
import ussum.homepage.infra.jpa.acl.entity.PostAclEntity;
import ussum.homepage.infra.jpa.acl.entity.Target;

// AnonymousAclStrategy.java
@Component
public class AnonymousAclStrategy implements AclStrategy {
    @Override
    public boolean supports(Target target) {
        return target == Target.ANONYMOUS;
    }

    @Override
    public boolean matches(PostAclEntity acl, User user) {
        return user == null;
    }
}
