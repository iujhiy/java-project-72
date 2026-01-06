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

    public static void build(Context ctx) throws SQLException, IllegalAccessException {
        int urlId = ctx.pathParamAsClass("urlId", Integer.class).get();
        var urlOptional = UrlRepository.findById(urlId);
        if (urlOptional.isEmpty()) {
            ctx.sessionAttribute(FLASH_NAME, "Сайт с id: " + urlId + " не найден");
            ctx.redirect(NamedRoutes.urlsPath());
        } else {
            var url = urlOptional.get().getName();
            var statusCode = UrlChecker.getStatusCode(url);
            var seoAnalyseMap = UrlChecker.startSEOAnalyse(url);
            var urlCheck = new UrlCheck(urlId, statusCode);
            UrlCheckRepository.save(urlCheck);
            for (var set: seoAnalyseMap.entrySet()) {
                var value = set.getValue();
                var columnName = set.getKey();
                if (value != null && !value.isEmpty()) {
                    UrlCheckRepository.saveString(columnName, value, urlCheck.getId());
                }
            }
            ctx.sessionAttribute(FLASH_NAME, "Страница успешно проверена");
            ctx.redirect(NamedRoutes.urlPath(urlId));
        }
    }
}
