package hexlet.code.repository;

import hexlet.code.BaseTestClass;
import hexlet.code.TestUtils;
import hexlet.code.model.UrlCheck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlsCheckRepositoryTest extends BaseTestClass {

    @BeforeEach
    public final void makeUrl() throws SQLException {
        String url = "https://example.com";
        TestUtils.saveUrl(dataSource, url);
    }

    @Test
    public void testUrlCheckSave() throws SQLException {
        final var urlId = 1;
        var statusCode = 200;
        TestUtils.saveUrlCheck(dataSource, urlId, statusCode);
        statusCode = 404;
        TestUtils.saveUrlCheck(dataSource, urlId, statusCode);
        var urlChecksFromDB = UrlCheckRepository.getEntitiesById(urlId);
        final var entityCount = 2;
        assertThat(urlChecksFromDB.size()).isEqualTo(entityCount);
        assertThat(urlChecksFromDB.get(0).getUrlId()).isEqualTo(urlId);
        assertThat(urlChecksFromDB.get(0).getStatusCode()).isEqualTo(404);
        assertThat(urlChecksFromDB.get(1).getStatusCode()).isEqualTo(200);
    }

    @Test
    public void testLastUrlCheck() throws SQLException {
        final var urlId = 1;
        var statusCode = 200;
        var urlCheck1 = new UrlCheck(urlId, statusCode);
        TestUtils.saveUrlCheck(dataSource, urlId, statusCode);
        statusCode = 404;
        var urlCheck2 = new UrlCheck(urlId, statusCode);
        TestUtils.saveUrlCheck(dataSource, urlId, statusCode);
        var lastChecks = TestUtils.getLastChecks(dataSource);
        assertThat(lastChecks.getFirst().getStatusCode())
                .isEqualTo(urlCheck2.getStatusCode());
        assertThat(lastChecks).doesNotContain(urlCheck1);
    }

}
