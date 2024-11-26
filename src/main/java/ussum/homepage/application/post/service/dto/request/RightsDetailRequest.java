package ussum.homepage.application.post.service.dto.request;

import ussum.homepage.domain.post.RightsDetail;
import ussum.homepage.infra.jpa.post.entity.RightsDetailEntity.PersonType;

public record RightsDetailRequest(
        String name,
        String phoneNumber,
        String studentId,
        String major,
        String personType,
        Long postId
) {
    public RightsDetail toDomain(Long postId) {

        return RightsDetail.of(name, phoneNumber, studentId, major, personType, postId);
    }
}
