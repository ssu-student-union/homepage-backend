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
    private Long studentId;
    private String studentName;
    private String groupName;
    private String program;
    private String major;
    private String specificMajor;
    private String studentStatus;

    public static StudentCsv of(Long studentId, String studentName, String groupName,
                                String program, String major, String specificMajor, String studentStatus) {
        return new StudentCsv(studentId, studentName, groupName,
                program, major, specificMajor, studentStatus);
    }
}
