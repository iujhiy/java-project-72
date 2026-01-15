package hexlet.code.util;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class UrlStringUtils {
    private UrlStringUtils() {
        throw new AssertionError("This is utility class");
    }

    public static final String FLASH_NAME = "flash";
    public static final String ERROR_FLASH_NAME = "errorFlash";

    public static String makeClearURL(URL url) {
        StringBuilder clearUrl = new StringBuilder();
        clearUrl.append(url.getProtocol())
                .append("://")
                .append(url.getHost());
        if (url.getPort() != -1) {
            clearUrl.append(":").append(url.getPort());
        }
        return clearUrl.toString().trim().toLowerCase();
    }

    public static String getCreatedAtAsString(LocalDateTime createdAt) {
        if (createdAt == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return createdAt.format(formatter);
    }


    public static boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            if (metaData.getColumnName(i).equalsIgnoreCase(columnName)) {
                return true;
            }
        }
        return false;
    }
}
