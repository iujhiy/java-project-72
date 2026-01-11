package hexlet.code.util;

import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;
import org.jsoup.Jsoup;

import java.util.HashMap;
import java.util.Map;

public class UrlChecker {

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

    public static Map<String, String> startSEOAnalysis(String url) {
        var resultMap = new HashMap<String, String>();
        String h1 = "";
        String description = "";
        var response = getResponse(url).getBody();
        var html = Jsoup.parse(response);
        var title = html.title();
        var h1Element = html.selectFirst("h1");
        if (h1Element != null) {
            h1 = h1Element.text();
        }
        var descriptionElement = html.selectFirst("meta[name=description]");
        if (descriptionElement != null) {
            description = descriptionElement.attr("content");
        }
        resultMap.put("title", title);
        resultMap.put("h1", h1);
        resultMap.put("description", description);
        return resultMap;
    }
}
