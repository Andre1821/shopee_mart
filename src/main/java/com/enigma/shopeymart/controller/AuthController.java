package com.enigma.shopeymart.controller;

import com.enigma.shopeymart.constant.AppPath;
import com.enigma.shopeymart.dto.request.AuthRequest;
import com.enigma.shopeymart.dto.response.CommonResponseAuth;
import com.enigma.shopeymart.dto.response.LoginResponse;
import com.enigma.shopeymart.dto.response.RegisterResponse;
import com.enigma.shopeymart.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.AUTH)
public class AuthController {
    private final AuthService authService;

    @PostMapping
    public RegisterResponse registerCustomer(@RequestBody AuthRequest authRequest){
        return authService.registerCustomer(authRequest);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> registerCustomerComn(@RequestBody AuthRequest authRequest){
        RegisterResponse registerResponse = authService.registerCustomer(authRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponseAuth.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully register new acount")
                        .data(registerResponse)
                        .build());
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest){
        LoginResponse loginResponse = authService.loginresponse(authRequest);

        return  ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponseAuth.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully Login")
                        .data(loginResponse)
                        .build());
    }
}
