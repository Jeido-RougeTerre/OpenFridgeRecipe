package com.jeido.openfridgerecipe.controllers;

import com.jeido.openfridgerecipe.dto.UserDtoReceive;
import com.jeido.openfridgerecipe.dto.UserDtoRegister;
import com.jeido.openfridgerecipe.dto.UserDtoSend;
import com.jeido.openfridgerecipe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")  // Pour Angular
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDtoSend> inscriptionForm(@RequestBody UserDtoRegister user) {


        return  ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }


    @GetMapping
    public ResponseEntity<List<UserDtoSend>> getAllUsers() {
        List<UserDtoSend> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDtoSend> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserDtoSend> updateUser(@PathVariable UUID id, @RequestBody UserDtoReceive userDetails) {
        return ResponseEntity.ok(userService.updateUser(id, userDetails));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
