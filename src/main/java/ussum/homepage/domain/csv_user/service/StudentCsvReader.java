package ussum.homepage.domain.csv_user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ussum.homepage.application.user.service.dto.request.OnBoardingRequest;
import ussum.homepage.domain.csv_user.StudentCsv;
import ussum.homepage.domain.csv_user.StudentCsvRepository;
import ussum.homepage.global.error.exception.GeneralException;
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
        if (request.getMajorCode().equals(MajorCode.getEnumMajorCodeFromStringMajorCode(studentCsv.getMajor()).getStringMajorCode())){
            major = true;
        }else{
            // ㅇㅇ
            major = request.getMajorCode().equals("아무거나") | studentCsv.getMajor().equals("아무거나");
        }

        if(!(name && studentId && groupName && major)){
            throw new GeneralException(ErrorStatus.INVALID_ONBOARDING_REQUEST);
        }
    }

}
