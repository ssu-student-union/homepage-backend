package ussum.homepage.domain.acl.strategy;

import ussum.homepage.domain.acl.service.PostAclManager;
import ussum.homepage.infra.jpa.acl.entity.Action;
import ussum.homepage.infra.jpa.post.entity.BoardCode;

import java.util.List;

public class PartnerBoardStrategy implements BoardPermissionStrategy {
    @Override
    public void applyPermissions(List<Action> allowed, List<Action> denied, Long userId,
                                 boolean isLoggedIn, PostAclManager postAclManager) {
        if (!isLoggedIn && postAclManager.hasDenyPermissionForAnonymous(BoardCode.PARTNER.name(), Action.READ)) {
            denied.add(Action.READ);
        } else {
            allowed.add(Action.READ);
        }
    }
}
