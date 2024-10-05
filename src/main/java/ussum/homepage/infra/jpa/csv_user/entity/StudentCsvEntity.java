package ussum.homepage.infra.jpa.csv_user.entity;


import jakarta.persistence.*;
import lombok.*;

@Table(name = "csv_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class StudentCsvEntity {
    @Id
    private Long studentId;
    private String studentName;
    private String groupName;
    private String program;
    private String major;
    private String specificMajor;
    private String studentStatus;

    public static StudentCsvEntity of(Long studentId, String studentName, String groupName,
                                      String program, String major, String specificMajor, String studentStatus) {
        return StudentCsvEntity.builder()
                .studentId(studentId)
                .studentName(studentName)
                .groupName(groupName)
                .program(program)
                .major(major)
                .specificMajor(specificMajor)
                .studentStatus(studentStatus)
                .build();
    }
}
