package ussum.homepage.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.comment.service.PostCommentReader;
import ussum.homepage.domain.member.service.MemberManager;
import ussum.homepage.domain.post.Category;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.PostRepository;
import ussum.homepage.domain.postlike.service.PostReactionReader;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostStatusProcessor {
    private final PostRepository postRepository;
    private final PostCommentReader postCommentReader;
    private final PostReactionReader postReactionReader;
    private final MemberManager memberManager;
    private final CategoryReader categoryReader;

    public String processStatus(Post post) {
        //현재 게시물 상태 checking
        String currentStatus = Optional.ofNullable(post.getOnGoingStatus())
                .orElseThrow(() -> new IllegalStateException("Post status cannot be null"));
        //게시물의 좋아요 수를 미리 가져옴
        Integer likeCountOfPost = postReactionReader.countPostReactionsByType(post.getId(), "like");
        switch (currentStatus) {
            case "IN_PROGRESS":
                return handleInProgressStatus(post,likeCountOfPost);
            case "RECEIVED":
                return handleReceivedStatus(post);
        }
        return currentStatus;
    }

    /**
     * 해당 로직은 실제 청원게시물의 OnGoingStatus를 변경하는 로직
     */
    public String updatePostOngoingStatus(Long postId, String onGoingStatus) {
        Category category = categoryReader.getCategoryWithCode(onGoingStatus);
        return postRepository.updatePostOngoingStatus(postId, onGoingStatus, category).getOnGoingStatus();
    }

    /**
     * '진행중' 청원일 때 30일이 지난 시점에 좋아요 100개를 달성하지 못하면 '종료됨'
     * '진행중' 청원일 때 30일이 지난 시점에 좋아요 100개를 달성하면 '접수된' 청원으로 변경
     */
    private String handleInProgressStatus(Post post, Integer likeCountOfPost) {
        LocalDateTime createdAt = LocalDateTime.parse(post.getCreatedAt());
        // 30일이 경과한 경우
        if (LocalDateTime.now().isAfter(createdAt.plusDays(30))) {
            // 30일 동안 좋아요 100개를 달성하지 못한 경우에만 종료됨 상태로 변경
            if (likeCountOfPost < 100) {
                return updatePostOngoingStatus(post.getId(), "COMPLETED");
            } else return updatePostOngoingStatus(post.getId(), "RECEIVED");
        } else {
            if (likeCountOfPost >= 100) {
                return updatePostOngoingStatus(post.getId(), "RECEIVED");
            } else return "IN_PROGRESS";
        }
        // 30일 이내면 아직 상태를 변경하지 않음
//        return "IN_PROGRESS";
    }

    /**
     * '접수된' 청원에서 관리자가 댓글을 달면 '답변완료' 청원으로 변경
     */
    private String handleReceivedStatus(Post post) {
        if (isAnsweredByAdmin(post)) {
            return updatePostOngoingStatus(post.getId(),"ANSWERED");
        }
        return "RECEIVED";
    }

    /**
     * 관리자(지금은 일단 '중앙집행위원회')에 의해 답글이 달렸는지 여부를 확인하는 로직
     * 중앙집행위원회의 MemberCode는 CENTRAL_OPERATION_COMMITTEE 이다.
     */
    private Boolean isAnsweredByAdmin(Post post) {
        return postCommentReader.getCommentListWithPostId(post.getId())
                .stream()
                .anyMatch(postComment -> memberManager.validMemberWithUserId(postComment.getUserId()));
    }

}
