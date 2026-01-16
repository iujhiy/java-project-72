package hexlet.code.service;

import hexlet.code.model.UrlCheck;
import hexlet.code.util.AssertionErrorPrivateConstructor;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;
import org.jsoup.Jsoup;

public final class UrlChecker {

    private UrlChecker() {
        AssertionErrorPrivateConstructor.throwAssertionError("service");
    }

    private static HttpResponse<String> getResponse(String url) {
        return Unirest.get(url).requestTimeout(5000).asString();
    }

    public static int getStatusCode(String url) {
        try {
            var response = getResponse(url);
            return response.getStatus();
        } catch (Exception e) {
            return -1;
        }
    }

    public static UrlCheck startSEOAnalysis(String url) {
        var result = new UrlCheck();
        String h1 = "";
        var description = new StringBuilder();
        var response = getResponse(url).getBody();
        var html = Jsoup.parse(response);
        var title = html.title();
        var h1Element = html.selectFirst("h1");
        if (h1Element != null) {
            h1 = h1Element.text();
        }
        var descriptionElement = html.selectFirst("meta[name=description]");
        if (descriptionElement != null) {
            var content = descriptionElement.attr("content");
            description.append(content);
        }
        result.setTitle(title);
        result.setH1(h1);
        result.setDescription(description);
        return result;
    }
}
