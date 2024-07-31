package ussum.homepage.infra.jpa.csv_user;

import org.springframework.stereotype.Component;
import ussum.homepage.domain.csv_user.StudentCsv;
import ussum.homepage.infra.jpa.csv_user.entity.StudentCsvEntity;

@Component
public class StudentCsvMapper {

    public StudentCsv toDomain(StudentCsvEntity csvData) {
        return StudentCsv.of(
                csvData.getSTID(),
                csvData.getStudentId(),
                csvData.getStudentName(),
                csvData.getGroupName(),
                csvData.getMajor(),
                csvData.getStudentStatus(),
                csvData.getStudentGroup(),
                csvData.getStudentEmail()
        );
    }

    public StudentCsvEntity toEntity(StudentCsv studentCsv) {
        return ussum.homepage.infra.jpa.csv_user.entity.StudentCsvEntity.of(
                studentCsv.getSTID(),
                studentCsv.getStudentId(),
                studentCsv.getStudentName(),
                studentCsv.getGroupName(),
                studentCsv.getMajor(),
                studentCsv.getStudentStatus(),
                studentCsv.getStudentGroup(),
                studentCsv.getStudentEmail()
        );
    }
}
