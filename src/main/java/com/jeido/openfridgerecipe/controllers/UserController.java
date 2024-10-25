package com.jeido.openfridgerecipe.controllers;

import com.jeido.openfridgerecipe.entity.User;
import com.jeido.openfridgerecipe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")  // Pour Angular
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping("/inscription")
    public String inscription(Model model) {
        model.addAttribute("user", User.builder().name("").email("").password("").build());
        model.addAttribute("mode", "inscription");
        return "/login/form";
    }


    @PostMapping("/inscription")
    public String inscriptionForm(@Valid @ModelAttribute("user") User user,
                                  BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("mode", "inscription");
            return "/login/form";
        }

        userService.createUser(user);
        return "redirect:/";
    }


    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }


    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }


    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
