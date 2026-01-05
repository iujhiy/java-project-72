package hexlet.code.controller;

import hexlet.code.BaseTestClass;
import hexlet.code.repository.BaseRepository;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.testtools.JavalinTest;
import io.micrometer.core.instrument.binder.netty4.NettyAllocatorMetrics;
import mockwebserver3.MockResponse;
import mockwebserver3.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.naming.Name;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlsChecksControllerTest extends BaseTestClass {
    private static MockWebServer mockServer;
    private static String mockServerUrl;

    @BeforeAll
    public static void setUpAll() throws IOException {
        mockServer = new MockWebServer();
        mockServer.start();
        mockServerUrl = mockServer.url("/").toString();
    }

    @AfterAll
    public static void clearAll() {
        if (mockServer != null) {
            mockServer.close();
        }
    }

    @Test
    public void testCreateUrlWithMockServer() {
        JavalinTest.test(app, (server, client) -> {
            mockServer.enqueue(new MockResponse.Builder()
                    .code(200)
                    .build());
            var testUrl = mockServerUrl.endsWith("/") ?
                    mockServerUrl.substring(0, mockServerUrl.length() - 1) :
                    mockServerUrl;
            client.post(NamedRoutes.urlsPath(), "url=" + testUrl);
            client.post(NamedRoutes.urlChecksPath(1));
            var response = client.get(NamedRoutes.urlPath(1));
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string())
                    .contains(testUrl)
                    .contains("200");
        });
    }
}
