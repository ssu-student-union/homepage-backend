package ussum.homepage.domain.acl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ussum.homepage.application.comment.service.dto.response.CommentListResponse;
import ussum.homepage.application.post.service.dto.response.postDetail.PostDetailRes;
import ussum.homepage.application.post.service.dto.response.postList.PostListRes;
import ussum.homepage.domain.acl.strategy.BoardPermissionStrategy;
import ussum.homepage.domain.acl.strategy.DefaultBoardStrategy;
import ussum.homepage.domain.acl.strategy.PartnerBoardStrategy;
import ussum.homepage.domain.acl.strategy.RightsBoardStrategy;
import ussum.homepage.infra.jpa.acl.entity.Action;
import ussum.homepage.infra.jpa.post.entity.BoardCode;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostAclHandler {
    private final PostAclManager postAclManager;

    public PostListRes<?> applyPermissionsToPostList(PostListRes<?> postListRes, Long userId, String boardCode) {
        List<Action> allowedActions = new ArrayList<>();
        List<Action> deniedActions = new ArrayList<>();
        boolean isLoggedIn = userId != null;

        determinePostListPermissions(allowedActions, deniedActions, userId, boardCode, isLoggedIn);

        return postListRes.validAuthorities(
                allowedActions.stream().map(Action::name).collect(Collectors.toList()),
                deniedActions.stream().map(Action::name).collect(Collectors.toList())
        );
    }

    public void applyPermissionsToPostDetail(PostDetailRes<?> postDetailRes, Long userId, String boardCode) {
        List<Action> allowedActions = new ArrayList<>();
        boolean isLoggedIn = userId != null;

        if (isLoggedIn) {
            if (postAclManager.hasPermission(userId, boardCode, Action.DELETE)) {
                allowedActions.add(Action.DELETE);
            }
            if (BoardCode.PETITION.name().equals(boardCode) &&
                    postAclManager.hasPermission(userId, boardCode, Action.REACTION)) {
                allowedActions.add(Action.REACTION);
            }
        }

        postDetailRes.validAuthority(
                allowedActions.stream().map(Action::name).collect(Collectors.toList())
        );
    }

    public CommentListResponse applyPermissionsToCommentList(CommentListResponse commentListRes, Long userId) {
        List<Action> allowedActions = new ArrayList<>();
        boolean isLoggedIn = userId != null;

        if (isLoggedIn) {
            // 권한 체크 메소드 호출을 Map으로 구성
            Map<String, List<Action>> boardPermissions = new HashMap<>();
            boardPermissions.put(BoardCode.PETITION.name(), Arrays.asList(Action.COMMENT, Action.REACTION, Action.DELETE_COMMENT));
            boardPermissions.put(BoardCode.RIGHTS.name(), Arrays.asList(Action.COMMENT));

            boardPermissions.forEach((board, actions) -> {
                actions.forEach(action -> {
                    if (postAclManager.hasPermission(userId, board, action)) {
                        allowedActions.add(action);
                    }
                });
            });
        }

        return commentListRes.validAuthorities(
                allowedActions.stream().map(Action::name).collect(Collectors.toList())
        );
    }

    private void determinePostListPermissions(List<Action> allowed, List<Action> denied, Long userId, String boardCode, boolean isLoggedIn) {
        // WRITE 권한 체크
        if (isLoggedIn ? postAclManager.hasPermission(userId, boardCode, Action.WRITE)
                : postAclManager.hasAllowPermissionForAnonymous(boardCode, Action.WRITE)) {
            allowed.add(Action.WRITE);
        }

        // 게시판별 권한 처리를 전략 패턴으로 구현
        BoardPermissionStrategy strategy = getBoardPermissionStrategy(boardCode);
        strategy.applyPermissions(allowed, denied, userId, isLoggedIn, postAclManager);
    }

    private BoardPermissionStrategy getBoardPermissionStrategy(String boardCode) {
        switch(BoardCode.valueOf(boardCode)) {
            case RIGHTS:
                return new RightsBoardStrategy();
            case PARTNER:
                return new PartnerBoardStrategy();
            default:
                return new DefaultBoardStrategy();
        }
    }
}
