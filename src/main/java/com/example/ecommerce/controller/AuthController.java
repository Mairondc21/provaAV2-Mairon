package com.example.ecommerce.controller;


import com.example.ecommerce.model.User;
import com.example.ecommerce.service.AuthService;
import com.example.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        String response = userService.registerUser(user);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        String token = authService.authenticateUser(username, password);
        if (token != null) {
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @GetMapping("/role/{token}")
    public ResponseEntity<String> extractRole(@PathVariable String token) {
        String role = authService.extractRole(token);
        if (role != null) {
            System.out.println("Role extraída: " + role); // Log para verificar a role
            return ResponseEntity.ok(role);
        } else {
            System.err.println("Role não encontrada para o token: " + token); // Log de erro
            return ResponseEntity.status(400).body("Role not found");
        }
    }
}
