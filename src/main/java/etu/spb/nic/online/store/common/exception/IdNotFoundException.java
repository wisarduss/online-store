package etu.spb.nic.online.store.common.exception;

public class IdNotFoundException extends RuntimeException {
    public IdNotFoundException() {
        super();
    }

    public IdNotFoundException(String message) {
        super(message);
    }

    public IdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
