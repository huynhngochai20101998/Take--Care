package com.tamaumi.takecare.converter;

import com.tamaumi.takecare.entity.User;
import com.tamaumi.takecare.model.request.AuthRequest;

public class AuthConverter {

    public static User toEntity(AuthRequest request) {
        User user = new User();

        user.setUsername(request.getUsername());

        return user;
    }
}
