package hexlet.code.util;

import hexlet.code.model.UrlCheck;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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

    public static UrlCheck createUrlCheck(ResultSet resultSet) throws SQLException {
        var urlCheck = new UrlCheck();
        urlCheck.setId(resultSet.getInt("id"));
        urlCheck.setUrlId(resultSet.getInt("url_id"));
        urlCheck.setCreatedAt(resultSet.getTimestamp("created_at"));
        urlCheck.setStatusCode(resultSet.getInt("status_code"));
        if (hasColumn(resultSet, "description")) {
            String description = resultSet.getString("description");
            if (description != null) {
                urlCheck.setDescription(new StringBuilder(description));
            }
        }
        if (hasColumn(resultSet, "h1")) {
            String h1 = resultSet.getString("h1");
            if (h1 != null) {
                urlCheck.setH1(h1);
            }
        }
        if (hasColumn(resultSet, "title")) {
            String title = resultSet.getString("title");
            if (title != null) {
                urlCheck.setTitle(title);
            }
        }
        return urlCheck;
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
