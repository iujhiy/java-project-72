package hexlet.code.repository;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.SQLException;
import java.util.ArrayList;

public class BaseRepository {
    public static HikariDataSource dataSource;

    public BaseRepository() { }

    public static void clearDataBase() throws SQLException {
        var sqlRequests = new ArrayList<String>();
        sqlRequests.add("DELETE FROM urls_check");
        sqlRequests.add("DELETE FROM urls");
        sqlRequests.add("ALTER TABLE urls_check ALTER COLUMN id RESTART WITH 1");
        sqlRequests.add("ALTER TABLE urls ALTER COLUMN id RESTART WITH 1");
        try (var conn = dataSource.getConnection();
            var statement = conn.createStatement()) {
            for (var sql: sqlRequests) {
                statement.executeUpdate(sql);
            }
        }
    }
}
