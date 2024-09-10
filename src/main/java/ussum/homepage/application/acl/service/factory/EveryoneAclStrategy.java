package ussum.homepage.application.acl.service.factory;

import org.springframework.stereotype.Component;
import ussum.homepage.domain.user.User;
import ussum.homepage.infra.jpa.acl.entity.PostAclEntity;
import ussum.homepage.infra.jpa.acl.entity.Target;

// EveryoneAclStrategy.java
@Component
public class EveryoneAclStrategy implements AclStrategy {
    @Override
    public boolean supports(Target target) {
        return target == Target.EVERYONE;
    }

    @Override
    public boolean matches(PostAclEntity acl, User user) {
        return true;
    }
}
