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

    /*
    *   대분류 : 총학생회
    */

    //결과보고
    학생총회_결과보고("총학생회", "학생총회","결과보고"),
    전체학생대표자회의_결과보고("총학생회", "전체학생대표자회의", "결과보고"),
    확대운영위원회_결과보고("총학생회", "확대운영위원회", "결과보고"),

    //회의록
    중앙운영위원회_회의록("총학생회", "중앙운영위원회", "회의록"),

    //총학생회칙
    총학생회칙("총학생회", "중앙운영위원회", "총학생회칙"),

    //특별기구 운영세칙
    특별기구_운영세칙("총학생회", "특별기구", "특별기구 운영세칙"),

    //결산안
    결산안("총학생회", "중앙집행위원회", "결산안"),

    //활동보고
    활동보고("총학생회", "중앙집행위원회", "활동보고"),

    //학생복지위원회
    학생복지위원회("총학생회", "특별기구", "학생복지위원회"),

    //인권위원회
    인권위원회("총학생회", "특별기구", "인권위원회"),

    //교지편집위원회
    교지편집위원회("총학생회", "특별기구", "교지편집위원회"),

    //IT지원위원회
    IT지원위원회("총학생회", "특별기구", "IT지원위원회"),

    /*
     *   대분류 : 선거관리위원회
     */

    //선거위원회 (선거시행세칙)
    중앙_선거시행세칙("선거관리위원회","중앙선거관리위원회","선거시행세칙"),
    경영대학_선거시행세칙("선거관리위원회","경영대학","선거시행세칙"),
    경제통상대학_선거시행세칙("선거관리위원회","경제통상대학","선거시행세칙"),
    법과대학_선거시행세칙("선거관리위원회","법과대학","선거시행세칙"),
    사회과학대학_선거시행세칙("선거관리위원회","사회과학대학","선거시행세칙"),
    인문대학_선거시행세칙("선거관리위원회","인문대학","선거시행세칙"),
    자연과학대학_선거시행세칙("선거관리위원회","자연과학대학","선거시행세칙"),
    IT대학_선거시행세칙("선거관리위원회","IT대학","선거시행세칙"),
    동아리연합회_선거시행세칙("선거관리위원회","동아리연합회","선거시행세칙"),

    //선거위원회 (선거세부지침서)
    중앙_선거세부지침서("선거관리위원회","중앙선거관리위원회","선거세부지침서"),
    경영대학_선거세부지침서("선거관리위원회","경영대학","선거세부지침서"),
    경제통상대학_선거세부지침서("선거관리위원회","경제통상대학","선거세부지침서"),
    법과대학_선거세부지침서("선거관리위원회","법과대학","선거세부지침서"),
    사회과학대학_선거세부지침서("선거관리위원회","사회과학대학","선거세부지침서"),
    인문대학_선거세부지침서("선거관리위원회","인문대학","선거세부지침서"),
    자연과학대학_선거세부지침서("선거관리위원회","자연과학대학","선거세부지침서"),
    IT대학_선거세부지침서("선거관리위원회","IT대학","선거세부지침서"),
    동아리연합회_선거세부지침서("선거관리위원회","동아리연합회","선거세부지침서"),

    //선거위원회 (특별선거시행세칙)
    중앙특별선거시행세칙("선거관리위원회","중앙선거관리위원회","특별선거시행세칙"),
    경영대학_특별선거시행세칙("선거관리위원회","경영대학","특별선거시행세칙"),
    경제통상대학_특별선거시행세칙("선거관리위원회","경제통상대학","특별선거시행세칙"),
    법과대학_특별선거시행세칙("선거관리위원회","법과대학","특별선거시행세칙"),
    사회과학대학_특별선거시행세칙("선거관리위원회","사회과학대학","특별선거시행세칙"),
    인문대학_특별선거시행세칙("선거관리위원회","인문대학","특별선거시행세칙"),
    자연과학대학_특별선거시행세칙("선거관리위원회","자연과학대학","특별선거시행세칙"),
    IT대학_특별선거시행세칙("선거관리위원회","IT대학","특별선거시행세칙"),
    동아리연합회_특별선거시행세칙("선거관리위원회","동아리연합회","특별선거시행세칙"),


    //감사위원회
    회계교육자료("감사기구", "중앙감사위원회", "회계교육자료"),
    중앙_감사시행세칙("감사기구", "중앙감사위원회", "감사시행세칙"),
    경영대학_감사시행세칙("감사기구", "경영대학 감사위원회", "감사시행세칙"),
    경제통상대학_감사시행세칙("감사기구", "경제통상대학 감사위원회", "감사시행세칙"),
    공과대학_감사시행세칙("감사기구", "공과대학 감사위원회", "감사시행세칙"),
    법과대학_감사시행세칙("감사기구", "법과대학 감사위원회", "감사시행세칙"),
    사회과학대학_감사시행세칙("감사기구", "사회과학대학 감사위원회", "감사시행세칙"),
    인문대학_감사시행세칙("감사기구", "인문대학 감사위원회", "감사시행세칙"),
    자연과학대학_감사시행세칙("감사기구", "자연과학대학 감사위원회", "감사시행세칙"),
    IT대학_감사시행세칙("감사기구", "IT대학 감사위원회", "감사시행세칙"),
    //회계지침서
    중앙_회계지침서("감사기구", "중앙감사위원회", "회계지침서"),
    경영대학_회계지침서("감사기구", "경영대학 감사위원회" , "회계지침서"),
    경제통상대학_회계지침서("감사기구", "경제통상대학 감사위원회", "회계지침서"),
    공과대학_회계지침서("감사기구", "공과대학 감사위원회", "회계지침서"),
    법과대학_회계지침서("감사기구", "법과대학 감사위원회", "회계지침서"),
    사회과학대학_회계지침서("감사기구", "사회과학대학 감사위원회", "회계지침서"),
    인문대학_회계지침서("감사기구", "인문대학 감사위원회", "회계지침서"),
    자연과학대학_회계지침서("감사기구", "자연과학대학 감사위원회", "회계지침서"),
    IT대학_회계지침서("감사기구", "IT대학 감사위원회", "회계지침서"),
    //감사결과보고서
    중앙_감사결과보고서("감사기구", "중앙감사위원회", "감사결과보고서"),
    경영대학_감사결과보고서("감사기구", "경영대학 감사위원회", "감사결과보고서"),
    경제통상대학_감사결과보고서("감사기구", "경제통상대학 감사위원회", "감사결과보고서"),
    공과대학_감사결과보고서("감사기구", "공과대학 감사위원회", "감사결과보고서"),
    법과대학_감사결과보고서("감사기구", "법과대학 감사위원회", "감사결과보고서"),
    사회과학대학_감사결과보고서("감사기구", "사회과학대학 감사위원회", "감사결과보고서"),
    인문대학_감사결과보고서("감사기구", "인문대학 감사위원회", "감사결과보고서"),
    자연과학대학_감사결과보고서("감사기구", "자연과학대학 감사위원회", "감사결과보고서"),
    IT대학_감사결과보고서("감사기구", "IT대학 감사위원회", "감사결과보고서"),
    //회의록
    중앙_감사회의록("감사기구", "중앙감사위원회", "회의록"),
    경영대학_감사회의록("감사기구", "경영대학 감사위원회", "회의록"),
    경제통상대학_감사회의록("감사기구", "경제통상대학 감사위원회", "회의록"),
    공과대학_감사회의록("감사기구", "공과대학 감사위원회", "회의록"),
    법과대학_감사회의록("감사기구", "법과대학 감사위원회", "회의록"),
    사회과학대학_감사회의록("감사기구", "사회과학대학 감사위원회", "회의록"),
    인문대학_감사회의록("감사기구", "인문대학 감사위원회", "회의록"),
    자연과학대학_감사회의록("감사기구", "자연과학대학 감사위원회", "회의록"),
    IT대학_감사회의록("감사기구", "IT대학 감사위원회", "회의록"),

    /*
     *   대분류 : 경영대학
     */

    // 회식세칙
    경영대학_회칙_세칙("경영대학", "경영대학", "회칙 세칙"),
    경영학부_회칙_세칙("경영대학", "경영학부", "회칙 세칙"),
    벤처중소기업학과_회칙_세칙("경영대학", "벤처중소기업학과", "회칙 세칙"),
    회계학과_회칙_세칙("경영대학", "회계학과", "회칙 세칙"),
    금융학부_회칙_세칙("경영대학", "금융학부", "회칙 세칙"),

    // 회의록
    경영대학_회의록("경영대학", "경영대학", "회의록"),
    경영학부_회의록("경영대학", "경영학부", "회의록"),
    벤처중소기업학과_회의록("경영대학", "벤처중소기업학과", "회의록"),
    회계학과_회의록("경영대학", "회계학과", "회의록"),
    금융학부_회의록("경영대학", "금융학부", "회의록"),

    // 예결산안
    경영대학_예결산안("경영대학", "경영대학", "예결산안"),
    경영학부_예결산안("경영대학", "경영학부", "예결산안"),
    벤처중소기업학과_예결산안("경영대학", "벤처중소기업학과", "예결산안"),
    회계학과_예결산안("경영대학", "회계학과", "예결산안"),
    금융학부_예결산안("경영대학", "금융학부", "예결산안"),

    // 활동보고
    경영대학_활동보고("경영대학", "경영대학", "활동보고"),
    경영학부_활동보고("경영대학", "경영학부", "활동보고"),
    벤처중소기업학과_활동보고("경영대학", "벤처중소기업학과", "활동보고"),
    회계학과_활동보고("경영대학", "회계학과", "활동보고"),
    금융학부_활동보고("경영대학", "금융학부", "활동보고"),

    /*
     *   대분류 : 경제통상대학
     */
    // 경제통상대학 - 회칙 세칙
    경제통상대학_회칙_세칙("경제통상대학", "경제통상대학", "회칙 세칙"),
    경제학과_회칙_세칙("경제통상대학", "경제학과", "회칙 세칙"),
    글로벌통상학과_회칙_세칙("경제통상대학", "글로벌통상학과", "회칙 세칙"),
    금융경제학과_회칙_세칙("경제통상대학", "금융경제학과", "회칙 세칙"),
    국제무역학과_회칙_세칙("경제통상대학", "국제무역학과", "회칙 세칙"),

    // 경제통상대학 - 회의록
    경제통상대학_회의록("경제통상대학", "경제통상대학", "회의록"),
    경제학과_회의록("경제통상대학", "경제학과", "회의록"),
    글로벌통상학과_회의록("경제통상대학", "글로벌통상학과", "회의록"),
    금융경제학과_회의록("경제통상대학", "금융경제학과", "회의록"),
    국제무역학과_회의록("경제통상대학", "국제무역학과", "회의록"),

    // 경제통상대학 - 예결산안
    경제통상대학_예결산안("경제통상대학", "경제통상대학", "예결산안"),
    경제학과_예결산안("경제통상대학", "경제학과", "예결산안"),
    글로벌통상학과_예결산안("경제통상대학", "글로벌통상학과", "예결산안"),
    금융경제학과_예결산안("경제통상대학", "금융경제학과", "예결산안"),
    국제무역학과_예결산안("경제통상대학", "국제무역학과", "예결산안"),

    // 경제통상대학 - 활동보고
    경제통상대학_활동보고("경제통상대학", "경제통상대학", "활동보고"),
    경제학과_활동보고("경제통상대학", "경제학과", "활동보고"),
    글로벌통상학과_활동보고("경제통상대학", "글로벌통상학과", "활동보고"),
    금융경제학과_활동보고("경제통상대학", "금융경제학과", "활동보고"),
    국제무역학과_활동보고("경제통상대학", "국제무역학과", "활동보고"),

    /*
     *   대분류 : 공과대학
     */
    // 회칙 세칙
    공과대학_회칙_세칙("공과대학", "공과대학", "회칙 세칙"),
    화학공학과_회칙_세칙("공과대학", "화학공학과", "회칙 세칙"),
    신소재공학과_회칙_세칙("공과대학", "신소재공학과", "회칙 세칙"),
    전기공학부_회칙_세칙("공과대학", "전기공학부", "회칙 세칙"),
    기계공학부_회칙_세칙("공과대학", "기계공학부", "회칙 세칙"),
    산업정보시스템공학과_회칙_세칙("공과대학", "산업정보시스템공학과", "회칙 세칙"),
    건축학부_회칙_세칙("공과대학", "건축학부", "회칙 세칙"),

    // 회의록
    공과대학_회의록("공과대학", "공과대학", "회의록"),
    화학공학과_회의록("공과대학", "화학공학과", "회의록"),
    신소재공학과_회의록("공과대학", "신소재공학과", "회의록"),
    전기공학부_회의록("공과대학", "전기공학부", "회의록"),
    기계공학부_회의록("공과대학", "기계공학부", "회의록"),
    산업정보시스템공학과_회의록("공과대학", "산업정보시스템공학과", "회의록"),
    건축학부_회의록("공과대학", "건축학부", "회의록"),

    // 예결산안
    공과대학_예결산안("공과대학", "공과대학", "예결산안"),
    화학공학과_예결산안("공과대학", "화학공학과", "예결산안"),
    신소재공학과_예결산안("공과대학", "신소재공학과", "예결산안"),
    전기공학부_예결산안("공과대학", "전기공학부", "예결산안"),
    기계공학부_예결산안("공과대학", "기계공학부", "예결산안"),
    산업정보시스템공학과_예결산안("공과대학", "산업정보시스템공학과", "예결산안"),
    건축학부_예결산안("공과대학", "건축학부", "예결산안"),

    // 활동보고
    공과대학_활동보고("공과대학", "공과대학", "활동보고"),
    화학공학과_활동보고("공과대학", "화학공학과", "활동보고"),
    신소재공학과_활동보고("공과대학", "신소재공학과", "활동보고"),
    전기공학부_활동보고("공과대학", "전기공학부", "활동보고"),
    기계공학부_활동보고("공과대학", "기계공학부", "활동보고"),
    산업정보시스템공학과_활동보고("공과대학", "산업정보시스템공학과", "활동보고"),
    건축학부_활동보고("공과대학", "건축학부", "활동보고"),

    /*
     *   대분류 : 법과대학
     */
    // 회칙 세칙
    법과대학_회칙_세칙("법과대학", "법과대학", "회칙 세칙"),
    법학과_회칙_세칙("법과대학", "법학과", "회칙 세칙"),
    국제법무학과_회칙_세칙("법과대학", "국제법무학과", "회칙 세칙"),

    // 회의록
    법과대학_회의록("법과대학", "법과대학", "회의록"),
    법학과_회의록("법과대학", "법학과", "회의록"),
    국제법무학과_회의록("법과대학", "국제법무학과", "회의록"),

    // 예결산안
    법과대학_예결산안("법과대학", "법과대학", "예결산안"),
    법학과_예결산안("법과대학", "법학과", "예결산안"),
    국제법무학과_예결산안("법과대학", "국제법무학과", "예결산안"),

    // 활동보고
    법과대학_활동보고("법과대학", "법과대학", "활동보고"),
    법학과_활동보고("법과대학", "법학과", "활동보고"),
    국제법무학과_활동보고("법과대학", "국제법무학과", "활동보고"),

    /*
     *   대분류 : 사회과학대학
     */
    // 회칙 세칙
    사회과학대학_회칙_세칙("사회과학대학", "사회과학대학", "회칙 세칙"),
    사회복지학부_회칙_세칙("사회과학대학", "사회복지학부", "회칙 세칙"),
    행정학부_회칙_세칙("사회과학대학", "행정학부", "회칙 세칙"),
    정치외교학과_회칙_세칙("사회과학대학", "정치외교학과", "회칙 세칙"),
    정보사회학과_회칙_세칙("사회과학대학", "정보사회학과", "회칙 세칙"),
    언론홍보학과_회칙_세칙("사회과학대학", "언론홍보학과", "회칙 세칙"),
    평생교육학과_회칙_세칙("사회과학대학", "평생교육학과", "회칙 세칙"),

    // 회의록
    사회과학대학_회의록("사회과학대학", "사회과학대학", "회의록"),
    사회복지학부_회의록("사회과학대학", "사회복지학부", "회의록"),
    행정학부_회의록("사회과학대학", "행정학부", "회의록"),
    정치외교학과_회의록("사회과학대학", "정치외교학과", "회의록"),
    정보사회학과_회의록("사회과학대학", "정보사회학과", "회의록"),
    언론홍보학과_회의록("사회과학대학", "언론홍보학과", "회의록"),
    평생교육학과_회의록("사회과학대학", "평생교육학과", "회의록"),

    // 예결산안
    사회과학대학_예결산안("사회과학대학", "사회과학대학", "예결산안"),
    사회복지학부_예결산안("사회과학대학", "사회복지학부", "예결산안"),
    행정학부_예결산안("사회과학대학", "행정학부", "예결산안"),
    정치외교학과_예결산안("사회과학대학", "정치외교학과", "예결산안"),
    정보사회학과_예결산안("사회과학대학", "정보사회학과", "예결산안"),
    언론홍보학과_예결산안("사회과학대학", "언론홍보학과", "예결산안"),
    평생교육학과_예결산안("사회과학대학", "평생교육학과", "예결산안"),

    // 활동보고
    사회과학대학_활동보고("사회과학대학", "사회과학대학", "활동보고"),
    사회복지학부_활동보고("사회과학대학", "사회복지학부", "활동보고"),
    행정학부_활동보고("사회과학대학", "행정학부", "활동보고"),
    정치외교학과_활동보고("사회과학대학", "정치외교학과", "활동보고"),
    정보사회학과_활동보고("사회과학대학", "정보사회학과", "활동보고"),
    언론홍보학과_활동보고("사회과학대학", "언론홍보학과", "활동보고"),
    평생교육학과_활동보고("사회과학대학", "평생교육학과", "활동보고"),

    /*
     *   대분류 : 인문대학
     */
// 회칙 세칙
    인문대학_회칙_세칙("인문대학", "인문대학", "회칙 세칙"),
    기독교학과_회칙_세칙("인문대학", "기독교학과", "회칙 세칙"),
    국어국문학과_회칙_세칙("인문대학", "국어국문학과", "회칙 세칙"),
    영어영문학과_회칙_세칙("인문대학", "영어영문학과", "회칙 세칙"),
    독어독문학과_회칙_세칙("인문대학", "독어독문학과", "회칙 세칙"),
    불어불문학과_회칙_세칙("인문대학", "불어불문학과", "회칙 세칙"),
    중어중문학과_회칙_세칙("인문대학", "중어중문학과", "회칙 세칙"),
    일어일문학과_회칙_세칙("인문대학", "일어일문학과", "회칙 세칙"),
    철학과_회칙_세칙("인문대학", "철학과", "회칙 세칙"),
    사학과_회칙_세칙("인문대학", "사학과", "회칙 세칙"),
    문예창작전공_회칙_세칙("인문대학", "문예창작전공", "회칙 세칙"),
    영화예술전공_회칙_세칙("인문대학", "영화예술전공", "회칙 세칙"),
    스포츠학부_회칙_세칙("인문대학", "스포츠학부", "회칙 세칙"),

    // 회의록
    인문대학_회의록("인문대학", "인문대학", "회의록"),
    기독교학과_회의록("인문대학", "기독교학과", "회의록"),
    국어국문학과_회의록("인문대학", "국어국문학과", "회의록"),
    영어영문학과_회의록("인문대학", "영어영문학과", "회의록"),
    독어독문학과_회의록("인문대학", "독어독문학과", "회의록"),
    불어불문학과_회의록("인문대학", "불어불문학과", "회의록"),
    중어중문학과_회의록("인문대학", "중어중문학과", "회의록"),
    일어일문학과_회의록("인문대학", "일어일문학과", "회의록"),
    철학과_회의록("인문대학", "철학과", "회의록"),
    사학과_회의록("인문대학", "사학과", "회의록"),
    문예창작전공_회의록("인문대학", "문예창작전공", "회의록"),
    영화예술전공_회의록("인문대학", "영화예술전공", "회의록"),
    스포츠학부_회의록("인문대학", "스포츠학부", "회의록"),

    // 예결산안
    인문대학_예결산안("인문대학", "인문대학", "예결산안"),
    기독교학과_예결산안("인문대학", "기독교학과", "예결산안"),
    국어국문학과_예결산안("인문대학", "국어국문학과", "예결산안"),
    영어영문학과_예결산안("인문대학", "영어영문학과", "예결산안"),
    독어독문학과_예결산안("인문대학", "독어독문학과", "예결산안"),
    불어불문학과_예결산안("인문대학", "불어불문학과", "예결산안"),
    중어중문학과_예결산안("인문대학", "중어중문학과", "예결산안"),
    일어일문학과_예결산안("인문대학", "일어일문학과", "예결산안"),
    철학과_예결산안("인문대학", "철학과", "예결산안"),
    사학과_예결산안("인문대학", "사학과", "예결산안"),
    문예창작전공_예결산안("인문대학", "문예창작전공", "예결산안"),
    영화예술전공_예결산안("인문대학", "영화예술전공", "예결산안"),
    스포츠학부_예결산안("인문대학", "스포츠학부", "예결산안"),

    // 활동보고
    인문대학_활동보고("인문대학", "인문대학", "활동보고"),
    기독교학과_활동보고("인문대학", "기독교학과", "활동보고"),
    국어국문학과_활동보고("인문대학", "국어국문학과", "활동보고"),
    영어영문학과_활동보고("인문대학", "영어영문학과", "활동보고"),
    독어독문학과_활동보고("인문대학", "독어독문학과", "활동보고"),
    불어불문학과_활동보고("인문대학", "불어불문학과", "활동보고"),
    중어중문학과_활동보고("인문대학", "중어중문학과", "활동보고"),
    일어일문학과_활동보고("인문대학", "일어일문학과", "활동보고"),
    철학과_활동보고("인문대학", "철학과", "활동보고"),
    사학과_활동보고("인문대학", "사학과", "활동보고"),
    문예창작전공_활동보고("인문대학", "문예창작전공", "활동보고"),
    영화예술전공_활동보고("인문대학", "영화예술전공", "활동보고"),
    스포츠학부_활동보고("인문대학", "스포츠학부", "활동보고"),

    /*
     *   대분류 : 자연과학대학
     */
// 회칙 세칙
    자연과학대학_회칙_세칙("자연과학대학", "자연과학대학", "회칙 세칙"),
    수학과_회칙_세칙("자연과학대학", "수학과", "회칙 세칙"),
    물리학과_회칙_세칙("자연과학대학", "물리학과", "회칙 세칙"),
    화학과_회칙_세칙("자연과학대학", "화학과", "회칙 세칙"),
    정보통계보험수리학과_회칙_세칙("자연과학대학", "정보통계보험수리학과", "회칙 세칙"),
    의생명시스템학부_회칙_세칙("자연과학대학", "의생명시스템학부", "회칙 세칙"),

    // 회의록
    자연과학대학_회의록("자연과학대학", "자연과학대학", "회의록"),
    수학과_회의록("자연과학대학", "수학과", "회의록"),
    물리학과_회의록("자연과학대학", "물리학과", "회의록"),
    화학과_회의록("자연과학대학", "화학과", "회의록"),
    정보통계보험수리학과_회의록("자연과학대학", "정보통계보험수리학과", "회의록"),
    의생명시스템학부_회의록("자연과학대학", "의생명시스템학부", "회의록"),

    // 예결산안
    자연과학대학_예결산안("자연과학대학", "자연과학대학", "예결산안"),
    수학과_예결산안("자연과학대학", "수학과", "예결산안"),
    물리학과_예결산안("자연과학대학", "물리학과", "예결산안"),
    화학과_예결산안("자연과학대학", "화학과", "예결산안"),
    정보통계보험수리학과_예결산안("자연과학대학", "정보통계보험수리학과", "예결산안"),
    의생명시스템학부_예결산안("자연과학대학", "의생명시스템학부", "예결산안"),

    // 활동보고
    자연과학대학_활동보고("자연과학대학", "자연과학대학", "활동보고"),
    수학과_활동보고("자연과학대학", "수학과", "활동보고"),
    물리학과_활동보고("자연과학대학", "물리학과", "활동보고"),
    화학과_활동보고("자연과학대학", "화학과", "활동보고"),
    정보통계보험수리학과_활동보고("자연과학대학", "정보통계보험수리학과", "활동보고"),
    의생명시스템학부_활동보고("자연과학대학", "의생명시스템학부", "활동보고"),

    /*
     *   대분류 : IT대학
     */
    // 회칙 세칙
    IT대학_회칙_세칙("IT대학", "IT대학", "회칙 세칙"),
    컴퓨터학부_회칙_세칙("IT대학", "컴퓨터학부", "회칙 세칙"),
    전자정보공학부_회칙_세칙("IT대학", "전자정보공학부", "회칙 세칙"),
    글로벌미디어학부_회칙_세칙("IT대학", "글로벌미디어학부", "회칙 세칙"),
    소프트웨어학부_회칙_세칙("IT대학", "소프트웨어학부", "회칙 세칙"),
    AI융합학부_회칙_세칙("IT대학", "AI융합학부", "회칙 세칙"),
    미디어경영학과_회칙_세칙("IT대학", "미디어경영학과", "회칙 세칙"),

    // 회의록
    IT대학_회의록("IT대학", "IT대학", "회의록"),
    컴퓨터학부_회의록("IT대학", "컴퓨터학부", "회의록"),
    전자정보공학부_회의록("IT대학", "전자정보공학부", "회의록"),
    글로벌미디어학부_회의록("IT대학", "글로벌미디어학부", "회의록"),
    소프트웨어학부_회의록("IT대학", "소프트웨어학부", "회의록"),
    AI융합학부_회의록("IT대학", "AI융합학부", "회의록"),
    미디어경영학과_회의록("IT대학", "미디어경영학과", "회의록"),

    // 예결산안
    IT대학_예결산안("IT대학", "IT대학", "예결산안"),
    컴퓨터학부_예결산안("IT대학", "컴퓨터학부", "예결산안"),
    전자정보공학부_예결산안("IT대학", "전자정보공학부", "예결산안"),
    글로벌미디어학부_예결산안("IT대학", "글로벌미디어학부", "예결산안"),
    소프트웨어학부_예결산안("IT대학", "소프트웨어학부", "예결산안"),
    AI융합학부_예결산안("IT대학", "AI융합학부", "예결산안"),
    미디어경영학과_예결산안("IT대학", "미디어경영학과", "예결산안"),

    // 활동보고
    IT대학_활동보고("IT대학", "IT대학", "활동보고"),
    컴퓨터학부_활동보고("IT대학", "컴퓨터학부", "활동보고"),
    전자정보공학부_활동보고("IT대학", "전자정보공학부", "활동보고"),
    글로벌미디어학부_활동보고("IT대학", "글로벌미디어학부", "활동보고"),
    소프트웨어학부_활동보고("IT대학", "소프트웨어학부", "활동보고"),
    AI융합학부_활동보고("IT대학", "AI융합학부", "활동보고"),
    미디어경영학과_활동보고("IT대학", "미디어경영학과", "활동보고"),

    융합특성화자유전공학부_회칙_세칙("융합특성화자유전공학부", "융합특성화자유전공학부", "회칙 세칙"),
    동아리연합회_회칙_세칙("동아리연합회", "동아리연합회", "회칙 세칙"),

    //회의록
    융합특성화자유전공학부_회의록("융합특성화자유전공학부", "융합특성화자유전공학부", "회의록"),
    동아리연합회_회의록("동아리연합회", "동아리연합회", "회의록"),
    //예결산안
    융합특성화자유전공학부_예결산안("융합특성화자유전공학부", "융합특성화자유전공학부", "예결산안"),
    동아리연합회_예결산안("동아리연합회", "동아리연합회", "예결산안"),
    //활동보고
    융합특성화자유전공학부_활동보고("융합특성화자유전공학부", "융합특성화자유전공학부", "활동보고"),
    동아리연합회_활동보고("동아리연합회", "동아리연합회", "활동보고"),

    /*
     *   대분류 : 차세대반도체학과
     */
// 회칙 세칙
    차세대반도체학과_회칙_세칙("차세대반도체학과", "차세대반도체학과", "회칙 세칙"),

    // 회의록
    차세대반도체학과_회의록("차세대반도체학과", "차세대반도체학과", "회의록"),

    // 예결산안
    차세대반도체학과_예결산안("차세대반도체학과", "차세대반도체학과", "예결산안"),

    // 활동보고
    차세대반도체학과_활동보고("차세대반도체학과", "차세대반도체학과", "활동보고"),

    /*
     *   대분류 : 베어드학부대학
     */
// 회칙 세칙
    베어드학부대학_회칙_세칙("베어드학부대학", "베어드학부대학", "회칙 세칙"),

    // 회의록
    베어드학부대학_회의록("베어드학부대학", "베어드학부대학", "회의록"),

    // 예결산안
    베어드학부대학_예결산안("베어드학부대학", "베어드학부대학", "예결산안"),

    // 활동보고
    베어드학부대학_활동보고("베어드학부대학", "베어드학부대학", "활동보고"),

    //대표자회의결과보고
    경영대학_대표자회의결과보고("경영대학", "경영대학", "대표자회의결과보고"),
    경제통상대학_대표자회의결과보고("경제통상대학", "경제통상대학", "대표자회의결과보고"),
    공과대학_대표자회의결과보고("공과대학", "공과대학", "대표자회의결과보고"),
    법과대학_대표자회의결과보고("법과대학", "법과대학", "대표자회의결과보고"),
    사회과학대학_대표자회의결과보고("사회과학대학", "사회과학대학", "대표자회의결과보고"),
    인문대학_대표자회의결과보고("인문대학", "인문대학", "대표자회의결과보고"),
    자연과학대학_대표자회의결과보고("자연과학대학", "자연과학대학", "대표자회의결과보고"),
    IT대학_대표자회의결과보고("IT대학", "IT대학", "대표자회의결과보고"),
    동아리연합회_대표자회의결과보고("동아리연합회", "동아리연합회", "대표자회의결과보고"),

    융합특성화자유전공학부_선거시행세칙("융합특성화자유전공학부", "융합특성화자유전공학부", "선거시행세칙"),

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
