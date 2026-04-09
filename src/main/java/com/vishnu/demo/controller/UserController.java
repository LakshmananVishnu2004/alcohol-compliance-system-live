package com.vishnu.demo.controller;

import com.vishnu.demo.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.vishnu.demo.model.User;
import com.vishnu.demo.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ApiResponse<User> register(@RequestBody User user) {
        return new ApiResponse<>(
                "SUCCESS",
                "User registered successfully",
                userService.registerUser(user)
        );
    }
}