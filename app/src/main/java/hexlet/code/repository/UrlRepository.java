package hexlet.code.repository;

import hexlet.code.model.Url;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UrlRepository extends BaseRepository {

    public static void save(Url url) throws SQLException {
        String sql = "INSERT INTO urls(name, created_at) VALUES(?, ?)";
        try (var conn = dataSource.getConnection();
            var preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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

    public static List<Url> getEntities() throws SQLException {
        String sql = "SELECT * FROM urls";
        try (var conn = dataSource.getConnection();
            var preparedStatement = conn.prepareStatement(sql);
            var resultSet = preparedStatement.executeQuery()) {
            var entities = new ArrayList<Url>();
            while (resultSet.next()) {
                var url = createUrl(resultSet);
                entities.add(url);
            }
            return entities;
        }
    }

    public static Optional<Url> findById(int id) throws SQLException {
        String sql = "SELECT * FROM urls WHERE id = ? LIMIT 1";
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    var url = createUrl(resultSet);
                    return Optional.of(url);
                }
            }
        }
        return Optional.empty();
    }

    public static Url createUrl(ResultSet resultSet) throws SQLException {
        var url = new Url();
        url.setId(resultSet.getInt("id"));
        url.setName(resultSet.getString("name"));
        url.setCreatedAt(resultSet.getTimestamp("created_at"));
        return url;
    }

    public static boolean isAlreadyExists(Url url) throws SQLException {
        String sql = "SELECT EXISTS(SELECT 1 FROM urls WHERE name = ?)";
        try (var conn = dataSource.getConnection();
            var preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, url.getName());
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean(1);
                }
            }
            return false;
        }
    }
}
