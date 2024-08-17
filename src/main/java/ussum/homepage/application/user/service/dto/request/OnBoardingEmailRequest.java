package ussum.homepage.application.user.service.dto.request;


public record OnBoardingEmailRequest(
        String name,
        Long studentId,
        String email,
        String content
) {
    public String toString(OnBoardingEmailRequest onBoardingEmailRequest) {
        return onBoardingEmailRequest.studentId + "학번과 "
                + onBoardingEmailRequest.email + " 라는 이메일을 가진 "
                + onBoardingEmailRequest.name + "님이 "
                + "메일을 보냈습니다.";
    }
}
