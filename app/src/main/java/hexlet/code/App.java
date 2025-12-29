package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import hexlet.code.controller.UrlChecksController;
import hexlet.code.controller.UrlsController;
import hexlet.code.repository.BaseRepository;
import hexlet.code.util.ErrorResponse;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;
import io.javalin.rendering.template.JavalinJte;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.stream.Collectors;

@Slf4j
public class App {
    private static final String DEFAULT_DATABASE_URL = "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;MODE=PostgreSQL";

    private static String readRecourseFile(String filename) throws IOException {
        var inputStream = App.class.getClassLoader().getResourceAsStream(filename);
        try (BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return bufferedReader.lines().collect(Collectors.joining("\n"));
        }
    }

    // ищем шаблоны в templates
    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }

    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "7072");
        return Integer.parseInt(port);
    }

    public static void main(String[] args) throws IOException, SQLException {
        var app = getApp();
        app.start(getPort());
    }

    public static Javalin getApp() throws IOException, SQLException {
        var hikariConfig = new HikariConfig();
        var url = System.getenv("JDBC_DATABASE_URL") == null // если не установлена переменная окружения
                ? DEFAULT_DATABASE_URL // ставим свою на h2 базу
                : System.getenv("JDBC_DATABASE_URL");
        hikariConfig.setJdbcUrl(url);
        var dataSource = new HikariDataSource(hikariConfig);
        var sql = readRecourseFile("schema.sql"); // схема базы данных
        log.info(sql);
        try (var connection = dataSource.getConnection();
            var statement = connection.createStatement()) {
            statement.execute(sql);
        }
        BaseRepository.dataSource = dataSource;


        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
        });

        app.before(ctx -> ctx.contentType("text/html; charset=UTF-8"));

        app.get("/", ctx -> ctx.render("index.jte"));

        app.post(NamedRoutes.urlsPath(), UrlsController::create);
        app.get(NamedRoutes.urlsPath(), UrlsController::index);
        app.get(NamedRoutes.urlPath("{id}"), UrlsController::show);

        app.post(NamedRoutes.urlChecksPath("{id}"), UrlChecksController::build);

        handeExceptions(app);

        return app;
    }

    public static void handeExceptions(Javalin app) {
        app.exception(IllegalArgumentException.class, (e, ctx) -> {
            ctx.status(HttpStatus.BAD_REQUEST);
            log.info(e.getMessage());
            ctx.json(new ErrorResponse("Bad request", e.getMessage()));
        });

        app.exception(NotFoundResponse.class, (e, ctx) -> {
            ctx.status(HttpStatus.NOT_FOUND);
            log.info(e.getMessage());
            ctx.json(new ErrorResponse("Not found", e.getMessage()));
        });

        app.exception(Exception.class, (e, ctx) -> {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
            log.info(e.getMessage());
            ctx.json(new ErrorResponse("Internal server error", e.getMessage()));
        });

    }
}
