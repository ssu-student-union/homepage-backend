package ussum.homepage.infra.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private static final DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

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
}
