package hexlet.code.util;

public final class AssertionErrorPrivateConstructor {
    private AssertionErrorPrivateConstructor() {
        throwAssertionError("util");
    }

    public static void throwAssertionError(String classUseName) {
        throw new AssertionError("Cannot instantiate " + classUseName + " class");
    }
}
