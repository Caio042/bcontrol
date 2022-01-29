package com.caiolima.bcontrol.controller.dto;

import org.springframework.http.HttpStatus;

public record ResponseMessage(HttpStatus status,
                              String message) {
}
