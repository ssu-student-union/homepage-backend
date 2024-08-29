package ussum.homepage.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.domain.comment.service.PostCommentReader;
import ussum.homepage.domain.member.service.MemberManager;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.PostRepository;
import ussum.homepage.domain.post.exception.PostException;
import ussum.homepage.domain.postlike.service.PostReactionReader;
import ussum.homepage.infra.jpa.post.entity.Category;
import ussum.homepage.infra.utils.DateUtils;

import java.time.LocalDateTime;
import java.util.List;

import static ussum.homepage.global.error.status.ErrorStatus.POST_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PetitionPostProcessor {
    private final PostRepository postRepository;
    private final PostReactionReader postReactionReader;
    private final PostCommentReader postCommentReader;
    private final MemberManager memberManager;

    // 매일 자정에 실행되는 스케줄러
//    @Scheduled(cron = "0 0 0 * * *")
    @Scheduled(fixedDelay = 600000)
    @Transactional
    public void scheduledStatusUpdate() {
        List<Post> posts = postRepository.findAllByCategory(List.of("진행중"));
        posts.forEach(post -> processStatus(post, postReactionReader.countPostReactionsByType(post.getId(), "like")));
    }

    /**
     * 좋아요 수 변경 시 호출되는 메소드(Trigger)
     */
    @Transactional
    public void onLikeCountChanged(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(POST_NOT_FOUND));

        if (post.getCategory().equals("종료됨") || post.getCategory().equals("답변완료") ) {
            return; // 최종 상태("종료됨", "답변완료")인 경우 상태를 업데이트하지 않음
        }
        Integer likeCount = postReactionReader.countPostReactionsByType(postId, "like");
        processStatus(post, likeCount);
    }

    /**
     * 중앙운영위원회가 댓글을 달았을 때 호출되는 메소드 (Trigger)
     */
    @Transactional
    public void onAdminCommentPosted(Long postId, String commentType) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(POST_NOT_FOUND));

        if (commentType.equals("OFFICIAL")) {
            if ("접수완료".equals(post.getCategory())) {
                handleReceivedStatus(post);
            }
        }
    }

    public void processStatus(Post post, Integer likeCount) {
        String currentStatus = Category.fromEnumOrNull(post.getCategory());
        switch (currentStatus) {
            case "진행중":
                handleInProgressStatus(post, likeCount);
                break;
            case "접수완료":
                handleReceivedStatus(post);
                break;
            default:
                break;
        }
    }

    /**
     * '진행중' 청원일 때 30일 이내에 좋아요 100개를 달성하면 '접수완료' 청원으로 변경
     * '진행중' 청원일 때 30일이 지나면 '종료됨' 청원으로 변경
     */
    private void handleInProgressStatus(Post post, Integer likeCountOfPost) {
        LocalDateTime createdAt = DateUtils.parseHourMinSecFromCustomString(post.getCreatedAt());
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(createdAt.plusDays(30)) && likeCountOfPost >= 100) {
            updatePostCategoryAndOngoingStatus(post.getId(), "접수완료");
        }
        else if (now.isAfter(createdAt.plusDays(30))) {
            updatePostCategoryAndOngoingStatus(post.getId(), "종료됨");
        } else {
            System.out.println("Like count after status change: " + likeCountOfPost);
        }
    }

    /**
     * '접수된' 청원에서 관리자가 댓글을 달면 '답변완료' 청원으로 변경
     */
    private void handleReceivedStatus(Post post) {
        if (isAnsweredByAdmin(post)) {
            updatePostCategoryAndOngoingStatus(post.getId(),"답변완료");
        }
    }

    /**
     * 관리자(지금은 일단 '중앙운영위원회')에 의해 답글이 달렸는지 여부를 확인하는 로직
     * 중앙집행위원회의 MemberCode는 CENTRAL_OPERATION_COMMITTEE 이다.
     */
    private Boolean isAnsweredByAdmin(Post post) {
        return postCommentReader.getCommentListWithPostId(post.getId())
                .stream()
                .anyMatch(postComment -> memberManager.getCommentType(postComment.getUserId()).equals("OFFICIAL"));
    }

    /**
     * 해당 로직은 실제 청원게시물의 OnGoingStatus를 변경하는 로직
     */
    public void updatePostCategoryAndOngoingStatus(Long postId, String category) {
        postRepository.updatePostCategory(postId, category);
    }




}
