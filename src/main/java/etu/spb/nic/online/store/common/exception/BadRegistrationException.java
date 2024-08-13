package etu.spb.nic.online.store.common.exception;

public class BadRegistrationException extends RuntimeException {

    public BadRegistrationException() {
        super();
    }

    public BadRegistrationException(String message) {
        super(message);
    }

    public BadRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
