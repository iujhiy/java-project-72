package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;

import java.io.IOException;
import java.sql.SQLException;

public class App {
    private static final String DEFAULT_DATABASE_URL = "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1";
    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7070");
        return Integer.parseInt(port);
    }

    public static void main(String[] args) throws SQLException, IOException {
        var app = getApp();
        app.start(getPort());
    }

    public static Javalin getApp() throws IOException, SQLException {
        var hikariConfig = new HikariConfig();
        var url = System.getenv("JDBC_DATABASE_URL") == null
                ? DEFAULT_DATABASE_URL
                : System.getenv("JDBC_DATABASE_URL");
        hikariConfig.setJdbcUrl(url);
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });
        app.get("/", ctx -> {
            ctx.result("Hello World");
        });

        return app;
    }
}
