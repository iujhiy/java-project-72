package hexlet.code.util;

import java.net.URL;

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

}
