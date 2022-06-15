package com.caiolima.bcontrol.controller;

import com.caiolima.bcontrol.controller.dto.request.LoginRequest;
import com.caiolima.bcontrol.controller.dto.response.TokenDTO;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin(allowedHeaders = "*")
public class LoginController {

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<TokenDTO> login(@RequestBody LoginRequest request) {
        throw new NotImplementedException();
    }
}
