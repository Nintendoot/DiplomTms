package by.nintendo.diplomtms.exception;

public class ProjectNotFountException extends RuntimeException{
    public ProjectNotFountException() {
        super();
    }

    public ProjectNotFountException(String message) {
        super(message);
    }

    public ProjectNotFountException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProjectNotFountException(Throwable cause) {
        super(cause);
    }

    protected ProjectNotFountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
