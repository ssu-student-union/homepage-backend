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
import ussum.homepage.domain.postlike.service.PostReactionReader;
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

    private final Map<String, BiFunction<Post, Integer, ? extends PostListResDto>> postResponseMap = Map.of(
            "공지사항게시판", (post, ignored) -> NoticePostResponse.of(post),
            "분실물게시판", (post, ignored) -> LostPostResponse.of(post),
            "제휴게시판", (post, ignored) -> PartnerPostResponse.of(post),
            "감사기구게시판", (post, ignored) -> AuditPostResponseDto.of(post),
            "청원게시판", PetitionPostResponse::of
    );

    public PostListRes<?> getPostList(int page, int take, String boardCode) {
        Board board = boardReader.getBoardWithBoardCode(boardCode);
        Pageable pageable = PageInfo.of(page, take);
        Page<Post> postList = postReader.getPostListByBoardId(board.getId(), pageable);
        PageInfo pageInfo = PageInfo.of(postList);

        BiFunction<Post, Integer, ? extends PostListResDto> responseFunction = postResponseMap.get(board.getName());

        if (responseFunction == null) {
            throw new IllegalArgumentException("Unknown board type: " + board.getName());
        }

        List<? extends PostListResDto> responseList = postList.getContent().stream()
                .map(post -> {
                    if (board.getName().equals("청원게시판")) {
                        Integer likeCount = postReactionReader.countPostReactionsByType(post.getId(), "like");
                        return responseFunction.apply(post, likeCount);
                    } else {
                        return responseFunction.apply(post, null);
                    }
                })
                .toList();

        return PostListRes.of(responseList, pageInfo);
    }
}