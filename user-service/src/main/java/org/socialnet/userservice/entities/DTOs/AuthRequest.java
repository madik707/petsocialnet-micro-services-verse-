package org.socialnet.userservice.entities.DTOs;

import lombok.Getter;

@Getter
public class AuthRequest {
    private String username;
    private String password;
}

