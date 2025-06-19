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
    private boolean paidUnionFee;

    public static StudentCsvEntity of(Long studentId, String studentName, String groupName,
                                      String program, String major, String specificMajor, String studentStatus, boolean paidUnionFee) {
        return StudentCsvEntity.builder()
                .studentId(studentId)
                .studentName(studentName)
                .groupName(groupName)
                .program(program)
                .major(major)
                .specificMajor(specificMajor)
                .studentStatus(studentStatus)
                .paidUnionFee(paidUnionFee)
                .build();
    }
}
