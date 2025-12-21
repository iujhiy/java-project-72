package hexlet.code.controller;

import hexlet.code.dto.urls.URLPage;
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

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlsController {
    public static void create(Context ctx) {
        var urlString = ctx.formParam("url");
        try {
            if (urlString == null || urlString.trim().isEmpty()) {
                throw new NullPointerException("URL не может быть пустым");
            }
            URI uri = new URI(urlString);
            URL url = uri.toURL();
            String clearUrl = URLStringUtils.makeClearURL(url);
            var result = new Url(clearUrl);
            var page = new URLPage(result);
            if (!UrlRepository.isAlreadyExists(result)) {
                UrlRepository.save(result);
                page.setFlash(ctx.consumeSessionAttribute("Страница успешно добавлена"));
            } else {
                page.setFlash(ctx.consumeSessionAttribute("Страница уже существует"));
            }
            ctx.render(NamedRoutes.urlsPath(), model("page", page));
        }
        catch (URISyntaxException e) {

            throw new RuntimeException(e); // бросить флеш сообщение
        }
        catch (NullPointerException e) {

        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
