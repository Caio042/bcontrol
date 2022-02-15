package com.caiolima.bcontrol.exception;

public class DuplicateUsernameException extends RuntimeException {

    public DuplicateUsernameException() {
        super("Username já está sendo usado");
    }
}
