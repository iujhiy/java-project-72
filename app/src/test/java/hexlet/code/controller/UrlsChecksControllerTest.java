package hexlet.code.controller;

import hexlet.code.BaseTestClass;
import hexlet.code.util.NamedRoutes;
import io.javalin.testtools.JavalinTest;
import mockwebserver3.MockResponse;
import mockwebserver3.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

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
    public void testCreateUrl() {
        JavalinTest.test(app, (server, client) -> {
            mockServer.enqueue(new MockResponse.Builder().code(200).build());
            client.post("/urls", "url=" + mockServerUrl);
            var checkResponse = client.post(NamedRoutes.urlPath(1));
            assertThat(checkResponse.code()).isEqualTo(302);
            var response = client.get(NamedRoutes.urlPath(1));
            assertThat(response.body().string()).contains("200");
        });
    }



}
