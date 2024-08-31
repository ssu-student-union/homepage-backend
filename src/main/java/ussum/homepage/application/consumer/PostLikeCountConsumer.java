package ussum.homepage.application.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ussum.homepage.domain.event.LikeCountChangedEvent;
import ussum.homepage.domain.post.Post;
import ussum.homepage.domain.post.PostRepository;
import ussum.homepage.domain.post.exception.PostException;
import ussum.homepage.domain.post.service.PetitionPostProcessor;
import ussum.homepage.domain.postlike.service.PostReactionReader;
import ussum.homepage.infra.jpa.post.entity.Category;
import ussum.homepage.infra.utils.DateUtils;

import java.time.LocalDateTime;

import static ussum.homepage.global.error.status.ErrorStatus.POST_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PostLikeCountConsumer {

    private final PostRepository postRepository;

    private final PostReactionReader postReactionReader;
    private final PetitionPostProcessor petitionPostProcessor;

    @KafkaListener(topics = "post-like-count-changed", groupId = "post-group")
    public void onLikeCountChanged(LikeCountChangedEvent event) {
        Long postId = event.getPostId();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(POST_NOT_FOUND));

        if (post.getCategory().equals("종료됨") || post.getCategory().equals("답변완료")) {
            return; // 최종 상태("종료됨", "답변완료")인 경우 상태를 업데이트하지 않음
        }
        Integer likeCount = postReactionReader.countPostReactionsByType(postId, "like");
        petitionPostProcessor.processStatus(post, likeCount);
    }
}
