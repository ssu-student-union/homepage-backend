package ussum.homepage.application.post.service.dto.response.postSave;

import lombok.Builder;
import lombok.Getter;
import ussum.homepage.domain.post.Post;

@Getter
@Builder
public class PostCreateResponse {
    private Long post_id;
    private String boardCode;

    public static PostCreateResponse of(Long id, String boardCode){
        return PostCreateResponse.builder()
                .post_id(id)
                .boardCode(boardCode)
                .build();
    }
}
