package bgawel.testing.fruit.usecases;

public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 1246173813499824282L;

    private final String field;
    private final String prettyError;

    public ValidationException(final String field, final String prettyError) {
        super(prettyError);
        this.field = field;
        this.prettyError = prettyError;
    }

    public String getField() {
        return field;
    }

    public String getPrettyError() {
        return prettyError;
    }
}
