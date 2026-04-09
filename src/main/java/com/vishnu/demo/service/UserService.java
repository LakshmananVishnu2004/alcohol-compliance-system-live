package com.vishnu.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vishnu.demo.model.User;
import com.vishnu.demo.repository.UserRepository;
import com.vishnu.demo.exception.UserAlreadyExistsException;
import com.vishnu.demo.exception.InvalidUserException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {

        // Email validation
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new InvalidUserException("Email is required");
        }

        // Age validation
        if (user.getAge() < 18) {
            throw new InvalidUserException("User must be above 18");
        }

        // Duplicate email check
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already registered");
        }

        return userRepository.save(user);
    }
}