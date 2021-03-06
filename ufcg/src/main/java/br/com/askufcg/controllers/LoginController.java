package br.com.askufcg.controllers;

import br.com.askufcg.dtos.auth.LoginRequest;
import br.com.askufcg.services.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/login")
@CrossOrigin
public class LoginController {
    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(authService.auth(loginRequest), HttpStatus.CREATED);
    }
}
