package hexlet.code.controller;

import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import hexlet.code.util.UrlChecker;
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
        } else {
            var statusCode = UrlChecker.getStatusCode(urlOptional.get().getName());

            var urlCheck = new UrlCheck(urlId, statusCode);
            UrlCheckRepository.save(urlCheck);
            ctx.sessionAttribute(FLASH_NAME, "Страница успешно проверена");
            ctx.redirect(NamedRoutes.urlPath(urlId));
        }
    }
}
