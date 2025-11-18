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
                url.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("DB haven't returned an id after saving an entity");
            }
        }
    }
}
