package com.caiolima.bcontrol.controller.dto;


public record TokenResponse(String accessToken,
                           String refreshToken) {
}
