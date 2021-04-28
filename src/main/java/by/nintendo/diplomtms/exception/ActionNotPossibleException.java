package by.nintendo.diplomtms.exception;

public class ActionNotPossibleException extends RuntimeException{
    public ActionNotPossibleException() {
        super();
    }

    public ActionNotPossibleException(String message) {
        super(message);
    }

    public ActionNotPossibleException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActionNotPossibleException(Throwable cause) {
        super(cause);
    }

    protected ActionNotPossibleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
