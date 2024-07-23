package ussum.homepage.infra.csvBatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import ussum.homepage.infra.csvBatch.csv.CSVReader;
import ussum.homepage.infra.csvBatch.csv.CSVWriter;
import ussum.homepage.infra.csvBatch.csv.entity.StudentCsvData;
import ussum.homepage.infra.csvBatch.csv.entity.StudentCsvDto;

@RequiredArgsConstructor
@Configuration
public class FileItemReaderJobConfig {
    private final CSVReader csvReader;
    private final CSVWriter csvWriter;

    @Bean
    public Job studentDataLoadJob(JobRepository jobRepository, Step studentDataLoadStep) {
        return new JobBuilder("studetnDataLoadJob", jobRepository)
                .flow(studentDataLoadStep)
                .end()
                .build();
    }

    @Bean
    public Step studentDataLoadStep(JobRepository jobRepository, PlatformTransactionManager transactionManager
    ) {
        return new StepBuilder("studentDataLoadStep", jobRepository)
                .<StudentCsvDto, StudentCsvData>chunk(1000, transactionManager)
                .reader(csvReader.csvFileItemReader())
                .writer(csvWriter)
                .build();
    }
}

