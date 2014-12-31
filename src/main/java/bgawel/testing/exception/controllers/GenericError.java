package bgawel.testing.exception.controllers;

class GenericError {

    private final String url;
    private final String message;

    public GenericError(final String url, final Exception ex) {
        this.url = url;
        this.message = ex.getLocalizedMessage();
    }

    public String getUrl() {
        return url;
    }

    public String getMessage() {
        return message;
    }
}
