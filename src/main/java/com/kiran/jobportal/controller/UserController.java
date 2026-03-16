package com.kiran.jobportal.controller;

import com.kiran.jobportal.model.User;
import com.kiran.jobportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    /* ---------------- REGISTER USER ---------------- */

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {

        try {

            // Check if email already exists
            User existingUser = userRepository.findByEmail(user.getEmail());

            if (existingUser != null) {
                return "Email already registered!";
            }

            userRepository.save(user);

            return "User registered successfully!";

        } catch (Exception e) {

            return "Registration failed!";
        }
    }


    /* ---------------- LOGIN USER ---------------- */

    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {

        try {

            User existingUser = userRepository.findByEmail(user.getEmail());

            if (existingUser == null) {
                return "User not found!";
            }

            if (!existingUser.getPassword().equals(user.getPassword())) {
                return "Invalid password!";
            }

            return "Login successful!";

        } catch (Exception e) {

            return "Login failed!";
        }
    }


    /* ---------------- GET ALL USERS ---------------- */

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    /* ---------------- GET USER BY ID ---------------- */

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }


    /* ---------------- DELETE USER ---------------- */

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {

        if (!userRepository.existsById(id)) {
            return "User not found!";
        }

        userRepository.deleteById(id);

        return "User deleted successfully!";
    }
}