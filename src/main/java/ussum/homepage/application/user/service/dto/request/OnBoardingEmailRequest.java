package ussum.homepage.application.user.service.dto.request;


public record OnBoardingEmailRequest(
        String name,
        Long studentId,
        String email,
        String content
) {
    public String toEmailSubject() {
        return "[총학생회 홈페이지 학생인증 문의] " + name;
    }

    public String toEmailContent() {
        return "학생명 : " + name + "\n" +
                "학번 : " + studentId + "\n" +
                "이메일 : " + email + "\n" +
                "문의 내용 : " + content;
    }
}
