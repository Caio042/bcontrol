package com.caiolima.bcontrol.exception;

public class RegistroDuplicadoException extends RuntimeException {

    public RegistroDuplicadoException() {
        super("Registro já feito no mesmo mês, com a mesma descrição");
    }
}
