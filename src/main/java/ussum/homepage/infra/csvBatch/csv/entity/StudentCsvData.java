package ussum.homepage.infra.csvBatch.csv.entity;


import jakarta.persistence.*;
import lombok.*;

@Table(name = "csv_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class StudentCsvData {
    // STID / 학번 / 성명 / 대학 / 학과(부) / 학적상 / 학생그룹 / (전화번호) / (전자메일)
    @Id
    private Long STID;
    private Long studentId;
    private String studentName;
    private String groupName;
    private String major;
    private String studentStatus;
    private String studentGroup; // 학생그룹의 의미
    // 전화번호?
    private String studentEmail;

    public static StudentCsvData of(Long STID, Long studentId, String studentName, String groupName,
                                   String major, String studentStatus, String studentGroup, String studentEmail) {
        return StudentCsvData.builder()
                .STID(STID)
                .studentId(studentId)
                .studentName(studentName)
                .groupName(groupName)
                .major(major)
                .studentStatus(studentStatus)
                .studentGroup(studentGroup)
                .studentEmail(studentEmail)
                .build();
    }
}
