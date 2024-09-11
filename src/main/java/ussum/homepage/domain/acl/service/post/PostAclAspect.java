package ussum.homepage.domain.acl.service.post;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ussum.homepage.application.comment.service.dto.response.CommentListResponse;
import ussum.homepage.application.post.service.dto.response.postDetail.PostDetailRes;
import ussum.homepage.application.post.service.dto.response.postList.PostListRes;

import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
public class PostAclAspect {
    private final PostAclManager postAclManager;

    @Around(value = "execution(* ussum.homepage.application.post.service.PostManageService.getPostList(..)) && args(userId, boardCode, ..)", argNames = "joinPoint,userId,boardCode")
    public Object validAuthorityOfPostList(ProceedingJoinPoint joinPoint, Long userId, String boardCode) throws Throwable {
        Object result = joinPoint.proceed();
        List<String> allowedAuthorities = new ArrayList<>();
        List<String> deniedAuthorities = new ArrayList<>();

        boolean isLoggedIn = userId != null;

        boolean canWrite = isLoggedIn
                ? postAclManager.hasPermission(userId, boardCode, "WRITE")
                : postAclManager.hasAllowPermissionForAnonymous(boardCode, "WRITE");
        if (canWrite) allowedAuthorities.add("WRITE");

        if (boardCode.equals("제휴게시판")) {
            if(!isLoggedIn) {
                boolean denyRead = postAclManager.hasDenyPermissionForAnonymous(boardCode, "READ");
                if (denyRead) deniedAuthorities.add("READ");
            } else allowedAuthorities.add("READ");
        } else allowedAuthorities.add("READ");

        PostListRes<?> postListRes = (PostListRes<?>) result;
        postListRes = postListRes.validAuthorities(allowedAuthorities, deniedAuthorities);

        return postListRes;
    }

    @Around(value = "execution(* ussum.homepage.application.post.service.PostManageService.getDataList(..)) && args(userId, page, ..)", argNames = "joinPoint,userId,page")
    public Object validAuthorityOfDataPostList(ProceedingJoinPoint joinPoint, Long userId, int page) throws Throwable {
        Object result = joinPoint.proceed();
        List<String> allowedAuthorities = new ArrayList<>();
        List<String> deniedAuthorities = new ArrayList<>();

        boolean isLoggedIn = userId != null;

        boolean canWrite = isLoggedIn
                ? postAclManager.hasPermission(userId, "자료집게시판", "WRITE")
                : postAclManager.hasAllowPermissionForAnonymous("자료집게시판", "WRITE");
        if (canWrite) allowedAuthorities.add("WRITE");

        allowedAuthorities.add("READ");

        PostListRes<?> postListRes = (PostListRes<?>) result;
        postListRes.validAuthorities(allowedAuthorities, deniedAuthorities);

        return result;
    }


    @Around(value = "execution(* ussum.homepage.application.post.service.PostManageService.getPost(..)) && args(userId, boardCode, ..)", argNames = "joinPoint,userId,boardCode")
    public Object validAuthorityOfPost(ProceedingJoinPoint joinPoint, Long userId, String boardCode) throws Throwable {
        Object result = joinPoint.proceed();
        List<String> allowedAuthority = new ArrayList<>();

        boolean isLoggedIn = userId != null;

        boolean canDelete = isLoggedIn
                ? postAclManager.hasPermission(userId, boardCode, "DELETE")
                : postAclManager.hasAllowPermissionForAnonymous(boardCode, "DELETE");
        if (canDelete) allowedAuthority.add("DELETE");

        if (boardCode.equals("청원게시판")) {
            boolean canReaction = isLoggedIn
                    ? postAclManager.hasPermission(userId, "청원게시판", "REACTION")
                    : postAclManager.hasAllowPermissionForAnonymous("청원게시판", "REACTION");
            if (canReaction) allowedAuthority.add("REACTION");
        }

        PostDetailRes<?> postDetailRes = (PostDetailRes<?>) result;
        postDetailRes.validAuthority(allowedAuthority);

        return result;
    }

    @Around(value = "execution(* ussum.homepage.application.comment.service.PostCommentManageService.getCommentList(..)) && args(userId,..)", argNames = "joinPoint,userId")
    public Object validCommentAuthorityOfPetitionPost(ProceedingJoinPoint joinPoint, Long userId) throws Throwable {
        Object result = joinPoint.proceed();
        List<String> allowedAuthority = new ArrayList<>();

        boolean isLoggedIn = userId != null;

        boolean canComment = isLoggedIn
                ? postAclManager.hasPermission(userId, "청원게시판", "COMMENT")
                : postAclManager.hasAllowPermissionForAnonymous("청원게시판", "COMMENT");

        boolean canReaction = isLoggedIn
                ? postAclManager.hasPermission(userId, "청원게시판", "REACTION")
                : postAclManager.hasAllowPermissionForAnonymous("청원게시판", "REACTION");

        boolean canDeleteComment = isLoggedIn
                ? postAclManager.hasPermission(userId, "청원게시판", "DELETE_COMMENT")
                : postAclManager.hasAllowPermissionForAnonymous("청원게시판", "DELETE_COMMENT");

        if (canReaction) allowedAuthority.add("REACTION");
        if (canDeleteComment) allowedAuthority.add("DELETE_COMMENT");
        if (canComment) allowedAuthority.add("COMMENT");

        CommentListResponse postCommentRes = (CommentListResponse) result;
        postCommentRes.validAuthority(allowedAuthority);

        return result;
    }

    @Around(value = "execution(* ussum.homepage.application.post.service.PostManageService.searchPost(..)) && args(userId, page, take, q, boardCode, ..)", argNames = "joinPoint,userId, page, take, q, boardCode")
    public Object validAuthorityOfSearchPostList(ProceedingJoinPoint joinPoint, Long userId, int page, int take, String q, String boardCode) throws Throwable {
        Object result = joinPoint.proceed();
        List<String> allowedAuthorities = new ArrayList<>();
        List<String> deniedAuthorities = new ArrayList<>();

        boolean isLoggedIn = userId != null;

        boolean canWrite = isLoggedIn
                ? postAclManager.hasPermission(userId, boardCode, "WRITE")
                : postAclManager.hasAllowPermissionForAnonymous(boardCode, "WRITE");
        if (canWrite) allowedAuthorities.add("WRITE");

        if (boardCode.equals("제휴게시판")) {
            if(!isLoggedIn) {
                boolean denyRead = postAclManager.hasDenyPermissionForAnonymous(boardCode, "READ");
                if (denyRead) deniedAuthorities.add("READ");
            } else allowedAuthorities.add("READ");
        } else allowedAuthorities.add("READ");

        PostListRes<?> postListRes = (PostListRes<?>) result;
        postListRes = postListRes.validAuthorities(allowedAuthorities, deniedAuthorities);

        return postListRes;
    }

    @Around(value = "execution(* ussum.homepage.application.post.service.PostManageService.searchDataList(..)) && args(userId, page, ..)", argNames = "joinPoint,userId,page")
    public Object validAuthorityOfSearchDataPostList(ProceedingJoinPoint joinPoint, Long userId, int page) throws Throwable {
        Object result = joinPoint.proceed();
        List<String> allowedAuthorities = new ArrayList<>();
        List<String> deniedAuthorities = new ArrayList<>();

        boolean isLoggedIn = userId != null;

        boolean canWrite = isLoggedIn
                ? postAclManager.hasPermission(userId, "자료집게시판", "WRITE")
                : postAclManager.hasAllowPermissionForAnonymous("자료집게시판", "WRITE");
        if (canWrite) allowedAuthorities.add("WRITE");

        allowedAuthorities.add("READ");

        PostListRes<?> postListRes = (PostListRes<?>) result;
        postListRes.validAuthorities(allowedAuthorities, deniedAuthorities);

        return result;
    }

}

