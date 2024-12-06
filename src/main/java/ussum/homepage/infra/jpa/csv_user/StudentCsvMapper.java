package ussum.homepage.infra.jpa.csv_user;

import org.springframework.stereotype.Component;
import ussum.homepage.domain.csv_user.StudentCsv;
import ussum.homepage.infra.jpa.csv_user.entity.StudentCsvEntity;

@Component
public class StudentCsvMapper {

    public StudentCsv toDomain(StudentCsvEntity csvData) {
        return StudentCsv.of(
                csvData.getStudentId(),
                csvData.getStudentName(),
                csvData.getGroupName(),
                csvData.getProgram(),
                csvData.getMajor(),
                csvData.getSpecificMajor(),
                csvData.getStudentStatus()
        );
    }

    public StudentCsvEntity toEntity(StudentCsv studentCsv) {
        return StudentCsvEntity.of(
                studentCsv.getStudentId(),
                studentCsv.getStudentName(),
                studentCsv.getProgram(),
                studentCsv.getGroupName(),
                studentCsv.getMajor(),
                studentCsv.getSpecificMajor(),
                studentCsv.getStudentStatus()
        );
    }
}
