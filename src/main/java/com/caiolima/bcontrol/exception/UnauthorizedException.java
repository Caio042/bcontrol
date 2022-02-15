package com.caiolima.bcontrol.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("Registro solicitado pertence a outro usuário");
    }
}
