package by.nintendo.diplomtms.exception;

public class AuthenticationExeption extends RuntimeException{
    public AuthenticationExeption() {
        super();
    }

    public AuthenticationExeption(String message) {
        super(message);
    }

    public AuthenticationExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationExeption(Throwable cause) {
        super(cause);
    }

    protected AuthenticationExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
