package com.caiolima.bcontrol.exception;

import com.caiolima.bcontrol.controller.dto.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<ResponseMessage> notFoundHandler(NotFoundException exception) {
        return new ResponseEntity<>(new ResponseMessage(HttpStatus.NOT_FOUND.value(), exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<ResponseMessage> registroDuplicadoHandler(RegistroDuplicadoException exception) {
        return new ResponseEntity<>(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
