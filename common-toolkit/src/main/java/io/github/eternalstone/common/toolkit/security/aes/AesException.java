package io.github.eternalstone.common.toolkit.security.aes;


public class AesException extends RuntimeException {
    public AesException() {
        super();
    }

    public AesException(String message) {
        super(message);
    }

    public AesException(String message, Throwable cause) {
        super(message, cause);
    }

    public AesException(Throwable cause) {
        super(cause);
    }
}