package hexlet.code.util;

public final class NamedRoutes {

    private NamedRoutes() {
        AssertionErrorPrivateConstructor.throwAssertionError("util");
    }

    private static final String URLS_PATH = "urls";
    private static final String CHECKS_PATH = "checks";
    private static final String INDEX_NAME = "index.jte";
    private static final String SHOW_NAME = "show.jte";


    public static String urlsPath() {
        return "/" + URLS_PATH;
    }

    public static String urlsTemplate() {
        return URLS_PATH + "/" + INDEX_NAME;
    }

    public static String urlTemplate() {
        return URLS_PATH + "/" + SHOW_NAME;
    }

    public static String urlPath(int id) {
        return urlPath(String.valueOf(id));
    }

    public static String urlPath(String id) {
        return "/" + URLS_PATH + "/" + id;
    }

    public static String urlChecksPath(int urlId) {
        return urlChecksPath(String.valueOf(urlId));
    }

    public static String urlChecksPath(String urlId) {
        return "/" + URLS_PATH + "/" + urlId + "/" + CHECKS_PATH;
    }
}
