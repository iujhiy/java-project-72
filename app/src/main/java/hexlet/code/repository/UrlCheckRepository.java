package hexlet.code.repository;

import hexlet.code.model.UrlCheck;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

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

    public static UrlCheck createUrlCheck(ResultSet resultSet) throws SQLException {
        var urlCheck = new UrlCheck();
        urlCheck.setId(resultSet.getInt("id"));
        urlCheck.setUrlId(resultSet.getInt("url_id"));
        urlCheck.setCreatedAt(resultSet.getTimestamp("created_at"));
        return urlCheck;
    }
}
