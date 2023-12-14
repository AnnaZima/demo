package net.annakat.demo.exception;

public class AuthenticationException extends ApiException{
    public AuthenticationException(String message, String errorCode) {
        super(message, errorCode);
    }
}
