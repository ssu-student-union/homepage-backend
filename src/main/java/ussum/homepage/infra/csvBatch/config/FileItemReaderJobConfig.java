package ussum.homepage.infra.csvBatch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import ussum.homepage.infra.csvBatch.csv.CSVReader;
import ussum.homepage.infra.csvBatch.csv.CSVWriter;
import ussum.homepage.domain.csv_user.StudentCsv;

@RequiredArgsConstructor
@Configuration
//@ConditionalOnProperty(name = "spring.batch.job.name", havingValue = "studentDataLoadJob")
public class FileItemReaderJobConfig {
    private final CSVReader csvReader;
    private final CSVWriter csvWriter;

    private static final String JOB_NAME = "studentDataLoadJob";

    @Bean(JOB_NAME)
    public Job studentDataLoadJob(JobRepository jobRepository, Step studentDataLoadStep) {
        return new JobBuilder(JOB_NAME, jobRepository)
                .flow(studentDataLoadStep)
                .end()
                .build();
    }

    @Bean
    @JobScope
    public Step studentDataLoadStep(JobRepository jobRepository,
                                    PlatformTransactionManager transactionManager) {
        return new StepBuilder("studentDataLoadStep", jobRepository)
                .<StudentCsv, StudentCsv>chunk(1000, transactionManager)
                .reader(csvReader.csvFileItemReader())
                .writer(csvWriter)
                .build();
    }
}

