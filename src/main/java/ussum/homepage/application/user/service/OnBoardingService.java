package ussum.homepage.application.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ussum.homepage.application.user.service.dto.request.OnBoardingEmailRequest;
import ussum.homepage.application.user.service.dto.request.OnBoardingRequest;
import ussum.homepage.domain.csv_user.StudentCsv;
import ussum.homepage.domain.csv_user.service.StudentCsvReader;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.exception.OnBoardingMessagingException;
import ussum.homepage.domain.user.service.UserModifier;
import ussum.homepage.domain.user.service.UserReader;
import ussum.homepage.global.error.exception.GeneralException;

import static ussum.homepage.global.error.status.ErrorStatus.ONBOARDING_FAIL_MAIL_ERROR;
import static ussum.homepage.global.error.status.ErrorStatus.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OnBoardingService {
    private final UserModifier userModifier;
    private final UserReader userReader;
    private final StudentCsvReader studentCsvReader;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String SENDER_EMAIL_ADDRESS;

    public void saveUserOnBoarding(Long userId, OnBoardingRequest request){
        User user = userReader.getUserWithId(userId);
        String studentId = request.getStudentId();
        StudentCsv studentCsv = studentCsvReader.getStudentWithStudentId(Long.valueOf(studentId),request)
                .orElseThrow(() -> new GeneralException(USER_NOT_FOUND));

        /*
        TO DO // MemberEntity, GroupEntity 연결
         */
        user.updateOnBoardingUser(request); // 이 메소드 수정 필요
        userModifier.save(user);
    }

    public void sendEmail(OnBoardingEmailRequest onBoardingEmailRequest) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setTo(SENDER_EMAIL_ADDRESS);
            mimeMessageHelper.setFrom(onBoardingEmailRequest.email());
            mimeMessageHelper.setSubject(onBoardingEmailRequest.toString(onBoardingEmailRequest));
            mimeMessageHelper.setText(onBoardingEmailRequest.content());

            javaMailSender.send(mimeMessage);
        } catch (MessagingException | OnBoardingMessagingException onBoardingMessagingException) {
            throw new OnBoardingMessagingException(ONBOARDING_FAIL_MAIL_ERROR);
        }
    }
}