package ussum.homepage.infra.csvBatch.csv;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import ussum.homepage.domain.csv_user.StudentCsv;

@Configuration
@RequiredArgsConstructor
public class CSVReader {
    @Bean
    public FlatFileItemReader<StudentCsv> csvFileItemReader() { // 엔티티를 반환하는게 맞나?
        /* file read */
        FlatFileItemReader<StudentCsv> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new ClassPathResource("csv/학생목록.csv"));
        flatFileItemReader.setLinesToSkip(1); // header line skip
        flatFileItemReader.setEncoding("UTF-8"); // encoding
//        flatFileItemReader.setStrict(false);
        // flatFileItemReader.setRecordSeparatorPolicy(new DefaultRecordSeparatorPolicy());

        /* read하는 데이터를 내부적으로 LineMapper을 통해 Mapping */
        DefaultLineMapper<StudentCsv> defaultLineMapper = new DefaultLineMapper<>();

        /* delimitedLineTokenizer : setNames를 통해 각각의 데이터의 이름 설정 */
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer(",");
        delimitedLineTokenizer.setNames(
                "STID",
                "studentId",
                "studentName",
                "groupName",
                "major",
                "studentStatus",
                "studentGroup",
                "studentEmail"
        );
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

        /* beanWrapperFieldSetMapper : Tokenizer에서 가지고온 데이터들을 VO로 바인드하는 역할 */
        BeanWrapperFieldSetMapper<StudentCsv> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(StudentCsv.class);

        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        /* lineMapper 지정 */
        flatFileItemReader.setLineMapper(defaultLineMapper);

        return flatFileItemReader;
    }
}