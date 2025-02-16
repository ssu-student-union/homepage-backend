package ussum.homepage.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ussum.homepage.application.post.service.dto.response.SimplePostResponse;
import ussum.homepage.infra.jpa.group.entity.GroupCode;
import ussum.homepage.infra.jpa.member.entity.MajorCode;
import ussum.homepage.infra.jpa.member.entity.MemberCode;
import ussum.homepage.domain.post.Board;
import ussum.homepage.domain.post.BoardRepository;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.PostRepository;
import ussum.homepage.global.error.exception.GeneralException;
import ussum.homepage.infra.jpa.post.entity.Category;
import ussum.homepage.infra.jpa.post.entity.FileCategory;
import ussum.homepage.infra.jpa.post.entity.SuggestionTarget;

import java.util.List;

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

    public Page<Post> getPostListByBoardIdAndCategoryAndSuggestionTarget(Long boardId, Category category, SuggestionTarget suggestionTarget, Pageable pageable ){
        return postRepository.findAllByBoardIdAndCategoryAndSuggestionTarget(boardId, category,suggestionTarget, pageable);
    }

    public Page<Post> getPostListByBoardIdAndQnAMajorCodeAndQnAMemberCode(Long boardId, MajorCode qnaMajorCode, MemberCode qnaMemberCode, Pageable pageable){
        return postRepository.findAllByBoardIdAndQnAMajorCodeAndQnAMemberCode(boardId, qnaMajorCode, qnaMemberCode, pageable);
    }

    public Page<Post> getPostListByFileCategories(List<FileCategory> fileCategories, Pageable pageable){
        return postRepository.findAllByFileCategories(fileCategories, pageable);
    }

    public Page<Post> searchPostListByFileCategories(String q, List<FileCategory> fileCategories, Pageable pageable){
        return postRepository.searchAllByFileCategories(q, fileCategories, pageable);
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

    public Page<Post> searchPostListByBoardIdAndGroupCodeAndMemberCode(Long boardId, String q, GroupCode groupCode, MemberCode memberCode, Pageable pageable) {
        return postRepository.searchAllByBoardIdAndGroupCodeAndMemberCode(boardId, q, groupCode,memberCode, pageable);
    }

    public Page<Post> searchPostListByBoardIdAndCategory(Long boardId, String q, Category category, Pageable pageable) {
        return postRepository.searchAllByBoardIdAndCategory(boardId, q, category, pageable);
    }

    public Page<Post> searchPostListByBoardIdAndCategoryAndQnAMajorCodeAndQnAMemberCode(Long boardId, String q, Category category, MajorCode qnaMajorCode, MemberCode qnaMemberCode, Pageable pageable) {
        return postRepository.searchAllByBoardIdAndCategoryAndQnAMajorCodeAndQnAMemberCode(boardId, q, category, qnaMajorCode, qnaMemberCode, pageable);
    }

    public Page<Post> getPostListByBoardIdAndCategoryAndUserId(Long boardId, Category category, Pageable pageable, Long userId) {
        return postRepository.searchAllByBoardIdAndCategoryAndUserId(boardId,category,pageable,userId);
    }

    public Page<Post> searchPostListByBoardIdAndCategoryAndUserId(Long boardId, String q, Category category, Pageable pageable, Long userId) {
        return postRepository.searchAllByBoardIdAndCategoryAndUserIdTwo(boardId,q,category,pageable,userId);
    }

    public Page<Post> getMyPosts(Long userId, Pageable pageable) {
        return postRepository.findAllByUserId(userId, pageable);
    }

    public Page<Post> searchMyPosts(Long userId, String q, Pageable pageable) {
        return postRepository.searchAllByUserId(userId, q, pageable);
    }

}
