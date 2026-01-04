package hexlet.code.controller;

import hexlet.code.BaseTestClass;
import io.javalin.testtools.JavalinTest;
import mockwebserver3.MockResponse;
import mockwebserver3.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class UrlsChecksControllerTest extends BaseTestClass {
    private static MockWebServer mockServer;
    private static String mockServerUrl;

    @BeforeAll
    public static void setUpAll() throws IOException {
        mockServer = new MockWebServer();
        mockServer.enqueue(new MockResponse.Builder().code(200).build());
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
    public void testUrlNotFound() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "urlId=999";
        });
    }
}
