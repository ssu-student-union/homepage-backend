package ussum.homepage.application.user.service.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Builder
@RequiredArgsConstructor
@Getter
public class CollegeAndDepartmentResponse {
    private final List<String> colleges;
    private final List<String> departments;

    public static CollegeAndDepartmentResponse of(List<String> colleges, List<String> departments) {
        return CollegeAndDepartmentResponse.builder()
                .colleges(colleges)
                .departments(departments)
                .build();
    }

}
