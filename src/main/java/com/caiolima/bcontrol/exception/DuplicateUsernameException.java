package com.caiolima.bcontrol.exception;

public class DuplicateUsernameException extends RuntimeException {

    public DuplicateUsernameException() {
        super("Email já cadastrado");
    }
}
