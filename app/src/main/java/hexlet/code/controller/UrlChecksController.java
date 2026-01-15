package hexlet.code.controller;

import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import hexlet.code.service.UrlChecker;
import io.javalin.http.Context;

import java.sql.SQLException;

import static hexlet.code.util.UrlStringUtils.FLASH_NAME;

public final class UrlChecksController {
    private UrlChecksController() {
        throw new AssertionError("This is utility class");
    }

    public static void build(Context ctx) throws SQLException {
        int urlId = ctx.pathParamAsClass("urlId", Integer.class).get();
        var urlOptional = UrlRepository.findById(urlId);
        if (urlOptional.isEmpty()) {
            ctx.sessionAttribute(FLASH_NAME, "Сайт с id: " + urlId + " не найден");
            ctx.redirect(NamedRoutes.urlsPath());
            return;
        }
        var url = urlOptional.get().getName();
        var urlCheck = UrlChecker.startSEOAnalysis(url);
        var statusCode = UrlChecker.getStatusCode(url);
        urlCheck.setUrlId(urlId);
        urlCheck.setStatusCode(statusCode);
        try {
            UrlCheckRepository.save(urlCheck);
            ctx.sessionAttribute(FLASH_NAME, "Страница успешно проверена");
        } catch (Exception e) {
            UrlCheckRepository.save(urlCheck);
            ctx.sessionAttribute(FLASH_NAME, "Не удалось проверить сайт: " + e.getMessage());
        }
        ctx.redirect(NamedRoutes.urlPath(urlId));
    }
}
