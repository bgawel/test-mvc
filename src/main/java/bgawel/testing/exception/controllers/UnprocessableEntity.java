package bgawel.testing.exception.controllers;

public class UnprocessableEntity {

    private final String field;
    private final String message;

    public UnprocessableEntity(final String field, final String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
