package hexlet.code.controller;

import hexlet.code.dto.checks.UrlCheckPage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static hexlet.code.util.UrlStringUtils.FLASH_NAME;
import static io.javalin.rendering.template.TemplateUtil.model;

public final class UrlChecksController {
    private UrlChecksController() {
        throw new AssertionError("This is utility class");
    }

    public static void build(Context ctx) throws SQLException {
        int urlId = ctx.pathParamAsClass("urlId", Integer.class).get();
        if (UrlRepository.findById(urlId).isEmpty()) {
            ctx.sessionAttribute(FLASH_NAME, "Сайт с id: " + urlId + " не найден");
            ctx.redirect(NamedRoutes.urlsPath());
        } else {
            var currentDateTimeNow = Timestamp.valueOf(LocalDateTime.now());
            var urlCheck = new UrlCheck(urlId, currentDateTimeNow);
            UrlCheckRepository.save(urlCheck);
            ctx.sessionAttribute(FLASH_NAME, "Страница успешно проверена");
            ctx.redirect(NamedRoutes.urlPath(urlId));
        }
    }

//    public static void show(Context ctx) throws SQLException {
//        int urlId = ctx.pathParamAsClass("urlId", Integer.class).get();
//        var urlCheck = UrlCheckRepository.findById(urlId)
//                .orElseThrow(() -> new NotFoundResponse("Url с urlId '" + urlId + "' не найден"));
//        var url = UrlRepository.findById(urlId)
//                .orElseThrow(() -> new NotFoundResponse("Url с urlId '" + urlId + "' не найден"));
//        var urlCheckPage = new UrlCheckPage(urlCheck);
//        var urlPage = new UrlPage(url);
//        urlCheckPage.setFlash(ctx.consumeSessionAttribute(FLASH_NAME));
//        ctx.render(NamedRoutes.urlTemplate(), model("urlPage", urlPage, "urlCheckPage", urlCheckPage));
//    }
}
