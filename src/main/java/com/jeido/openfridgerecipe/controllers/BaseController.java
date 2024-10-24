package com.jeido.openfridgerecipe.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class BaseController {

    @GetMapping()
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("Fridge API Working");
    }
}
