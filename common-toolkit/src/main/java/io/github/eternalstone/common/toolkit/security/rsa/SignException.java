package io.github.eternalstone.common.toolkit.security.rsa;

public class SignException extends RuntimeException {
    public SignException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
