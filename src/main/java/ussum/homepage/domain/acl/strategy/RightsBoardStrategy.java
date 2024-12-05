package ussum.homepage.domain.acl.strategy;

import ussum.homepage.domain.acl.service.PostAclManager;
import ussum.homepage.infra.jpa.acl.entity.Action;
import ussum.homepage.infra.jpa.post.entity.BoardCode;

import java.util.List;

public class RightsBoardStrategy implements BoardPermissionStrategy {
    @Override
    public void applyPermissions(List<Action> allowed, List<Action> denied, Long userId,
                                 boolean isLoggedIn, PostAclManager postAclManager) {
        if (allowed.isEmpty()) {
            denied.add(Action.WRITE);
        }

        if (isLoggedIn) {
            if (postAclManager.hasPermission(userId, BoardCode.RIGHTS.name(), Action.ALL_READ)) {
                allowed.add(Action.ALL_READ);
            } else if (postAclManager.hasPermission(userId, BoardCode.RIGHTS.name(), Action.READ)) {
                allowed.add(Action.READ);
            } else {
                denied.add(Action.ALL_READ);
            }
        } else {
            denied.add(Action.ALL_READ);
        }
    }
}
