package hexlet.code.controller;

import io.javalin.http.Context;

public class UrlsController {
    public static void create(Context ctx) {
        var url = ctx.formParam("url");
        if (url == null || url.isEmpty()) {
            throw new NullPointerException("Url is empty");
        }

    }
}
