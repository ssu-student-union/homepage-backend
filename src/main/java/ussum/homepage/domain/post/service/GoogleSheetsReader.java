package ussum.homepage.domain.post.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ussum.homepage.global.error.exception.GeneralException;
import ussum.homepage.infra.jpa.sheet_email.entity.CollegeDepartmentEmailEntity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ussum.homepage.global.error.status.ErrorStatus.GOOGLE_SHEET_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class GoogleSheetsReader {

    @Value("${google-sheet.name}")
    private String APPLICATION_NAME;
    @Value("${google-sheet.id}")
    private String SPREADSHEET_ID;
    @Value("${google-sheet.range}")
    private String RANGE;

    @Value("${google-sheet.credentials}")
    private String credentialsJson;

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public String getEmailsFromSheet(String deptName) throws GeneralSecurityException, IOException {

        try (InputStream inputStream = new ByteArrayInputStream(credentialsJson.getBytes(StandardCharsets.UTF_8))) {
            Credential credential = GoogleCredential
                    .fromStream(inputStream)
                    .createScoped(List.of("https://www.googleapis.com/auth/spreadsheets.readonly"));

            Sheets sheetsService = new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, credential)
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            ValueRange response = sheetsService.spreadsheets().values().get(SPREADSHEET_ID, RANGE).execute();

            List<List<Object>> values = response.getValues();

            if (values == null || values.isEmpty()) {
                throw new GeneralException(GOOGLE_SHEET_NOT_FOUND);
            }

            List<CollegeDepartmentEmailEntity> emailEntities = values.stream()
                    .map(row -> CollegeDepartmentEmailEntity.of(
                            getStringOrNull(row, 0), // Name
                            getStringOrNull(row, 1)  // Email
                    ))
                    .collect(Collectors.toList());

            String targetEmail = findEmailByName(emailEntities, deptName);

            return targetEmail;

        }
    }


    // 방어처리 메서드 (없으면 null 반환)
    private String getStringOrNull(List<Object> row, int index) {
        return Optional.ofNullable(row.size() > index ? row.get(index) : null)
                .map(Object::toString)
                .orElse(null);
    }

    public String findEmailByName(List<CollegeDepartmentEmailEntity> list, String deptName) {
        return list.stream()
                .filter(e -> deptName.equals(e.getName())) // 이름이 일치하는 엔티티만 추림
                .map(CollegeDepartmentEmailEntity::getEmail) // 이메일만 꺼냄
                .findFirst()
                .orElse(null);
    }
}


