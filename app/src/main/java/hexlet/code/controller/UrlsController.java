package hexlet.code.controller;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import hexlet.code.util.URLStringUtils;
import io.javalin.http.Context;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;


public class UrlsController {
    public static void create(Context ctx) {
        var urlString = ctx.formParam("url");
        try {
            if (urlString == null || urlString.trim().isEmpty()) {
                throw new IllegalArgumentException("URL не может быть пустым");
            }
            URI uri = new URI(urlString);
            URL url = uri.toURL();
            String clearUrl = URLStringUtils.makeClearURL(url);
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
        catch (SQLException e) {
            ctx.sessionAttribute("flash", "Ошибка сохранения в базу данных");
        }
        ctx.redirect(NamedRoutes.urlsPath());
    }
}
