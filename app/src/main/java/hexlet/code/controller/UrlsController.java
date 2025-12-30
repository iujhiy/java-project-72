package hexlet.code.controller;

import hexlet.code.dto.checks.UrlCheckPage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import hexlet.code.util.UrlStringUtils;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;

import static hexlet.code.util.UrlStringUtils.FLASH_NAME;
import static hexlet.code.util.UrlStringUtils.URL_CHECK_PAGE;
import static io.javalin.rendering.template.TemplateUtil.model;


public final class UrlsController {
    private UrlsController() {
        throw new AssertionError("This is utility class");
    }


    public static void create(Context ctx) throws SQLException {
        var urlString = ctx.formParam("url");
        try {
            if (urlString == null || urlString.trim().isEmpty()) {
                throw new IllegalArgumentException("URL не может быть пустым");
            }
            URI uri = new URI(urlString);
            URL url = uri.toURL();
            String clearUrl = UrlStringUtils.makeClearURL(url);
            var result = new Url(clearUrl);
            if (!UrlRepository.isAlreadyExists(result)) {
                UrlRepository.save(result);
                ctx.sessionAttribute(FLASH_NAME, "Страница успешно добавлена");
            } else {
                ctx.sessionAttribute(FLASH_NAME, "Страница уже существует");
            }
        } catch (URISyntaxException | MalformedURLException e) {
            ctx.sessionAttribute(FLASH_NAME, "Некорректный URL");
        } catch (IllegalArgumentException e) {
            ctx.sessionAttribute(FLASH_NAME, e.getMessage());
        }
        ctx.redirect(NamedRoutes.urlsPath());
    }

    public static void index(Context ctx) throws SQLException {
        var urls = UrlRepository.getEntities();
        var page = new UrlsPage(urls);
        page.setFlash(ctx.consumeSessionAttribute(FLASH_NAME));
        ctx.render(NamedRoutes.urlsTemplate(), model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Integer.class).get();
        var url = UrlRepository.findById(id)
                .orElseThrow(() -> new NotFoundResponse("Url с id '" + id + "' не найден"));
        var page = new UrlPage(url);
        page.setFlash(ctx.consumeSessionAttribute(FLASH_NAME));
        var urlCheckPage = ctx.consumeSessionAttribute(URL_CHECK_PAGE);
        ctx.render(NamedRoutes.urlTemplate(), model("urlPage", page, "urlCheckPage", urlCheckPage));
    }
}
