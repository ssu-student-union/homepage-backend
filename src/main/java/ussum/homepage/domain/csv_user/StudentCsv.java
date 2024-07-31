package ussum.homepage.domain.csv_user;

import lombok.*;

/*
원래는 record로 dto를 관리하지만,
기본 생성자에서 문제가 발생하여 class 사용
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StudentCsv {
    private Long STID;
    private Long studentId;
    private String studentName;
    private String groupName;
    private String major;
    private String studentStatus;
    private String studentGroup; // 학생그룹의 의미
    // 전화번호?
    private String studentEmail;

    public static StudentCsv of(Long STID, Long studentId, String studentName, String groupName,
                                String major, String studentStatus, String studentGroup, String studentEmail) {
        return new StudentCsv(STID, studentId, studentName, groupName,
                major, studentStatus, studentGroup, studentEmail);
    }
}
