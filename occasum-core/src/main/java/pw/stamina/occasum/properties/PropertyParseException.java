package pw.stamina.occasum.properties;

public final class PropertyParseException extends Exception {
    private final String input;

    public PropertyParseException(String message, String input) {
        super(message);
        this.input = input;
    }

    public PropertyParseException(String message, Throwable cause, String input) {
        super(message, cause);
        this.input = input;
    }

    public PropertyParseException(Throwable cause, String input) {
        super(cause);
        this.input = input;
    }

    public static PropertyParseException nullInput() {
        return new PropertyParseException("input is null", null);
    }

    public String getInput() {
        return input;
    }
}
