package ussum.homepage.infra.csvBatch.csv;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;
import ussum.homepage.domain.csv_user.StudentCsv;
import ussum.homepage.domain.csv_user.StudentCsvRepository;
import ussum.homepage.infra.jpa.csv_user.entity.StudentCsvEntity;

@Configuration
@RequiredArgsConstructor
@Transactional
public class CSVWriter implements ItemWriter<StudentCsv> {

    private final StudentCsvRepository csvRepository;

    @Override
    @Transactional
    public void write(Chunk<? extends StudentCsv> chunk) throws Exception {
        Chunk<StudentCsvEntity> students = new Chunk<>();

        chunk.forEach(studentCsvDto -> {
            StudentCsvEntity data = ussum.homepage.infra.jpa.csv_user.entity.StudentCsvEntity.of(studentCsvDto.getStudentId(), studentCsvDto.getStudentName(), studentCsvDto.getGroupName(),
                    studentCsvDto.getProgram(), studentCsvDto.getMajor(), studentCsvDto.getSpecificMajor(), studentCsvDto.getStudentStatus());
            students.add(data);
        });
        csvRepository.saveAll(students);
    }
}
