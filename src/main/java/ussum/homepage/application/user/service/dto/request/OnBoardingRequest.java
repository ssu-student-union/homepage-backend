package ussum.homepage.application.user.service.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OnBoardingRequest {
    private String name;
    private String studentId;
    private String groupName;
    private String major;
}
