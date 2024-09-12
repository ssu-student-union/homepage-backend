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
    private final PostAclHandler postAclHandler;

    // 게시물 전체 조회 권한 검사
    @Around(value = "execution(* ussum.homepage.application.post.service.PostManageService.getPostList(..)) && args(userId, boardCode, ..)", argNames = "joinPoint,userId,boardCode")
    public Object validAuthorityOfPostList(ProceedingJoinPoint joinPoint, Long userId, String boardCode) throws Throwable {
        Object result = joinPoint.proceed();
        PostListRes<?> postListRes = (PostListRes<?>) result;
        postListRes = postAclHandler.applyPermissionsToPostList(postListRes, userId, boardCode);
        return postListRes;
    }

    // 자료집 게시물 전체 조회 권한 검사
    @Around(value = "execution(* ussum.homepage.application.post.service.PostManageService.getDataList(..)) && args(userId, page, ..)", argNames = "joinPoint,userId,page")
    public Object validAuthorityOfDataPostList(ProceedingJoinPoint joinPoint, Long userId, int page) throws Throwable {
        Object result = joinPoint.proceed();
        PostListRes<?> postListRes = (PostListRes<?>) result;
        postListRes = postAclHandler.applyPermissionsToPostList(postListRes, userId, "자료집게시판");
        return postListRes;
    }

    // 게시물 단건 권한 검사
    @Around(value = "execution(* ussum.homepage.application.post.service.PostManageService.getPost(..)) && args(userId, boardCode, ..)", argNames = "joinPoint,userId,boardCode")
    public Object validAuthorityOfPost(ProceedingJoinPoint joinPoint, Long userId, String boardCode) throws Throwable {
        Object result = joinPoint.proceed();
        PostDetailRes<?> postDetailRes = (PostDetailRes<?>) result;
        postAclHandler.applyPermissionsToPostDetail(postDetailRes, userId, boardCode);
        return postDetailRes;
    }

    // 댓글 권한 검사
    @Around(value = "execution(* ussum.homepage.application.comment.service.PostCommentManageService.getCommentList(..)) && args(userId,..)", argNames = "joinPoint,userId")
    public Object validCommentAuthorityOfPetitionPost(ProceedingJoinPoint joinPoint, Long userId) throws Throwable {
        Object result = joinPoint.proceed();
        CommentListResponse commentListRes = (CommentListResponse) result;
        postAclHandler.applyPermissionsToCommentList(commentListRes, userId);
        return commentListRes;
    }

    // 검색된 게시물 권한 검사
    @Around(value = "execution(* ussum.homepage.application.post.service.PostManageService.searchPost(..)) && args(userId, page, take, q, boardCode, ..)", argNames = "joinPoint,userId,page,take,q,boardCode")
    public Object validAuthorityOfSearchPostList(ProceedingJoinPoint joinPoint, Long userId, int page, int take, String q, String boardCode) throws Throwable {
        Object result = joinPoint.proceed();
        PostListRes<?> postListRes = (PostListRes<?>) result;
        postAclHandler.applyPermissionsToPostList(postListRes, userId, boardCode);
        return postListRes;
    }

    // 검색된 자료집 게시물 권한 검사
    @Around(value = "execution(* ussum.homepage.application.post.service.PostManageService.searchDataList(..)) && args(userId, page, ..)", argNames = "joinPoint,userId,page")
    public Object validAuthorityOfSearchDataPostList(ProceedingJoinPoint joinPoint, Long userId, int page) throws Throwable {
        Object result = joinPoint.proceed();
        PostListRes<?> postListRes = (PostListRes<?>) result;
        postAclHandler.applyPermissionsToPostList(postListRes, userId, "자료집게시판");
        return postListRes;
    }
}
