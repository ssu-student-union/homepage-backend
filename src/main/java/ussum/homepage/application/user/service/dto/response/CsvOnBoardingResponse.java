package ussum.homepage.application.user.service.dto.response;

import lombok.Builder;

@Builder
public record CsvOnBoardingResponse(
        boolean name,
        boolean groupName,
        boolean major
) {
    public static CsvOnBoardingResponse of(boolean name, boolean groupName, boolean major) {
        return CsvOnBoardingResponse.builder()
                .name(name)
                .groupName(groupName)
                .major(major)
                .build();
    }
}
