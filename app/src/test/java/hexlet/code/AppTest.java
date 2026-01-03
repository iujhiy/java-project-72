package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.BaseRepository;
import hexlet.code.repository.UrlRepository;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import mockwebserver3.MockWebServer;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {
    private Javalin app;
    private static MockWebServer mockServer;
    private static String mockServerUrl;

    @BeforeAll
    public static void setUpAll() throws IOException {
        mockServer = new MockWebServer();
        mockServerUrl = mockServer.url("/").toString();
        mockServer.start();
    }

    @AfterAll
    public static void clearAll() {
        if (mockServer != null) {
            mockServer.close();
        }
    }

    @BeforeEach
    public final void setUp() throws SQLException, IOException {
        System.setProperty("JDBC_DATABASE_URL", "jdbc:h2:mem:project;DB_CLOSE_DELAY=-1;MODE=PostgreSQL");
        app = App.getApp();
        BaseRepository.clearDataBase();
    }

    @AfterEach
    public final void clearDatabase() {
        System.clearProperty("JDBC_DATABASE_URL");
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testUrlPage() {
        JavalinTest.test(app, (server, client) -> {
            var url = new Url("https://some-domain.org");
            UrlRepository.save(url);
            var response = client.get("/urls/" + url.getId());
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testUrlNotFound() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/999999");
            assertThat(response.code()).isEqualTo(404);
        });
    }

    @Test
    public void testCreateUrl() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://example.com";
            var response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://example.com");
        });
    }

    @Test
    public void testIncorrectUrl() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=example.com";
            var response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://example.com");
        });
    }
}
