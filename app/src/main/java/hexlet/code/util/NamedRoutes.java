package hexlet.code.util;

public final class NamedRoutes {

    private NamedRoutes() {
        throw new AssertionError("Cannot instantiate utility class");
    }

    public static String urlsPath() {
        return "/urls";
    }

    public static String urlsTemplate() {
        return "urls/index.jte";
    }

    public static String urlTemplate() {
        return "urls/show.jte";
    }

    public static String urlPath(int id) {
        return urlPath(String.valueOf(id));
    }

    public static String urlPath(String id) {
        return "/urls/" + id;
    }
}
