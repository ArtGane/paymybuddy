package com.paymybuddy.webapp.controller;

import com.paymybuddy.webapp.entity.CreditCard;
import com.paymybuddy.webapp.entity.User;
import com.paymybuddy.webapp.model.CreditCardDto;
import com.paymybuddy.webapp.model.UserRegistration;
import com.paymybuddy.webapp.service.CreditCardService;
import com.paymybuddy.webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CreditCardController {

    @Autowired
    private UserService userService;

    @Autowired
    private CreditCardService creditCardService;

    @GetMapping("/creditcard")
    public ModelAndView showCreditCard(@RequestParam(required = false) String action) {

        // Get current logged user
        User user = userService.getLoggedUser();
        Long userId = user.getId();

        Map<String, Object> model = new HashMap<>();


        return new ModelAndView("creditcard", model);
    }
}
