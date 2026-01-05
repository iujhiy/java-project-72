package hexlet.code.repository;

import hexlet.code.model.UrlCheck;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UrlCheckRepository extends BaseRepository {

    public static void save(UrlCheck urlCheck) throws SQLException {
        var sql = "INSERT INTO urls_check(url_id, created_at, status_code) VALUES(?, ?, ?)";
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, urlCheck.getUrlId());
            preparedStatement.setTimestamp(2, urlCheck.getCreatedAt());
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
        String sql = "SELECT * FROM urls_check WHERE url_id = ? ORDER BY id DESC";
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

    public static ArrayList<UrlCheck> getLastChecks() throws SQLException {
        String sql = """
            SELECT u_chk.url_id,
                   u_chk.status_code,
                   u_chk.created_at,
                   last_chk.id
            FROM urls_check u_chk
            JOIN
                (SELECT url_id, MAX(id) as id
                 FROM urls_check
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
        urlCheck.setCreatedAt(resultSet.getTimestamp("created_at"));
        urlCheck.setStatusCode(resultSet.getInt("status_code"));
//   проверить на null     urlCheck.setTitle();
//        urlCheck.setDescription();
//        urlCheck.setH1();
        return urlCheck;
    }
}
