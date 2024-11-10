package ussum.homepage.application.post.service.dto.request;

import ussum.homepage.domain.post.RightsDetail;

public record RightsDetailRequest(
        String name,
        String studentId,
        String major,
        String personType
) {
    public RightsDetail toDomain() {
        return RightsDetail.of(null, name, studentId, major, personType);
    }
}
