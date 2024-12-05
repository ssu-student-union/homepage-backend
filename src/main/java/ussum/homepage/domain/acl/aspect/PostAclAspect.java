package ussum.homepage.domain.acl.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ussum.homepage.application.comment.service.dto.response.CommentListResponse;
import ussum.homepage.application.post.service.dto.response.postDetail.PostDetailRes;
import ussum.homepage.application.post.service.dto.response.postList.PostListRes;
import ussum.homepage.domain.acl.annotation.ACLPermission;
import ussum.homepage.domain.acl.annotation.ACLRule;
import ussum.homepage.domain.acl.service.PostAclHandler;
import ussum.homepage.domain.acl.service.PostAclManager;
import ussum.homepage.global.error.exception.GeneralException;
import ussum.homepage.global.error.status.ErrorStatus;
import ussum.homepage.infra.jpa.acl.entity.Type;

import java.util.Arrays;
import java.util.Comparator;

@Aspect
@Component
@RequiredArgsConstructor
public class PostAclAspect {
    private final PostAclHandler postAclHandler;
    private final PostAclManager postAclManager;

    @Around("@annotation(aclRule)")
    public Object applyAcl(ProceedingJoinPoint joinPoint, ACLRule aclRule) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Long userId = extractUserId(args);
        String boardCode = extractBoardCode(args);

        // 권한을 order 기준으로 정렬하여 체크
        ACLPermission[] sortedPermissions = Arrays.stream(aclRule.value())
                .sorted(Comparator.comparingInt(ACLPermission::order))
                .toArray(ACLPermission[]::new);

        for (ACLPermission permission : sortedPermissions) {
            // DENY 타입 권한 먼저 체크
            if (permission.type() == Type.DENY) {
                if (isDenied(permission, userId, boardCode)) {
                    throw new GeneralException(ErrorStatus._FORBIDDEN);
                }
            }
        }

        // ALLOW 타입 권한 체크
        boolean hasAllowPermission = Arrays.stream(sortedPermissions)
                .filter(permission -> permission.type() == Type.ALLOW)
                .anyMatch(permission -> checkAllowPermission(permission, userId, boardCode));

        if (!hasAllowPermission) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }

        return proceedAndApplyPermissions(joinPoint, userId, boardCode);
    }

    private boolean isDenied(ACLPermission permission, Long userId, String boardCode) {
        switch (permission.target()) {
            case ANONYMOUS:
                return userId == null;
            case USER:
                return userId != null && checkUserPermission(permission, userId, boardCode);
//            case GROUP:
//                return userId != null && !permission.groupCode().isEmpty() &&
//                        postAclManager.checkGroupPermission(userId, permission.groupCode(),
//                                permission.actions(), Type.DENY);
//            case SPECIFIC_USER:
//                return userId != null && permission.userId().equals(userId.toString());
            case EVERYONE:
                return true;
            default:
                return false;
        }
    }

    private boolean checkAllowPermission(ACLPermission permission, Long userId, String boardCode) {
        switch (permission.target()) {
            case ANONYMOUS:
                return userId == null;
            case USER:
                return userId != null && checkUserPermission(permission, userId, boardCode);
//            case GROUP:
//                return userId != null && !permission.groupCode().isEmpty() &&
//                        postAclManager.checkGroupPermission(userId, permission.groupCode(),
//                                permission.actions(), Type.ALLOW);
//            case SPECIFIC_USER:
//                return userId != null && permission.userId().equals(userId.toString());
            case EVERYONE:
                return true;
            default:
                return false;
        }
    }

    private boolean checkUserPermission(ACLPermission permission, Long userId, String boardCode) {
        return Arrays.stream(permission.actions())
                .allMatch(action -> postAclManager.hasPermission(userId, boardCode, action));
    }

    private Long extractUserId(Object[] args) {
        return Arrays.stream(args)
                .filter(arg -> arg instanceof Long)
                .map(arg -> (Long) arg)
                .findFirst()
                .orElse(null);
    }

    private String extractBoardCode(Object[] args) {
        return Arrays.stream(args)
                .filter(arg -> arg instanceof String)
                .map(arg -> (String) arg)
                .findFirst()
                .orElse(null);
    }

    private Object proceedAndApplyPermissions(ProceedingJoinPoint joinPoint, Long userId, String boardCode)
            throws Throwable {
        Object result = joinPoint.proceed();

        if (result instanceof PostListRes) {
            return postAclHandler.applyPermissionsToPostList((PostListRes) result, userId, boardCode);
        } else if (result instanceof PostDetailRes) {
            postAclHandler.applyPermissionsToPostDetail((PostDetailRes) result, userId, boardCode);
        } else if (result instanceof CommentListResponse) {
            postAclHandler.applyPermissionsToCommentList((CommentListResponse) result, userId);
        }

        return result;
    }
}