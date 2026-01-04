package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends BaseTestClass {
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
            try (var response = client.post("/urls", requestBody)) {
                assertThat(response.code()).isEqualTo(200);
                assertThat(response.body().string()).contains("https://example.com");
            }
        });
    }

    @Test
    public void testCreateDuplicateUrl() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://example.com";
            try (var response = client.post("/urls", requestBody)) {
                assertThat(response.code()).isEqualTo(200);
            }
            var requestBody2 = "url=https://example.com";
            try (var response = client.post("/urls", requestBody2)) {
                assertThat(response.code()).isEqualTo(409);
            }
        });
    }

    @Test
    public void testIncorrectHostUrl() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=ttps://something.com";
            try (var response = client.post("/urls", requestBody)) {
                assertThat(response.code()).isEqualTo(400);
            }
        });
    }

    @Test
    public void testIncorrectNameUrl() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url= ";
            try (var response = client.post("/urls", requestBody)) {
                assertThat(response.code()).isEqualTo(400);
            }
        });
    }
}
