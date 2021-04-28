package by.nintendo.diplomtms.exception;

public class TitleAlreadyExistsException extends RuntimeException{
    public TitleAlreadyExistsException() {
        super();
    }

    public TitleAlreadyExistsException(String message) {
        super(message);
    }

    public TitleAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TitleAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    protected TitleAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
