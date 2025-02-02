package ussum.homepage.infra.utils;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private static final DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private static final DateTimeFormatter CUSTOM_HOUR_MIN_SEC_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private static final DateTimeFormatter YEAR_MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");

    public static String formatToCustomString(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(CUSTOM_FORMATTER);
    }

    public static LocalDateTime parseFromCustomString(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(dateString, CUSTOM_FORMATTER);
    }

    public static String formatHourMinSecToCustomString(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(CUSTOM_HOUR_MIN_SEC_FORMATTER);
    }

    public static LocalDateTime parseHourMinSecFromCustomString(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(dateString, CUSTOM_HOUR_MIN_SEC_FORMATTER);
    }

    public static YearMonth parseYearMonthFromString(String monthString) {
        if (monthString == null || monthString.isEmpty()) {
            return null;
        }
        return YearMonth.parse(monthString, YEAR_MONTH_FORMATTER);
    }
}

