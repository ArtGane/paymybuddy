package com.paymybuddy.webapp.controller;

import com.paymybuddy.webapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class RegisterController {

    @Autowired
    private UserService userService;


    @GetMapping("/register")
    public String users(String model) {
        return "register";
    }
}
