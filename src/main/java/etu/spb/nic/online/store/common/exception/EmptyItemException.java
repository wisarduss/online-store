package etu.spb.nic.online.store.common.exception;

public class EmptyItemException extends RuntimeException {

    public EmptyItemException() {
        super();
    }

    public EmptyItemException(String message) {
        super(message);
    }

    public EmptyItemException(String message, Throwable cause) {
        super(message, cause);
    }
}
