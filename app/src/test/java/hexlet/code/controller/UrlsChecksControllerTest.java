package hexlet.code.controller;

import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.App;
import hexlet.code.TestUtils;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;


import static org.assertj.core.api.Assertions.assertThat;

public class UrlsChecksControllerTest {
    private static MockWebServer mockServer;
    private static String mockServerUrl;
    private Javalin app;
    public static HikariDataSource dataSource;
    private static final String HTML_BODY = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <meta name="description" content="it's description content">
                <title>It's title</title>
            </head>
            <body>
                <div>
                    <h1>It's h1</h1>
                </div>
            </body>
            </html>
            """;

    @BeforeAll
    public static void setUpAll() throws IOException {
        mockServer = new MockWebServer();
        MockResponse mockResponse = new MockResponse().setBody(HTML_BODY);
        mockServer.enqueue(mockResponse);
        mockServer.start();
        mockServerUrl = mockServer.url("/").toString();
    }

    @AfterAll
    public static void clearAll() throws IOException {
        if (mockServer != null) {
            mockServer.shutdown();
        }
    }

    @BeforeEach
    public final void setUp() throws SQLException, IOException {
        app = App.getApp();
        dataSource = App.getDataSource();
    }

    @Test
    public void testCreateUrlWithMockServer() {
        JavalinTest.test(app, (server, client) -> {
            var testUrl = mockServerUrl.endsWith("/")
                    ? mockServerUrl.substring(0, mockServerUrl.length() - 1)
                    : mockServerUrl;
            assertThat(client.post(NamedRoutes.urlsPath(), "url=" + testUrl).code())
                    .isEqualTo(HttpStatus.OK.getCode());

            var urlMap = TestUtils.getUrlByName(dataSource, testUrl);
            assertThat(urlMap).isNotNull();
            assertThat(urlMap.get("name")).isEqualTo(testUrl);
            assertThat(urlMap.get("id")).isNotNull();
            int urlId = (int) urlMap.get("id");

            assertThat(client.post(NamedRoutes.urlChecksPath(urlId)).code())
                    .isEqualTo(HttpStatus.OK.getCode());
            var actualUrlCheck = TestUtils.getUrlCheck(dataSource, urlId);
            assertThat(actualUrlCheck).isNotNull();
            assertThat(actualUrlCheck.get("h1")).isEqualTo("It's h1");
            assertThat(actualUrlCheck.get("description")).isEqualTo("it's description content");
            assertThat(actualUrlCheck.get("title")).isEqualTo("It's title");
        });
    }
}
