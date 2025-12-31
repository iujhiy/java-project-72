package hexlet.code.util;

import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;

public class UrlChecker {

    public static int getStatusCode(String url) {
        try {
            HttpResponse<String> response = Unirest.get(url).asString();
            return response.getStatus();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
