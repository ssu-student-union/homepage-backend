package ussum.homepage.domain.acl.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ussum.homepage.application.comment.service.dto.response.CommentListResponse;
import ussum.homepage.application.post.service.dto.response.postDetail.PostDetailRes;
import ussum.homepage.application.post.service.dto.response.postList.PostListRes;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PostAclHandler {
    private final PostAclManager postAclManager;

    // 공통 로직: 권한 적용
    public PostListRes<?> applyPermissionsToPostList(PostListRes<?> postListRes, Long userId, String boardCode) {
        List<String> allowedAuthorities = new ArrayList<>();
        List<String> deniedAuthorities = new ArrayList<>();
        boolean isLoggedIn = userId != null;

        addPermissionAuthorities(allowedAuthorities, deniedAuthorities, userId, boardCode, isLoggedIn);

        return postListRes.validAuthorities(allowedAuthorities, deniedAuthorities);
    }

    public void applyPermissionsToPostDetail(PostDetailRes<?> postDetailRes, Long userId, String boardCode) {
        List<String> allowedAuthorities = new ArrayList<>();
        boolean isLoggedIn = userId != null;

        if (isLoggedIn) {
            if (postAclManager.hasPermission(userId, boardCode, "DELETE")) {
                allowedAuthorities.add("DELETE");
            }
            if (boardCode.equals("청원게시판") && postAclManager.hasPermission(userId, boardCode, "REACTION")) {
                allowedAuthorities.add("REACTION");
            }
        }

        postDetailRes.validAuthority(allowedAuthorities);
    }

    public CommentListResponse applyPermissionsToCommentList(CommentListResponse commentListRes, Long userId) {
        List<String> allowedAuthorities = new ArrayList<>();
        boolean isLoggedIn = userId != null;

        if (isLoggedIn) {
            if (postAclManager.hasPermission(userId, "청원게시판", "COMMENT")) {
                allowedAuthorities.add("COMMENT");
            }
            if (postAclManager.hasPermission(userId, "청원게시판", "REACTION")) {
                allowedAuthorities.add("REACTION");
            }
            if (postAclManager.hasPermission(userId, "청원게시판", "DELETE_COMMENT")) {
                allowedAuthorities.add("DELETE_COMMENT");
            }
        }

        return commentListRes.validAuthorities(allowedAuthorities);
    }

    private void addPermissionAuthorities(List<String> allowedAuthorities, List<String> deniedAuthorities, Long userId, String boardCode, boolean isLoggedIn) {
        boolean canWrite = isLoggedIn
                ? postAclManager.hasPermission(userId, boardCode, "WRITE")
                : postAclManager.hasAllowPermissionForAnonymous(boardCode, "WRITE");

        if (canWrite) {
            allowedAuthorities.add("WRITE");
        }

        if (boardCode.equals("인권신고게시판")){
            if (isLoggedIn) {
                boolean canAllRead = postAclManager.hasPermission(userId, boardCode, "ALL_READ");
                if (canAllRead) {
                    allowedAuthorities.add("ALL_READ");
                } else
                    deniedAuthorities.add("ALL_READ");
            }else deniedAuthorities.add("ALL_READ");
        }else if (boardCode.equals("제휴게시판")) {
            if (!isLoggedIn) {
                boolean denyRead = postAclManager.hasDenyPermissionForAnonymous(boardCode, "READ");
                if (denyRead) {
                    deniedAuthorities.add("READ");
                }
            } else allowedAuthorities.add("READ");
        } else allowedAuthorities.add("READ");
    }
}
