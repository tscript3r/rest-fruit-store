package pl.tscript3r.fruitstore.exceptions;

public class ResourceExistsException extends RuntimeException {

    public ResourceExistsException() {
    }

    public ResourceExistsException(String s) {
        super(s);
    }

    public ResourceExistsException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ResourceExistsException(Throwable throwable) {
        super(throwable);
    }

    public ResourceExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
