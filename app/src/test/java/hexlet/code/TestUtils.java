package hexlet.code;

import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.model.UrlCheck;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static hexlet.code.repository.UrlCheckRepository.createUrlCheck;

public class TestUtils {

    public static Map<String, Object> getUrlByName(HikariDataSource dataSource, String url) throws SQLException {
        var result = new HashMap<String, Object>();
        var sql = "SELECT id, name FROM urls WHERE name = ?";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, url);
            var resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                result.put("id", resultSet.getInt("id"));
                result.put("name", resultSet.getString("name"));
                return result;
            }

            return null;
        }
    }

    public static Map<String, Object> getUrlCheck(HikariDataSource dataSource, int urlId) throws SQLException {
        var result = new HashMap<String, Object>();
        var sql = "SELECT * FROM url_checks WHERE url_id = ?";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, urlId);
            var resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                result.put("id", resultSet.getInt("id"));
                result.put("url_id", resultSet.getInt("url_id"));
                result.put("status_code", resultSet.getInt("status_code"));
                result.put("title", resultSet.getString("title"));
                result.put("h1", resultSet.getString("h1"));
                result.put("description", resultSet.getString("description"));
                return result;
            }

            return null;
        }
    }

    public static void saveUrlCheck(HikariDataSource dataSource, int urlId, int statusCode) throws SQLException {
        var sql = "INSERT INTO url_checks (url_id, status_code, created_at)"
                + "VALUES (?, ?, '2025-09-27 14:20:19.13')";

        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, urlId);
            stmt.setInt(2, statusCode);
            var resultSet = stmt.executeUpdate();
        }
    }

    public static List<UrlCheck> getLastChecks(HikariDataSource dataSource) throws SQLException {
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

    public static void saveUrl(HikariDataSource dataSource, String name) throws SQLException {
        String sql = "INSERT INTO urls(name, created_at) VALUES(?, '2025-09-27 14:20:19.13')";
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        }
    }
}
