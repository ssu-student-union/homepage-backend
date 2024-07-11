package ussum.homepage.application.user.service.dto.response;

import lombok.Builder;
import ussum.homepage.application.user.service.dto.request.OnBoardingRequest;

@Builder
public record OnBoardingResponse(
        String name,
        Long studentId,
        String groupName,
        String major
) {
    public static OnBoardingResponse of(OnBoardingRequest request, String groupName, String major) {
        return OnBoardingResponse.builder()
                .name(request.getName())
                .studentId(request.getStudentId())
                .groupName(groupName)
                .major(major)
                .build();
    }
}
