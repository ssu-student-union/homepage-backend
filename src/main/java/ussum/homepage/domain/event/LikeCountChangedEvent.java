package ussum.homepage.domain.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor  // 기본 생성자 추가
@Getter
public class LikeCountChangedEvent {
    private Long postId;
}