package hexlet.code.util;

public record ErrorResponse(String error, String message) {
    @Override
    public String toString() {
        return String.format("[%s] %s", error, message);
    }
}
