package ussum.homepage.infra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

public class DateUtilTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");

    @Test
    public void testParseValidYearMonth() {
        // Given
        String validMonthString = "202503";

        // When
        YearMonth yearMonth = YearMonth.parse(validMonthString, FORMATTER);

        // Then
        System.out.println(yearMonth);
        assertEquals(YearMonth.of(2025, 3), yearMonth);
    }
}
