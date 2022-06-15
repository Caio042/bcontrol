package com.caiolima.bcontrol.exception;

import com.caiolima.bcontrol.controller.dto.response.ResponseMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<ResponseMessage> notFoundHandler(NotFoundException exception) {
        return new ResponseEntity<>(new ResponseMessage(HttpStatus.NOT_FOUND.value(), exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ResponseEntity<ResponseMessage> registroDuplicadoHandler(RegistroDuplicadoException exception) {
        return new ResponseEntity<>(new ResponseMessage(HttpStatus.UNPROCESSABLE_ENTITY.value(), exception.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ResponseEntity<ResponseMessage> unauthorizedHandler(UnauthorizedException exception) {
        return new ResponseEntity<>(new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), exception.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ResponseEntity<ResponseMessage> invalidTokenHander(InvalidTokenException exception) {
        return new ResponseEntity<>(new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), exception.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ResponseEntity<ResponseMessage> duplicateUsernameHandler(DuplicateUsernameException exception) {
        return new ResponseEntity<>(new ResponseMessage(HttpStatus.UNPROCESSABLE_ENTITY.value(), exception.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        String mensagem = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return new ResponseEntity<>(new ResponseMessage(status.value(), mensagem), status);
    }
}
