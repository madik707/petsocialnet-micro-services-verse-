package org.socialnet.userservice.controllers;

import lombok.RequiredArgsConstructor;
import org.socialnet.userservice.config.JWTUtill;
import org.socialnet.userservice.entities.DTOs.AuthRequest;
import org.socialnet.userservice.entities.DTOs.UserDTO;
import org.socialnet.userservice.entities.User;
import org.socialnet.userservice.services.impls.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @DeleteMapping("delete")
    public ResponseEntity<User> deleteUser(@RequestParam Long id){

        Optional<User> userToDelete = userServiceImpl.getUserById(id);

        if (userToDelete.isPresent()){
            userServiceImpl.deleteUser(id);
            return ResponseEntity.ok(userToDelete.get());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userToDelete.get());
    }

    @PatchMapping("update")
    public ResponseEntity<User> updateUser(@RequestBody User user,
                                           @RequestParam Long id) {
        Optional<User> userToUpdate = userServiceImpl.getUserById(id);
        if (userToUpdate.isPresent()) {
            User loggedUser = userToUpdate.get();

            if (user.getUsername() != null) {
                loggedUser.setUsername(user.getUsername());
            }
            if (user.getEmail() != null) {
                loggedUser.setEmail(user.getEmail());
            }
            if (user.getPassword() != null) {
                loggedUser.setPassword(user.getPassword());
            }
            if (user.getFirstName() != null) {
                loggedUser.setFirstName(user.getFirstName());
            }
            if (user.getLastName() != null) {
                loggedUser.setLastName(user.getLastName());
            }

            User updatedUser = userServiceImpl.updateUser(id, loggedUser);
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(user);
    }


    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userServiceImpl.getAllUsers();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id) {
        Optional<User> user = userServiceImpl.getUserById(id);
        if (user.isPresent()){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
    }
}