package ussum.homepage.infra.jpa.post.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ussum.homepage.global.error.exception.InvalidValueException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ussum.homepage.global.error.status.ErrorStatus.INVALID_BOARDCODE;
import static ussum.homepage.global.error.status.ErrorStatus.INVALID_FILE_CATEGORY;

@RequiredArgsConstructor
@Getter
public enum FileCategory {

    //중앙운영위원회
    학생총회_결과보고("총학생회", "학생총회","결과보고"),
    전체학생대표자회의_결과보고("총학생회", "전체학생대표자회의", "결과보고"),
    확대운영위원회_결과보고("총학생회", "확대운영위원회", "결과보고"),
    중앙운영위원회_회의록("총학생회", "중앙운영위원회", "회의록"),
    총학생회칙("총학생회", "중앙운영위원회", "총학생회칙"),
    특별기구_운영세칙("총학생회", "특별기구", "특별기구 운영세칙"),

    //총학생회
    결산안("총학생회", "중앙집행위원회", "결산안"),
    활동보고("총학생회", "중앙집행위원회", "활동보고"),

    //학생복지위원회
    학생복지위원회("총학생회", "특별기구", "학생복지위원회"),

    //인권위원회
    인권위원회("총학생회", "특별기구", "인권위원회"),

    //교지편집위원회
    교지편집위원회("총학생회", "특별기구", "교지편집위원회"),

    //IT지원위원회
    IT지원위원회("총학생회", "특별기구", "IT지원위원회"),

    //선거위원회
    선거시행세칙("선거관리위원회","","선거시행세칙"),
    선거세부지침서("선거관리위원회","","선거세부지침서"),
    특별선거시행세칙("선거관리위원회","","특별선거시행세칙"),

    //감사위원회
    회계교육자료("감사기구", "중앙감사위원회", "회계교육자료"),
    감사시행세칙("감사기구", "접속계정", "감사시행세칙"),
    회계지침서("감사기구", "접속계정", "회계지침서"),
    감사결과보고서("감사기구", "접속계정", "감사결과보고서"),
    회의록("감사기구", "접속계정", "회의록"),


    //단과대
    회칙_세칙("단과대", "접속계정", "회칙 세칙"),
    단과대회의록("단과대", "접속계정", "회의록"),
    예결산안("단과대", "접속계정", "예결산안"),
    단과대활동보고("단과대", "접속계정", "활동보고"),
    대표자회의결과보고("단과대", "접속계정", "대표자회의결과보고"),

    자료집아님("","","");
    private final String majorCategory;
    private final String middleCategory;
    private final String subCategory;

    public static FileCategory getEnumFileCategoryFromStringCategories(String majorCategory, String middleCategory, String subCategory) {
        return Arrays.stream(values())
                .filter(fileCategory ->
                        fileCategory.majorCategory.equals(majorCategory) &&
                                fileCategory.middleCategory.equals(middleCategory) &&
                                fileCategory.subCategory.equals(subCategory)
                )
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_FILE_CATEGORY));
    }

    public static FileCategory getEnumFileCategoryFromString(String stringFileCategory) {
        return Arrays.stream(values())
                .filter(fileCategory -> fileCategory.name().equalsIgnoreCase(stringFileCategory) ||
                        fileCategory.toString().equalsIgnoreCase(stringFileCategory))
                .findFirst()
                .orElseThrow(() -> new InvalidValueException(INVALID_FILE_CATEGORY));
    }
    @Override
    public String toString() {
        return this.name().replace("_", " ");
    }
    public static List<FileCategory> getFileCategoriesByCategories(String majorCategory, String middleCategory, String subCategory) {
        return Arrays.stream(values())
                .filter(fileCategory ->
                        matchCategory(fileCategory.majorCategory, majorCategory) &&
                                matchCategory(fileCategory.middleCategory, middleCategory) &&
                                matchCategory(fileCategory.subCategory, subCategory)
                )
                .collect(Collectors.toList());
    }

    private static boolean matchCategory(String categoryValue, String filterValue) {
        return filterValue == null || categoryValue.equals(filterValue);
    }

    public static FileCategory fromString(String value) {
        try {
            return FileCategory.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidValueException(INVALID_FILE_CATEGORY);
        }
    }
}
