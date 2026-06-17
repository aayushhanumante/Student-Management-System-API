package com.student.student_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/me")
    public String getLoggedInUser(Principal principal) {
        if (principal == null) {
            return "No user logged in";
        }
        return "Logged in as: " + principal.getName();
    }
}