package etu.spb.nic.online.store.common.exception;

public class LassThenZeroException extends RuntimeException {
    public LassThenZeroException() {
        super();
    }

    public LassThenZeroException(String message) {
        super(message);
    }

    public LassThenZeroException(String message, Throwable cause) {
        super(message, cause);
    }
}
