package etu.spb.nic.online.store.common.exception;

public class NotOwnerException extends RuntimeException {

    public NotOwnerException() {
        super();
    }

    public NotOwnerException(String message) {
        super(message);
    }

    public NotOwnerException(String message, Throwable cause) {
        super(message, cause);
    }
}
