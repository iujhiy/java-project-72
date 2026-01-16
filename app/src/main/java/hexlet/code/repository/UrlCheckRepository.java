package hexlet.code.repository;

import hexlet.code.model.UrlCheck;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static hexlet.code.util.UrlStringUtils.hasColumn;

public class UrlCheckRepository extends BaseRepository {

    public static void save(UrlCheck urlCheck) throws SQLException {
        var sql = "INSERT INTO url_checks(url_id, created_at, status_code, title, h1, description)"
                + " VALUES(?, ?, ?, ?, ?, ?)";
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            var currentDateTimeNow = LocalDateTime.now();
            var timestampDateTime = Timestamp.valueOf(currentDateTimeNow);
            urlCheck.setCreatedAt(currentDateTimeNow);
            preparedStatement.setInt(1, urlCheck.getUrlId());
            preparedStatement.setTimestamp(2, timestampDateTime);
            preparedStatement.setInt(3, urlCheck.getStatusCode());
            preparedStatement.setString(4, urlCheck.getTitle());
            preparedStatement.setString(5, urlCheck.getH1());
            preparedStatement.setString(6, urlCheck.getDescription().toString());
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                urlCheck.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Ошибка добавления в базу данных");
            }
        }
    }

    public static List<UrlCheck> getEntitiesById(int urlId) throws SQLException {
        String sql = "SELECT * FROM url_checks WHERE url_id = ? ORDER BY id DESC";
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, urlId);
            try (var resultSet = preparedStatement.executeQuery()) {
                var result = new ArrayList<UrlCheck>();
                while (resultSet.next()) {
                    var urlCheck = createUrlCheck(resultSet);
                    result.add(urlCheck);
                }
                return result;
            }
        }
    }

    public static List<UrlCheck> getLastChecks() throws SQLException {
        String sql = """
            SELECT u_chk.url_id,
                   u_chk.status_code,
                   u_chk.created_at,
                   last_chk.id
            FROM url_checks u_chk
            JOIN
                (SELECT url_id, MAX(id) as id
                 FROM url_checks
                 GROUP BY url_id) as last_chk
            ON u_chk.id = last_chk.id
            """;
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql)) {
            try (var resultSet = preparedStatement.executeQuery()) {
                var result = new ArrayList<UrlCheck>();
                while (resultSet.next()) {
                    var urlCheck = createUrlCheck(resultSet);
                    result.add(urlCheck);
                }
                return result;
            }
        }
    }


    public static UrlCheck createUrlCheck(ResultSet resultSet) throws SQLException {
        var urlCheck = new UrlCheck();
        urlCheck.setId(resultSet.getInt("id"));
        urlCheck.setUrlId(resultSet.getInt("url_id"));
        var timestampDateTime = resultSet.getTimestamp("created_at");
        urlCheck.setCreatedAt(timestampDateTime.toLocalDateTime());
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
}
