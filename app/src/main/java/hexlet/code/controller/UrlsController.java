package hexlet.code.controller;

import hexlet.code.dto.checks.UrlChecksPage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import hexlet.code.util.UrlStringUtils;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;

import static hexlet.code.util.UrlStringUtils.FLASH_NAME;
import static io.javalin.rendering.template.TemplateUtil.model;


public final class UrlsController {
    private UrlsController() {
        throw new AssertionError("This is utility class");
    }


    public static void create(Context ctx) throws SQLException {
        var urlString = ctx.formParam("url");
        if (urlString == null || urlString.trim().isEmpty()) {
            saveFlashAndStatus(ctx, "URL не может быть пустым",
                    HttpStatus.BAD_REQUEST.getCode());
            index(ctx);
            return;
        }
        URL url;
        try {
            URI uri = new URI(urlString);
            url = uri.toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            saveFlashAndStatus(ctx, "Некорректный URL",
                    HttpStatus.BAD_REQUEST.getCode());
            index(ctx);
            return;
        }
        String clearUrl = UrlStringUtils.makeClearURL(url);
        var result = new Url(clearUrl);
        if (!UrlRepository.isAlreadyExists(result)) {
            UrlRepository.save(result);
            ctx.sessionAttribute(FLASH_NAME, "Страница успешно добавлена");
            ctx.redirect(NamedRoutes.urlsPath());
        } else {
            saveFlashAndStatus(ctx, "Страница уже существует",
                    HttpStatus.CONFLICT.getCode());
            index(ctx); // отрисовываем страницу /urls без сохранения в БД и с определенным статусом
        }
    }

    public static void saveFlashAndStatus(Context ctx, String flash, int status) {
        ctx.sessionAttribute(FLASH_NAME, flash);
        ctx.status(status);
    }

    public static void index(Context ctx) throws SQLException {
        var urls = UrlRepository.getEntities();
        var urlPage = new UrlsPage(urls);
        var lastChecks = UrlCheckRepository.getLastChecks();
        var lastChecksPage = new UrlChecksPage(lastChecks);
        urlPage.setFlash(ctx.consumeSessionAttribute(FLASH_NAME));
        ctx.render(NamedRoutes.urlsTemplate(),
                model("urlPage", urlPage, "lastChecksPage", lastChecksPage));
    }

    public static void show(Context ctx) throws SQLException {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        var url = UrlRepository.findById(id)
                .orElseThrow(() -> new NotFoundResponse("Url с id '" + id + "' не найден"));
        var urlPage = new UrlPage(url);
        var urlChecks = UrlCheckRepository.getEntitiesById(id);
        var urlChecksPage = new UrlChecksPage(urlChecks);
        urlPage.setFlash(ctx.consumeSessionAttribute(FLASH_NAME));
        urlChecksPage.setFlash(ctx.consumeSessionAttribute(FLASH_NAME));
        ctx.render(NamedRoutes.urlTemplate(), model("urlPage", urlPage, "urlChecksPage", urlChecksPage));
    }
}
