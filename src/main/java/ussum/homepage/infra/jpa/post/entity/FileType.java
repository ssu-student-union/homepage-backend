package ussum.homepage.infra.jpa.post.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ussum.homepage.global.error.exception.InvalidValueException;

import java.util.Arrays;

import static ussum.homepage.global.error.status.ErrorStatus.*;

@RequiredArgsConstructor
@Getter
public enum FileType {
    RESULT_REPORT("결과보고서"),
    MINUTES("회의록"),
    STUDENT_COUNCIL_RULES("총학생회칙"),
    OPERATION_RULES("운영세칙"),
    DATA("자료"),
    RULE_AMENDMENT("회칙개정"),
    TIME_TABLE("시간표"),
    TERM_REPORT("지침서"),
    STUDENT_REPRESENTATIVE("신구대조표"),
    POLICY("정책자료"),
    COUNCIL_REPORT("결과보고서"),
    BUSINESS_REPORT("활동보고"),
    EXECUTIVE_COUNCIL("집행위원회"),
    DEPARTMENT_BUSINESS("예결산안");

    private final String stringFileType;

    public static FileType getEnumFileTypeFromStringFileType(String stringFileType) {
        return Arrays.stream(values())
                .filter(fileType -> fileType.stringFileType.equals(stringFileType))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_BOARDCODE));
    }

    public static FileType getEnumFileTypeFromString(String stringFileType) {
        return Arrays.stream(values())
                .filter(fileType -> fileType.name().equalsIgnoreCase(stringFileType) ||
                        fileType.toString().equalsIgnoreCase(stringFileType))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_FILETYPE));
    }
}