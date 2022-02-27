package com.tamaumi.takecare.service;

import com.tamaumi.takecare.model.request.AuthRequest;

public interface AuthService {
    boolean signUp(AuthRequest authRequest);
}
