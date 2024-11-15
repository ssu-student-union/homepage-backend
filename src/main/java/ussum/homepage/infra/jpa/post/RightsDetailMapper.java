package ussum.homepage.infra.jpa.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ussum.homepage.domain.post.PostRepository;
import ussum.homepage.domain.post.RightsDetail;
import ussum.homepage.infra.jpa.post.entity.PostEntity;
import ussum.homepage.infra.jpa.post.entity.RightsDetailEntity;
import ussum.homepage.infra.jpa.post.entity.RightsDetailEntity.PersonType;
import ussum.homepage.infra.jpa.post.repository.PostJpaRepository;

@Component
@RequiredArgsConstructor
public class RightsDetailMapper {

    private final PostJpaRepository postJpaRepository;

    public RightsDetail toDomain(RightsDetailEntity rightsDetailEntity){
        return RightsDetail.of(rightsDetailEntity.getId(), rightsDetailEntity.getName(),
                rightsDetailEntity.getPhoneNumber(), rightsDetailEntity.getStudentId(),rightsDetailEntity.getMajor(),rightsDetailEntity.getPersonType(),
                rightsDetailEntity.getPostEntity().getId());
    }

    public RightsDetailEntity toEntity(RightsDetail rightsDetail) {  // non-static
        PostEntity postEntity = postJpaRepository.findById(rightsDetail.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        PersonType personType = rightsDetail.getPersonType();
        return RightsDetailEntity.of(rightsDetail.getId(), rightsDetail.getName(), rightsDetail.getPhoneNumber(),
                rightsDetail.getStudentId(), rightsDetail.getMajor(), personType, postEntity);
    }

}
