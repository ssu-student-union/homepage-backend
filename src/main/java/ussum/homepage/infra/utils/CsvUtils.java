package ussum.homepage.infra.utils;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ussum.homepage.application.user.service.dto.request.OnBoardingRequest;
import ussum.homepage.application.user.service.dto.response.CsvOnBoardingResponse;
import ussum.homepage.domain.user.User;
import ussum.homepage.domain.user.UserRepository;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Component
public class CsvUtils {
    private final UserRepository userRepository;

    // CSV파일을 순회하면서 학적상 정보가 일치하지 않는 유저를 CSV파일 정보로 바꾸고 저장
    public void updateStudentStatusFromCsv(MultipartFile file){
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()));
            String[] headers = reader.readNext();

            int studentIdIndex = -1;
            int studentStatusIndex = -1;

            for (int i = 0; i < headers.length; i++) {
                if (headers[i].equalsIgnoreCase("학번")) {
                    studentIdIndex = i;
                } else if (headers[i].equalsIgnoreCase("학적상")) {
                    studentStatusIndex = i;
                }
            }

            if (studentIdIndex == -1 || studentStatusIndex == -1) {
                throw new IllegalArgumentException("필요한 컬럼이 csv파일에 존재하지 않습니다."); // 수정 필요
            }

            String[] row;

            // csv 파일 전체를 순회함. 수정 필요
            while ((row = reader.readNext()) != null) {
                Long studentId = Long.valueOf(row[studentIdIndex]);
                String studentStatus = row[studentStatusIndex];

                Optional<User> optionalUser = userRepository.findByStudentId(studentId);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    if (!user.getStudentStatus().equals(studentStatus)) {
                        user.setStudentStatus(studentStatus);
                        userRepository.save(user);
                    }
                }
            }

        }catch (IOException e){

        }catch (CsvException e){

        }
    }

    // 온보딩 정보 받은 후, 이름 대학 학부가 학번을 기반한 레코드와 동일한지 true, false로 만들어진 json 리턴
    public CsvOnBoardingResponse getOnBoardingResponseFromCsv(OnBoardingRequest request, MultipartFile file){
        try {
            InputStreamReader reader = new InputStreamReader(file.getInputStream());
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());

            for (CSVRecord record : csvParser) {
                if (record.get("학번").equals(String.valueOf(request.getStudentId()))) {
                    boolean name = record.get("이름").equals(request.getName());
                    boolean groupName = record.get("대학").equals(request.getGroupName());
                    boolean major = record.get("학과(부)").equals(request.getMajor());
                    CsvOnBoardingResponse response = CsvOnBoardingResponse.of(name, groupName, major);
                    return response;
                }
            }
        }catch (IOException e){
            // 파일이 존재하지 않음을 의미
            return null;
        }
        return null;

    }
}
