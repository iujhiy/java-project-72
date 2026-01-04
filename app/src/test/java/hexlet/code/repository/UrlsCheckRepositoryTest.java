package hexlet.code.repository;

import hexlet.code.App;
import hexlet.code.BaseTestClass;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlsCheckRepositoryTest extends BaseTestClass {

    @BeforeEach
    public final void makeUrl() throws SQLException {
        var url = new Url("https://example.com");
        UrlRepository.save(url);
    }

    @Test
    public void testUrlCheckSave() throws SQLException {
        final var urlId = 1;
        var statusCode = 200;
        var urlCheck1 = new UrlCheck(urlId,statusCode);
        UrlCheckRepository.save(urlCheck1);
        statusCode = 404;
        var urlCheck2 = new UrlCheck(urlId,statusCode);
        UrlCheckRepository.save(urlCheck2);
        var urlChecksFromDB = UrlCheckRepository.getEntitiesById(urlId);
        assertThat(urlChecksFromDB.size()).isEqualTo(2);
        assertThat(urlChecksFromDB.get(0).getUrlId()).isEqualTo(urlId);
        assertThat(urlChecksFromDB.get(0).getStatusCode()).isEqualTo(404);
        assertThat(urlChecksFromDB.get(1).getStatusCode()).isEqualTo(200);
    }

    @Test
    public void testLastUrlCheck() throws SQLException {
        final var urlId = 1;
        var statusCode = 200;
        var urlCheck1 = new UrlCheck(urlId, statusCode);
        statusCode = 404;
        var urlCheck2 = new UrlCheck(urlId, statusCode);
        UrlCheckRepository.save(urlCheck1);
        UrlCheckRepository.save(urlCheck2);
        var lastChecks = UrlCheckRepository.getLastChecks();
        assertThat(lastChecks.getFirst().getStatusCode())
                .isEqualTo(urlCheck2.getStatusCode());
        assertThat(lastChecks).doesNotContain(urlCheck1);
    }

}
