package com.caiolima.bcontrol.exception;

public class DuplicateUsername extends RuntimeException {

    public DuplicateUsername() {
        super("Username já está sendo usado");
    }
}
