package io.github.eternalstone.common.toolkit.security.mac;

public class MacException extends RuntimeException {
    public MacException() {
        super();
    }

    public MacException(String message) {
        super(message);
    }

    public MacException(String message, Throwable cause) {
        super(message, cause);
    }

    public MacException(Throwable cause) {
        super(cause);
    }
}
