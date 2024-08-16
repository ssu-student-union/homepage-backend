package ussum.homepage.application.post.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ussum.homepage.application.post.service.dto.response.postList.*;
import ussum.homepage.domain.comment.service.formatter.TriFunction;
import ussum.homepage.domain.post.Board;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.service.BoardReader;
import ussum.homepage.domain.post.service.PostReader;
import ussum.homepage.domain.postlike.service.PostReactionReader;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.service.UserReader;
import ussum.homepage.global.common.PageInfo;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import static ussum.homepage.global.error.status.ErrorStatus.POST_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PostManageService {

    private final BoardReader boardReader;
    private final PostReader postReader;
    private final PostReactionReader postReactionReader;
    private final UserReader userReader;

    private final Map<String, TriFunction<Post, Integer, User, ? extends PostListResDto>> postResponseMap = Map.of(
            "공지사항게시판", (post, ignored1, user) -> NoticePostResponse.of(post, user),
            "분실물게시판", (post, ignored1, ignored2) -> LostPostResponse.of(post),
            "제휴게시판", (post, ignored1, ignored2) -> PartnerPostResponse.of(post),
            "감사기구게시판", (post, ignored1, ignored2) -> AuditPostResponseDto.of(post),
            "청원게시판", (post, likeCount, ignored2) -> PetitionPostResponse.of(post, likeCount)
    );

    public PostListRes<?> getPostList(int page, int take, String boardCode) {
        Board board = boardReader.getBoardWithBoardCode(boardCode);
        Pageable pageable = PageInfo.of(page, take);
        Page<Post> postList = postReader.getPostListByBoardId(board.getId(), pageable);
        PageInfo pageInfo = PageInfo.of(postList);

        TriFunction<Post, Integer, User, ? extends PostListResDto> responseFunction = postResponseMap.get(board.getName());

        if (responseFunction == null) {
            throw new IllegalArgumentException("Unknown board type: " + board.getName());
        }

        List<? extends PostListResDto> responseList = postList.getContent().stream()
                .map(post -> {
                    User user = null;
                    Integer likeCount = null;
                    switch (board.getName()) {
                        case "공지사항게시판":
                            user = userReader.getUserWithId(post.getUserId());
                            return responseFunction.apply(post, null, user);
                        case "분실물게시판":
                        case "제휴게시판":
                        case "감사기구게시판":
                            return responseFunction.apply(post, null, null);
                        case "청원게시판":
                            likeCount = postReactionReader.countPostReactionsByType(post.getId(), "like");
                            return responseFunction.apply(post, likeCount, null);
                        default:
                            throw new EntityNotFoundException(String.valueOf(POST_NOT_FOUND));
                    }
                })
                .toList();

        return PostListRes.of(responseList, pageInfo);
    }
}
//스위치 사용 로직
/*
package ussum.homepage.application.post.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ussum.homepage.application.post.service.dto.response.postList.*;
import ussum.homepage.domain.post.Board;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.service.BoardReader;
import ussum.homepage.domain.post.service.PostReader;
import ussum.homepage.global.common.PageInfo;

import java.util.List;

import static ussum.homepage.global.error.status.ErrorStatus.POST_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PostManageService {

    private final BoardReader boardReader;
    private final PostReader postReader;

    public PostListRes<?> getPostList(int page, int take, String boardCode) {
        Board board = boardReader.getBoardWithBoardCode(boardCode);
        Pageable pageable = PageInfo.of(page, take);
        Page<Post> postList = postReader.getPostListByBoardId(board.getId(), pageable);
        PageInfo pageInfo = PageInfo.of(postList);

        switch (board.getName()) {
            case "공지사항":
                return getNoticePostList(postList, pageInfo);
            case "분실물":
                return getLostPostList(postList, pageInfo);
            case "제휴":
                return getPartnerPostList(postList, pageInfo);
            case "감사기구":
                return getAuditPostList(postList, pageInfo);
            case "청원":
                // return getPetitionPostList(postList, pageInfo);
            default:
                throw new EntityNotFoundException(String.valueOf(POST_NOT_FOUND));
        }
    }

    private PostListRes<NoticePostResponse> getNoticePostList(Page<Post> postList, PageInfo pageInfo) {
        List<NoticePostResponse> responseList = postList.getContent().stream()
                .map(NoticePostResponse::of)
                .toList();
        return PostListRes.of(responseList, pageInfo);
    }

    private PostListRes<LostPostResponse> getLostPostList(Page<Post> postList, PageInfo pageInfo) {
        List<LostPostResponse> responseList = postList.getContent().stream()
                .map(LostPostResponse::of)
                .toList();
        return PostListRes.of(responseList, pageInfo);
    }

    private PostListRes<PartnerPostResponse> getPartnerPostList(Page<Post> postList, PageInfo pageInfo) {
        List<PartnerPostResponse> responseList = postList.getContent().stream()
                .map(PartnerPostResponse::of)
                .toList();
        return PostListRes.of(responseList, pageInfo);
    }

    private PostListRes<AuditPostResponseDto> getAuditPostList(Page<Post> postList, PageInfo pageInfo) {
        List<AuditPostResponseDto> responseList = postList.getContent().stream()
                .map(AuditPostResponseDto::of)
                .toList();
        return PostListRes.of(responseList, pageInfo);
    }
}

 */