package ussum.homepage.domain.post.service.factory;

import org.springframework.stereotype.Component;
import ussum.homepage.application.post.service.dto.request.PostCreateRequest;

@Component
public interface PostFactory {
    PostCreateRequest convert(String boardCode, PostCreateRequest request);
}
