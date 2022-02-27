package com.tamaumi.takecare.controller;

import com.tamaumi.takecare.common.ResponseSetting;
import com.tamaumi.takecare.entity.CustomUserDetails;
import com.tamaumi.takecare.exception.RequestFailedException;
import com.tamaumi.takecare.model.request.AuthRequest;
import com.tamaumi.takecare.model.response.LoginResponse;
import com.tamaumi.takecare.security.JwtTokenProvider;
import com.tamaumi.takecare.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.tamaumi.takecare.common.enums.ResponseDefinition.REQUEST_FAILED;
import static com.tamaumi.takecare.common.enums.ResponseDefinition.SUCCESS;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final AuthService authService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.authService = authService;
    }

    @PostMapping("/sign-in")
    public LoginResponse authenticateUser(@Valid @RequestBody AuthRequest authRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateJWT((CustomUserDetails) authentication.getPrincipal());

        return new LoginResponse(jwt);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Object> signUp(@Valid @RequestBody AuthRequest request) {
        if (authService.signUp(request)) {

            return ResponseSetting.response(
                    SUCCESS.getStatusCode(),
                    SUCCESS.getMessage()
            );
        }

        throw new RequestFailedException(REQUEST_FAILED.getMessage(), REQUEST_FAILED.getDevCode());
    }
}
