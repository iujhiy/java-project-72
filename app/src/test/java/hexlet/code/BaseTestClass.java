package hexlet.code;

import io.javalin.Javalin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.sql.SQLException;

public class BaseTestClass {
    protected Javalin app;

    @BeforeEach
    public final void setUp() throws SQLException, IOException {
        System.setProperty("JDBC_DATABASE_URL", "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;MODE=PostgreSQL");
        app = App.getApp();
        //BaseRepository.clearDataBase();
    }

    @AfterEach
    public final void clearDatabase() {
        System.clearProperty("JDBC_DATABASE_URL");
    }

}
