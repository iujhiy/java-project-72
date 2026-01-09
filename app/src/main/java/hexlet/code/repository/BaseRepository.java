package hexlet.code.repository;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.SQLException;

public class BaseRepository {
    public static HikariDataSource dataSource;

    public BaseRepository() { }

    public static void clearDataBase() throws SQLException {
        try (var conn = dataSource.getConnection();
             var statement = conn.createStatement()) {
            // 1. Удаляем все данные из таблиц (работает везде)
            statement.executeUpdate("DELETE FROM urls_check CASCADE");
            statement.executeUpdate("DELETE FROM urls CASCADE");

            // 2. Пробуем сбросить автоинкремент разными способами
            // Сначала пробуем синтаксис PostgreSQL (работает и для H2 с MODE=PostgreSQL)
            try {
                statement.executeUpdate("ALTER SEQUENCE urls_check_id_seq RESTART WITH 1");
                statement.executeUpdate("ALTER SEQUENCE urls_id_seq RESTART WITH 1");
            } catch (SQLException e) {
                // Если не сработало, пробуем синтаксис H2
                try {
                    statement.executeUpdate("ALTER TABLE urls_check ALTER COLUMN id RESTART WITH 1");
                    statement.executeUpdate("ALTER TABLE urls ALTER COLUMN id RESTART WITH 1");
                } catch (SQLException e2) {
                    // Если и это не сработало, пробуем с IF EXISTS для PostgreSQL
                    try {
                        statement.executeUpdate("ALTER SEQUENCE IF EXISTS urls_check_id_seq RESTART WITH 1");
                        statement.executeUpdate("ALTER SEQUENCE IF EXISTS urls_id_seq RESTART WITH 1");
                    } catch (SQLException e3) {
                        // Если ничего не работает, просто пропускаем сброс автоинкремента
                        // Это нормально для тестов - главное, что данные удалены
                    }
                }
            }
        }
    }
}
