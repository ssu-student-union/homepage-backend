package ussum.homepage.application.admin.service;

import java.io.File;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ussum.homepage.application.admin.service.dto.request.CouncilSignInRequest;
import ussum.homepage.domain.group.Group;
import ussum.homepage.domain.group.service.GroupReader;
import ussum.homepage.domain.member.service.MemberAppender;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.service.UserAppender;
import ussum.homepage.global.error.exception.GeneralException;
import ussum.homepage.global.error.status.ErrorStatus;
import ussum.homepage.infra.csvBatch.JobLauncherRunner;
import ussum.homepage.infra.utils.S3utils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
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

    public void updateUserInfoByCsv(String FileName) {
        String filePath = System.getProperty("user.dir") + "/csv/" + FileName;
        File file = new File(filePath);

        if (!file.exists() || !file.isFile()) {
            throw new GeneralException(ErrorStatus.FILE_NOT_FOUND);
        }
        runner.runJob(); // batch job 실행
    }

    public void councilSignIn(CouncilSignInRequest request){
        String password = passwordEncoder.encode(request.password());
        User user = User.createCouncilUser(request, password);
        User savedUser = userAppender.createUser(user);
        List<Group> groupList = groupReader.getGroupsByGroupIdList(request.groupIdList());
        memberAppender.saveMemberList(groupList, request, savedUser);
    }
}
