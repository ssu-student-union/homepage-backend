package ussum.homepage.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ussum.homepage.application.post.service.dto.response.SimplePostResponse;
import ussum.homepage.infra.jpa.group.entity.GroupCode;
import ussum.homepage.infra.jpa.member.entity.MemberCode;
import ussum.homepage.infra.jpa.post.dto.SimplePostDto;
import ussum.homepage.domain.post.Board;
import ussum.homepage.domain.post.BoardRepository;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.PostRepository;
import ussum.homepage.global.error.exception.GeneralException;
import ussum.homepage.infra.jpa.post.entity.Category;

import static ussum.homepage.global.error.status.ErrorStatus.BOARD_NOT_FOUND;
import static ussum.homepage.global.error.status.ErrorStatus.POST_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PostReader {
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    public Page<Post> getPostList(Pageable pageable, String boardCode) {
        return postRepository.findAllWithBoard(pageable, boardCode);
    }

    public Page<Post> getPostListByBoardIdAndCategory(Long boardId, Category category, Pageable pageable) {
        return postRepository.findAllByBoardIdAndCategory(boardId, category, pageable);
    }

    public Page<SimplePostResponse> findSimplePostDtoListByBoardCode(String boardCode, Pageable pageable){
        return postRepository.findPostDtoListByBoardCode(boardCode, pageable);
    }

    public Page<Post> getPostListByBoardIdAndGroupCodeAndMemberCode(Long boardId, GroupCode groupCode, MemberCode memberCode, Pageable pageable ){
        return postRepository.findAllByBoardIdAndGroupCodeAndMemberCode(boardId, groupCode,memberCode, pageable);
    }

    public Page<Post> getPostListByGroupCodeAndMemberCodeAndSubCategory(GroupCode groupCode, MemberCode memberCode, String subCategory, Pageable pageable){
        return postRepository.findAllByGroupCodeAndMemberCodeAndSubCategory(groupCode,memberCode,subCategory, pageable);
    }

    public Post getPostWithId(Long postId) {
        return postRepository.findById(postId).orElseThrow(()-> new GeneralException(POST_NOT_FOUND));
    }

    public Post getPostWithBoardCodeAndPostId(String boardCode, Long postId) {
        Board board = boardRepository.findByBoardCode(boardCode).orElseThrow(
                () -> new GeneralException(BOARD_NOT_FOUND));

        return postRepository.findByBoardIdAndId(board.getId(), postId).orElseThrow(() -> new GeneralException(POST_NOT_FOUND));
    }

    public Post getPostWithBoardCodeForEditAndDelete(String boardCode,Long postId) {
        Board board = boardRepository.findByBoardCode(boardCode).orElseThrow(
                () -> new GeneralException(BOARD_NOT_FOUND));

        return postRepository.findByBoardIdAndIdForEditAndDelete(board.getId(), postId).orElseThrow(() -> new GeneralException(POST_NOT_FOUND));
    }

    public Page<Post> getPostListBySearch(Pageable pageable, String boardCode, String q, String categoryCode) {
        return postRepository.findBySearchCriteria(pageable, boardCode, q, categoryCode);
    }
}
