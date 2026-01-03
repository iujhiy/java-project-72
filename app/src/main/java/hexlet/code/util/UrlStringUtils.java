package hexlet.code.util;

import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public final class UrlStringUtils {
    private UrlStringUtils() {
        throw new AssertionError("This is utility class");
    }

    public static final String FLASH_NAME = "flash";

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

    public static String getCreatedAtAsString(Timestamp createdAt) {
        if (createdAt == null) {
            return "";
        }
        return new SimpleDateFormat("dd.MM.yyyy HH:mm").format(createdAt);
    }
}
