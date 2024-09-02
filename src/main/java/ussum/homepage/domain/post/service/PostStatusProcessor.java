package ussum.homepage.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.domain.post.PostRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostStatusProcessor {
    private final PostRepository postRepository;

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행됨, 특정 게시물의 생성시간이 3일이 지나면 스케줄러에 의해서 GENERAL 상태로 바뀜
    @Transactional
    public void updatePostStatusNewToGeneral() {
        LocalDateTime dueDateOfNew = LocalDateTime.now().minusDays(3);
        postRepository.updatePostStatusNewToGeneral(dueDateOfNew);
    }
}
