package ussum.homepage.application.user.service.dto.response;

import lombok.Builder;
import ussum.homepage.application.user.service.dto.request.OnBoardingRequest;

@Builder
public record OnBoardingResponse(
        boolean name,
        boolean studentId,
        boolean groupName,
        boolean major
) {
    public static OnBoardingResponse of(boolean name, boolean studentId, boolean groupName, boolean major) {
        return OnBoardingResponse.builder()
                .name(name)
                .studentId(studentId)
                .groupName(groupName)
                .major(major)
                .build();
    }
}
