package ussum.homepage.domain.acl.strategy;

import ussum.homepage.domain.acl.service.PostAclManager;
import ussum.homepage.infra.jpa.acl.entity.Action;

import java.util.List;

public interface BoardPermissionStrategy {
    void applyPermissions(List<Action> allowed, List<Action> denied, Long userId,
                          boolean isLoggedIn, PostAclManager postAclManager);
}
