package ussum.homepage.application.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ussum.homepage.infra.csvBatch.JobLauncherRunner;
import ussum.homepage.infra.utils.S3utils;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {
    private final S3utils s3utils;
    private final JobLauncherRunner runner;

    public void uploadCsvToS3(MultipartFile file) {
        s3utils.uploadFile(file);
    }

    public void uploadCsvFromS3ToProject(String fileName) {
        s3utils.getFileToProject(fileName);
        runner.runJob(); // batch job 실행

        // runJob이후 resources/csv 경로에 학생목록.csv파일이 생성됨, 코드로 삭제해야하나?
    }
}
