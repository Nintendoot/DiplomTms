package by.nintendo.diplomtms.exception;

public class UserWasNotFoundException extends RuntimeException{
    public UserWasNotFoundException() {
        super();
    }

    public UserWasNotFoundException(String message) {
        super(message);
    }

    public UserWasNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserWasNotFoundException(Throwable cause) {
        super(cause);
    }

    protected UserWasNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
