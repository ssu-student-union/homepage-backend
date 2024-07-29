package ussum.homepage.infra.csvBatch.csv;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;
import ussum.homepage.infra.csvBatch.csv.entity.StudentCsvData;
import ussum.homepage.infra.csvBatch.csv.entity.StudentCsvDto;
import ussum.homepage.infra.csvBatch.csv.repository.StudentCsvRepository;

@Configuration
@RequiredArgsConstructor
@Transactional
public class CSVWriter implements ItemWriter<StudentCsvDto> {

    private final StudentCsvRepository csvRepository;

    @Override
    @Transactional
    public void write(Chunk<? extends StudentCsvDto> chunk) throws Exception {
        Chunk<StudentCsvData> students = new Chunk<>();

        chunk.forEach(studentCsvDto -> {
            StudentCsvData data = StudentCsvData.of(studentCsvDto.getSTID(), studentCsvDto.getStudentId(), studentCsvDto.getStudentName(), studentCsvDto.getGroupName(),
                    studentCsvDto.getMajor(), studentCsvDto.getStudentStatus(), studentCsvDto.getStudentGroup(), studentCsvDto.getStudentEmail());
            students.add(data);
        });
        csvRepository.saveAll(students);
    }
}
