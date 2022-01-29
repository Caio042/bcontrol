package com.caiolima.bcontrol.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("Registro não encontrado");
    }
}
