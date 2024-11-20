package ussum.homepage.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ussum.homepage.application.post.service.dto.response.SimplePostResponse;
import ussum.homepage.infra.jpa.group.entity.GroupCode;
import ussum.homepage.infra.jpa.member.entity.MemberCode;
import ussum.homepage.infra.jpa.post.entity.Category;
import ussum.homepage.infra.jpa.post.entity.FileCategory;
import ussum.homepage.infra.jpa.post.entity.SuggestionTarget;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Optional<Post> findById(Long postId);
    Page<Post> findAllWithBoard(Pageable pageable, String boardCode);
    Optional<Post> findByBoardIdAndId(Long boardId,Long postId);
    Optional<Post> findByBoardIdAndIdForEditAndDelete(Long boardId,Long postId);
    Page<Post> findAllByBoardIdAndCategory(Long boardId, Category category, Pageable pageable);
    Post save(Post post);
    void updatePostStatusNewToGeneral(LocalDateTime dueDateOfNew);
    void updatePostStatusEmergencyToGeneralInBatches();
    void delete(Post post);
    Page<Post> findBySearchCriteria(Pageable pageable,String boardCode, String q, String categoryCode);
    Page<SimplePostResponse> findPostDtoListByBoardCode(String boardCode, Pageable pageable);
    List<Post> findAllByCategory(List<String> statuses);
    Post updatePostCategory(Post post, Category category);
    //    Page<Post> findAllByGroupCodeAndMemberCodeAndSubCategory(GroupCode groupCode, MemberCode memberCode, String subCategory, Pageable pageable);
    Page<Post> findAllByFileCategories(List<FileCategory> fileCategories, Pageable pageable);
    Page<Post> findAllByBoardIdAndGroupCodeAndMemberCode(Long boarId, GroupCode groupCode, MemberCode memberCode, Pageable pageable);
    Page<Post> findAllByBoardIdAndCategoryAndSuggestionTarget(Long boarId, Category category, SuggestionTarget suggestionTarget, Pageable pageable);
    Page<Post> searchAllByBoardIdAndGroupCodeAndMemberCode(Long boardId, String q, GroupCode groupCode, MemberCode memberCode, Pageable pageable);
    Page<Post> searchAllByBoardIdAndCategory(Long boardId, String q, Category category, Pageable pageable);
    Page<Post> searchAllByFileCategories(String q, List<FileCategory> fileCategories, Pageable pageable);

    Page<Post> searchAllByBoardIdAndCategoryAndUserId(Long boardId, Category category, Pageable pageable, Long userId);
}
