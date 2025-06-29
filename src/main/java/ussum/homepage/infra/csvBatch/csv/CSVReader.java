package ussum.homepage.infra.csvBatch.csv;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.FileSystemResource;
import ussum.homepage.domain.csv_user.StudentCsv;

@Configuration
@RequiredArgsConstructor
public class CSVReader {
    @Bean
    public FlatFileItemReader<StudentCsv> csvFileItemReader() { // 엔티티를 반환하는게 맞나?
        /* file read */
        FlatFileItemReader<StudentCsv> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource(System.getProperty("user.dir") + "/csv/학생목록.csv"));
        flatFileItemReader.setLinesToSkip(1); // header line skip
        flatFileItemReader.setEncoding("UTF-8"); // encoding
//        flatFileItemReader.setStrict(false);
        // flatFileItemReader.setRecordSeparatorPolicy(new DefaultRecordSeparatorPolicy());

        /* read하는 데이터를 내부적으로 LineMapper을 통해 Mapping */
        DefaultLineMapper<StudentCsv> defaultLineMapper = new DefaultLineMapper<>();

        /* delimitedLineTokenizer : setNames를 통해 각각의 데이터의 이름 설정 */
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer(",");
        delimitedLineTokenizer.setNames(
                "studentId", // 학번
                "studentName", // 성명
                "groupName", // 대학
                "program", // 프로그램?
                "major", // 학과(부)
                "specificMajor", // 세부전공(건축만 존재)
                "studentStatus", // 재학여부(재학, 휴학)
                "paidUnionFee " // 학생회비 납부 여부
        );
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

        // "Y/N" → boolean 커스텀 컨버터
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(String.class, Boolean.class, s -> {
            if (s == null) return null;
            return switch (s.trim().toUpperCase()) {
                case "Y", "YES", "TRUE", "1" -> true;
                case "N", "NO",  "FALSE", "0" -> false;
                default -> throw new RuntimeException("CSV를 DB에 넣던 중에 에러가 발생했습니다.");
            };
        });

        /* beanWrapperFieldSetMapper : Tokenizer에서 가지고온 데이터들을 VO로 바인드하는 역할 */
        BeanWrapperFieldSetMapper<StudentCsv> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(StudentCsv.class);
        beanWrapperFieldSetMapper.setConversionService(conversionService);

        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        /* lineMapper 지정 */
        flatFileItemReader.setLineMapper(defaultLineMapper);

        return flatFileItemReader;
    }
}
