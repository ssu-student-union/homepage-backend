package ussum.homepage.domain.csv_user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.application.user.service.dto.request.OnBoardingRequest;
import ussum.homepage.domain.csv_user.StudentCsv;
import ussum.homepage.domain.csv_user.StudentCsvRepository;
import ussum.homepage.global.error.exception.GeneralException;
import ussum.homepage.global.error.exception.InvalidValueException;
import ussum.homepage.global.error.status.ErrorStatus;
import ussum.homepage.infra.jpa.member.entity.MajorCode;
import ussum.homepage.infra.jpa.member.entity.MemberCode;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentCsvReader {
    private final StudentCsvRepository studentCsvRepository;

    public Optional<StudentCsv> getStudentWithStudentId(Long studentId, OnBoardingRequest request) {
        return studentCsvRepository.findByStudentId(studentId)
                .map(studentCsv -> {
                    checkStudentRight(request, studentCsv);
                    return studentCsv;
                });
    }

    public Optional<StudentCsv> getStudentWithStudentId(Long studentId){
        return studentCsvRepository.findByStudentId(studentId);
    }

    private void checkStudentRight(OnBoardingRequest request, StudentCsv studentCsv){
        boolean name = request.getName().equals(studentCsv.getStudentName());
        boolean studentId = request.getStudentId().equals(studentCsv.getStudentId().toString());
        boolean groupName = request.getMemberCode().equals(MemberCode.getEnumMemberCodeFromStringMemberCode(studentCsv.getGroupName()).getStringMemberCode());
        boolean major;


        // TODO(inho): csv의 단과대 학과 구조랑 MajorCode, MemberCode의 구조가 달라서 예외 처리 로직 추가
        try {
            if (request.getMajorCode().equals(MajorCode.getEnumMajorCodeFromStringMajorCode(studentCsv.getMajor()).getStringMajorCode())){
                major = true;
            }else{
                // ㅇㅇ
                major = request.getMajorCode().equals("아무거나") | studentCsv.getMajor().equals("아무거나");
            }
        } catch (Exception e) {
            if (e instanceof InvalidValueException) {
                if (request.getMajorCode().equals(MajorCode.getEnumMajorCodeFromStringMajorCode(studentCsv.getProgram()).getStringMajorCode())){
                    major = true;
                }else{
                    // ㅇㅇ
                    major = request.getMajorCode().equals("아무거나") | studentCsv.getMajor().equals("아무거나");
                }
            } else {
                throw e;  // 다른 예외는 다시 던지기
            }
        }

        if(!(name && studentId && groupName && major)){
            throw new GeneralException(ErrorStatus.INVALID_ONBOARDING_REQUEST);
        }
    }

}
