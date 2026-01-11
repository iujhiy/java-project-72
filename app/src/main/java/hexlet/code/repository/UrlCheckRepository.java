package hexlet.code.repository;

import hexlet.code.model.UrlCheck;
import hexlet.code.util.UrlStringUtils;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;

public class UrlCheckRepository extends BaseRepository {

    public static void save(UrlCheck urlCheck) throws SQLException {
        var sql = "INSERT INTO url_checks(url_id, created_at, status_code) VALUES(?, ?, ?)";
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            var currentDateTimeNow = Timestamp.valueOf(LocalDateTime.now());
            urlCheck.setCreatedAt(currentDateTimeNow);
            preparedStatement.setInt(1, urlCheck.getUrlId());
            preparedStatement.setTimestamp(2, currentDateTimeNow);
            preparedStatement.setInt(3, urlCheck.getStatusCode());
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                urlCheck.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Ошибка добавления в базу данных");
            }
        }
    }

    public static ArrayList<UrlCheck> getEntitiesById(int urlId) throws SQLException {
        String sql = "SELECT * FROM url_checks WHERE url_id = ? ORDER BY id DESC";
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, urlId);
            try (var resultSet = preparedStatement.executeQuery()) {
                var result = new ArrayList<UrlCheck>();
                while (resultSet.next()) {
                    var urlCheck = UrlStringUtils.createUrlCheck(resultSet);
                    result.add(urlCheck);
                }
                return result;
            }
        }
    }

    public static ArrayList<UrlCheck> getLastChecks() throws SQLException {
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
                    var urlCheck = UrlStringUtils.createUrlCheck(resultSet);
                    result.add(urlCheck);
                }
                return result;
            }
        }
    }

    public static void saveString(String columnName, String value, int id)
            throws SQLException, IllegalAccessException {
        Set<String> allowedColumns = Set.of("h1", "title", "description");
        if (!allowedColumns.contains(columnName)) {
            throw new IllegalAccessException("Недопустимое значение столбца");
        }
        var sql = "UPDATE url_checks SET " + columnName + " = ? WHERE id = ?";
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, value);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        }
    }


}
