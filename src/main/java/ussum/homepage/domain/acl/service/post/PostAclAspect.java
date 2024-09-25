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
import ussum.homepage.infra.jpa.acl.entity.Target;
import ussum.homepage.infra.jpa.acl.entity.TargetGroup;
import ussum.homepage.infra.jpa.acl.entity.Type;
import ussum.homepage.infra.jpa.post.entity.BoardCode;

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
        Target target = customACL.target();
        Type type = customACL.type();

        // 비로그인 사용자 체크
        if (userId == null && !customACL.allowAnonymous()) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }

        // BoardPermission 확인
        boolean isAllowed = isTargetTypeAllowOrDeny(customACL.boardPermissions(), boardCode, target, type, userId);

        if (!isAllowed) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }

        // 추가적인 권한 체크는 type이 ALLOW일 때만 수행
//        if (type == Type.ALLOW) {
//            boolean hasPermission = checkBoardPermission(customACL.boardPermissions(), boardCode, action, userId);
//
//            if (!hasPermission && !action.isEmpty()) {
//                hasPermission = userId != null
//                        ? postAclManager.hasPermission(userId, boardCode, action)
//                        : postAclManager.hasAllowPermissionForAnonymous(boardCode, action);
//            }
//
//            if (!hasPermission) {
//                throw new GeneralException(ErrorStatus._FORBIDDEN);
//            }
//        }

        return proceedAndApplyPermissions(joinPoint, userId, boardCode);
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

    private Object proceedAndApplyPermissions(ProceedingJoinPoint joinPoint, Long userId, String boardCode) throws Throwable {
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

    public boolean isTargetTypeAllowOrDeny(BoardPermission[] permissions, String boardCode, Target target, Type type, Long userId) {
        for (BoardPermission permission : permissions) {



                        // PARTNER 게시판에 대한 ANONYMOUS 사용자의 DENY 처리
                        if (BoardCode.PARTNER.equals(permission.boardCode()) && permission.target() == Target.ANONYMOUS && permission.type() == Type.DENY) {
                            return userId != null; // 로그인한 사용자(userId != null)는 true, 비로그인 사용자는 false 반환
                        }

                        // BoardPermission의 target과 type이 CustomACL의 target과 type과 일치하는지 확인
                        if (permission.target() == target && permission.type() == type) {
                            // targetGroups 확인
                            if (permission.targetGroups().length > 0) {
                                for (TargetGroup targetGroup : permission.targetGroups()) {
                                    // targetGroup에 따른 추가적인 검증 로직이 필요할 수 있습니다.
                                    // 예: 특정 그룹에 속한 사용자인지 확인
                                }
                            }
                            return type == Type.ALLOW;
                        }



        }
        // 일치하는 BoardPermission을 찾지 못한 경우, 기본적으로 접근을 거부
        return false;
    }
}