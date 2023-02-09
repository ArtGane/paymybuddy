package com.paymybuddy.webapp.controller;

import com.paymybuddy.webapp.entity.User;
import com.paymybuddy.webapp.model.UserRegistration;
import com.paymybuddy.webapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;


    @GetMapping("/register")
    public ModelAndView showRegistrationFrontend() {
        Map<String, Object> model = new HashMap<>();
        model.put("userRegistration", new UserRegistration());

        return new ModelAndView("register", model);
    }

    @PostMapping("/register")
    public ModelAndView submitRegistrationFrontend(@Valid UserRegistration userRegistration,
                                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("register");
        }

        // Creating the user if every fields from userRegistration have been validated
        User user = new User();
        user.setPseudo(userRegistration.getPseudo());
        user.setEmail(userRegistration.getEmail());

        //TODO : rajouter if pour user password confirmation
        user.setPassword(userRegistration.getPassword());

        userService.createUser(user);

        RedirectView redirect = new RedirectView();
        redirect.setUrl("/");
        return new ModelAndView(redirect);
    }
}
