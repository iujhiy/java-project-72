package hexlet.code.controller;

import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import hexlet.code.util.UrlStringUtils;
import io.javalin.http.Context;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;

import static io.javalin.rendering.template.TemplateUtil.model;


public class UrlsController {
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
                ctx.sessionAttribute("flash", "Страница успешно добавлена");
            } else {
                ctx.sessionAttribute("flash", "Страница уже существует");
            }
        }
        catch (URISyntaxException | MalformedURLException e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
        }
        catch (IllegalArgumentException e) {
            ctx.sessionAttribute("flash", e.getMessage());
        }
        ctx.redirect(NamedRoutes.urlsPath());
    }

    public static void index(Context ctx) throws SQLException {
        var urls = UrlRepository.getEntities();
        var page = new UrlsPage(urls);
        ctx.render(NamedRoutes.urlsTemplate(), model("page", page));
    }

    public static void show(Context ctx) {

    }
}
