package com.catmanscodes.todoapis.controller;

import com.catmanscodes.todoapis.dto.LoginUserDto;
import com.catmanscodes.todoapis.dto.RegisterUserDto;
import com.catmanscodes.todoapis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterUserDto registerUserDto) {

        userService.registerNewUser(registerUserDto);

        return ResponseEntity.ok("Register User");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginUserDto loginUserDto) {
        userService.loginUser(loginUserDto);

        return ResponseEntity.ok("Login User");
    }

}
