package com.caiolima.bcontrol.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("Não autenticado");
    }

    public InvalidTokenException(String message) {
        super(message);
    }
}
