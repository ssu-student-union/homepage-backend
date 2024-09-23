package ussum.homepage.domain.acl.service.post;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ussum.homepage.application.comment.service.dto.response.CommentListResponse;
import ussum.homepage.application.post.service.dto.response.postDetail.PostDetailRes;
import ussum.homepage.application.post.service.dto.response.postList.PostListRes;
import ussum.homepage.global.error.exception.GeneralException;
import ussum.homepage.global.error.status.ErrorStatus;

@Aspect
@Component
@RequiredArgsConstructor
public class PostAclAspect {
    private final PostAclHandler postAclHandler;
    private final PostAclManager postAclManager;

    @Around("@annotation(customACL)")
    public Object applyAcl(ProceedingJoinPoint joinPoint, CustomACL customACL) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Long userId = extractUserId(args);
        String boardCode = customACL.boardCode().isEmpty() ? extractBoardCode(args) : customACL.boardCode();
        String action = customACL.action();

        // 비로그인 사용자 체크
        if (userId == null && !customACL.allowAnonymous()) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }
        // 권한 체크

        boolean hasPermission = checkBoardPermission(customACL.boardPermissions(), boardCode, action, userId);

        if (!hasPermission && !action.isEmpty()) {
            hasPermission = userId != null
                    ? postAclManager.hasPermission(userId, boardCode, action)
                    : postAclManager.hasAllowPermissionForAnonymous(boardCode, action);
        }

        if (!hasPermission) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }

        Object result = joinPoint.proceed();

        if (result instanceof PostListRes) {
            return postAclHandler.applyPermissionsToPostList((PostListRes<?>) result, userId, boardCode);
        } else if (result instanceof PostDetailRes) {
            postAclHandler.applyPermissionsToPostDetail((PostDetailRes<?>) result, userId, boardCode);
        } else if (result instanceof CommentListResponse) {
            postAclHandler.applyPermissionsToCommentList((CommentListResponse) result, userId);
        }

        return result;
    }
    private boolean checkBoardPermission(BoardPermission[] boardPermissions, String boardCode, String action, Long userId) {
        for (BoardPermission permission : boardPermissions) {
            if (permission.boardCode().name().equals(boardCode)) {
                for (String permittedAction : permission.actions()) {
                    if (permittedAction.equals(action)) {
                        return userId != null
                                ? postAclManager.hasPermission(userId, boardCode, action)
                                : postAclManager.hasAllowPermissionForAnonymous(boardCode, action);
                    }
                }
            }
        }
        return false;
    }
    private Long extractUserId(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof Long) {
                return (Long) arg;
            }
        }
        return null;
    }

    private String extractBoardCode(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof String) {
                return (String) arg;
            }
        }
        return null;
    }
}