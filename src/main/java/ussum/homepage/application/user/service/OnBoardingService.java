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
import ussum.homepage.domain.member.Member;
import ussum.homepage.domain.member.service.MemberAppender;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.exception.OnBoardingMessagingException;
import ussum.homepage.domain.user.service.UserModifier;
import ussum.homepage.domain.user.service.UserReader;
import ussum.homepage.global.error.exception.GeneralException;

import java.util.Optional;

import static ussum.homepage.global.error.status.ErrorStatus.ONBOARDING_FAIL_MAIL_ERROR;
import static ussum.homepage.global.error.status.ErrorStatus.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OnBoardingService {
    private final UserReader userReader;
    private final StudentCsvReader studentCsvReader;
    private final JavaMailSender javaMailSender;
    private final MemberAppender memberAppender;
    private final UserModifier userModifier;

    @Value("${spring.mail.username}")
    private String SENDER_EMAIL_ADDRESS;

    @Transactional
    public void saveUserOnBoarding(Long userId, OnBoardingRequest request){
//        User user = userReader.getUserWithId(userId);
        String studentId = request.getStudentId();
        Optional<StudentCsv> studentCsv =  studentCsvReader.getStudentWithStudentId(Long.valueOf(studentId), request);
        Boolean isVerified = studentCsv.isPresent();

        userModifier.updateOnBoardingUser(userId, request);
        Member member = Member.createMember(false, request.getMemberCode(), request.getMajorCode(), userId, isVerified);
        memberAppender.saveMember(member);
    }

    public void sendEmail(OnBoardingEmailRequest onBoardingEmailRequest) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setTo(SENDER_EMAIL_ADDRESS);
            mimeMessageHelper.setFrom(SENDER_EMAIL_ADDRESS);
            mimeMessageHelper.setReplyTo(onBoardingEmailRequest.email());
//            mimeMessageHelper.setFrom(onBoardingEmailRequest.email());
            mimeMessageHelper.setSubject(onBoardingEmailRequest.toEmailSubject());
            mimeMessageHelper.setText(onBoardingEmailRequest.toEmailContent());

            javaMailSender.send(mimeMessage);
        } catch (MessagingException | OnBoardingMessagingException onBoardingMessagingException) {
            throw new OnBoardingMessagingException(ONBOARDING_FAIL_MAIL_ERROR);
        }
    }
}
