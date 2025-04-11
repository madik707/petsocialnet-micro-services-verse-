package org.socialnet.userservice.controllers;

import lombok.AllArgsConstructor;
import org.socialnet.userservice.config.JWTUtill;
import org.socialnet.userservice.entities.DTOs.AuthRequest;
import org.socialnet.userservice.entities.DTOs.UserDTO;
import org.socialnet.userservice.entities.User;
import org.socialnet.userservice.services.impls.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/auth")

public class AuthController {
    private final UserServiceImpl userServiceImpl;

    private final JWTUtill jwtUtill;

    private final PasswordEncoder passwordEncoder;

    @PostMapping(value = "/reg")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO user){


        if (userServiceImpl.existsByEmail(user.getEmail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("error", "You already have an account.").body(user);
        }

        if (userServiceImpl.existsByUsername(user.getUsername())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("error", "You already have an account.").body(user);
        }

        User createdUser = userServiceImpl.createUser(user);

        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/log")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        Optional<User> userToLog = userServiceImpl.getUserByUsername(request.getUsername());

        if (userToLog.isPresent()){
            if (passwordEncoder.matches(request.getPassword(), userToLog.get().getPassword())){
                String token = jwtUtill.generateToken(userToLog.get().getUsername());
                return ResponseEntity.ok(token);
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(request);
    }
}
