package ussum.homepage.application.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ussum.homepage.application.admin.service.dto.request.CouncilSignInRequest;
import ussum.homepage.domain.group.Group;
import ussum.homepage.domain.group.service.GroupReader;
import ussum.homepage.domain.member.Member;
import ussum.homepage.domain.member.service.MemberAppender;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.service.UserAppender;
import ussum.homepage.infra.csvBatch.JobLauncherRunner;
import ussum.homepage.infra.utils.S3utils;

import java.nio.file.attribute.GroupPrincipal;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {
    private final S3utils s3utils;
    private final JobLauncherRunner runner;
    private final PasswordEncoder passwordEncoder;
    private final UserAppender userAppender;
    private final MemberAppender memberAppender;
    private final GroupReader groupReader;

    public void uploadCsvToS3(MultipartFile file) {
        s3utils.uploadFile(file);
    }

    public void uploadCsvFromS3ToProject(String fileName) {
        s3utils.getFileToProject(fileName);
        runner.runJob(); // batch job 실행

        // runJob이후 resources/csv 경로에 학생목록.csv파일이 생성됨, 코드로 삭제해야하나?
    }

    public void councilSignIn(CouncilSignInRequest request){
        String password = passwordEncoder.encode(request.password());
        User user = User.createCouncilUser(request.accountId(), password);
        User savedUser = userAppender.createUser(user);
        Group group = groupReader.getGroupByGroupId(request.groupId());
        Member member = Member.of(null, true, request.memberCode(), request.majorCode(), savedUser.getId(), group.getId());
        memberAppender.saveMember(member);
    }
}
