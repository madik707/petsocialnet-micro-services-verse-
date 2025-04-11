package org.socialnet.userservice.services;

import org.socialnet.userservice.entities.User;
import org.socialnet.userservice.entities.DTOs.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(UserDTO user);
    Optional<User> getUserById(Long id);
    Optional<User> getUserByUsername(String username);
    List<User> getAllUsers();
    User updateUser(Long id, User userDetails);
    void deleteUser(Long id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
