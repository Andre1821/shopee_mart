package com.enigma.shopeymart.security;

import com.enigma.shopeymart.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthTokenFilter {
    private final JwtUtil jwtUtil;
    private final UserService userService;
}
