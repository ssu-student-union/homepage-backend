package ussum.homepage.application.user.service.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CollegeAndDepartmentResponse(List<String> colleges, List<String> departments) {
    public static CollegeAndDepartmentResponse of(List<String> colleges, List<String> departments) {
        return CollegeAndDepartmentResponse.builder()
                .colleges(colleges)
                .departments(departments)
                .build();
    }

}
