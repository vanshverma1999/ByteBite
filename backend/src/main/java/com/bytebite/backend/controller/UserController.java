package com.bytebite.backend.controller;

import com.bytebite.backend.model.User;
import com.bytebite.backend.request.CreateUserRequest;
import com.bytebite.backend.request.LoginUserRequest;
import com.bytebite.backend.response.AuthResponse;
import com.bytebite.backend.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final IUserService userService;
    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody CreateUserRequest userRequest){
        return ResponseEntity.status(201).body(userService.signUp(userRequest));
    }
    @GetMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginUserRequest loginUserRequest){
        return ResponseEntity.ok(userService.login(loginUserRequest));
    }
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
