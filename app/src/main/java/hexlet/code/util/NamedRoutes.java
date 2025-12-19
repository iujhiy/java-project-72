package hexlet.code.util;

import javax.naming.Name;

public final class NamedRoutes {

    private NamedRoutes() {
        throw new AssertionError("Cannot instantiate utility class");
    }

    public static String urlsPath() {
        return "/urls";
    }

}
