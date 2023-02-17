package com.paymybuddy.webapp.controller;

import com.paymybuddy.webapp.dto.UserLogin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    @GetMapping("/login")
    public ModelAndView showLoginForm() {

        Map<String, Object> model = new HashMap<>();
        model.put("userLogin", new UserLogin());

        return new ModelAndView("login", model);
    }
}
