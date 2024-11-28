package ussum.homepage.application.post.service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import ussum.homepage.domain.post.RightsDetail;
import ussum.homepage.infra.jpa.post.entity.RightsDetailEntity.PersonType;

@Schema(description = "rightsDetail 생성과 수정 데이터 스키마")
public record RightsDetailRequest(
        String name,
        String phoneNumber,
        String studentId,
        String major,
        String personType
) {
    public RightsDetail toDomain(Long postId) {

        return RightsDetail.of(name, phoneNumber, studentId, major, personType, postId);
    }
}
