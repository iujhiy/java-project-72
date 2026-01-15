package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.controller.UrlChecksController;
import io.javalin.Javalin;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

public class BaseTestClass {
    protected Javalin app;
    protected static HikariDataSource dataSource;
    protected HikariConfig hikariConfig;

    @BeforeEach
    public final void setUp() throws SQLException, IOException, URISyntaxException {
        //System.setProperty("JDBC_DATABASE_URL", "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;MODE=PostgreSQL");
        app = App.getApp();
        hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:h2:mem:project");
        dataSource = new HikariDataSource(hikariConfig);
        var schema = UrlChecksController.class.getClassLoader().getResource("schema.sql");
        var filePath = Paths.get(schema.toURI());
        var sql = Files.readString(filePath, StandardCharsets.UTF_8);
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(sql);
        }
        //BaseRepository.clearDataBase();
    }

//    @AfterEach
//    public final void clearDatabase() {
//        System.clearProperty("JDBC_DATABASE_URL");
//    }

}
