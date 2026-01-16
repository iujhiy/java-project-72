package hexlet.code.repository;

import com.zaxxer.hikari.HikariDataSource;

public class BaseRepository {
    public static HikariDataSource dataSource;

    public BaseRepository() {
        // этот класс нужен для централизованного подключения к HikariDataSource для репозиториев-наследников
    }
}
