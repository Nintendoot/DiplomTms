package by.nintendo.diplomtms.exception;

public class NotEnoughRightsException extends RuntimeException{
    public NotEnoughRightsException() {
        super();
    }

    public NotEnoughRightsException(String message) {
        super(message);
    }

    public NotEnoughRightsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughRightsException(Throwable cause) {
        super(cause);
    }

    protected NotEnoughRightsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
