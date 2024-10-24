package com.jeido.openfridgerecipe.service;

import com.jeido.openfridgerecipe.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService {

    List<User> getAllUsers();
    User getUserById(UUID id);
    User createUser(User user);
    User updateUser(UUID id, User userDetails);
    void deleteUser(UUID id);
    User getUserByEmail(String email);
}
