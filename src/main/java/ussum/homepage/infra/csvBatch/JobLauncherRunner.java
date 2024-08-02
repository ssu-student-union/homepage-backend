package ussum.homepage.infra.csvBatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobLauncherRunner {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job studentDataLoadJob;

    public void runJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("JobID", String.valueOf(System.currentTimeMillis()))
                    .toJobParameters();
            jobLauncher.run(studentDataLoadJob, jobParameters);
        } catch (Exception e) {
            throw new RuntimeException("CSV를 DB에 넣던 중에 에러가 발생했습니다.", e);
        }
    }
}