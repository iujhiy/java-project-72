package hexlet.code.repository;

import hexlet.code.model.Url;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class UrlRepository extends BaseRepository {

    public static void save(Url url) throws SQLException {
        String sql = "INSERT INTO urls(name, created_at) VALUES(?, ?)";
        try (var conn = dataSource.getConnection();
            var preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            var localDateTimeNow = Timestamp.valueOf(LocalDateTime.now());
            url.setCreatedAt(localDateTimeNow);
            preparedStatement.setString(1, url.getName());
            preparedStatement.setTimestamp(2, localDateTimeNow);
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                url.setId(generatedKeys.getInt(1)); // ставим возвращенный ключ базы в объект url
            } else {
                throw new SQLException("Ошибка добавления в базу данных");
            }
        }
    }

    public static boolean isAlreadyExists(Url url) throws SQLException {
        String sql = "SELECT EXISTS(SELECT 1 FROM urls WHERE name = ?)";
        try (var conn = dataSource.getConnection();
            var preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, url.getName());
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean(1);
            }
            return false;
        }
    }
}
