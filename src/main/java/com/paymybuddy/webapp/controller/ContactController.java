package com.paymybuddy.webapp.controller;

import com.paymybuddy.webapp.dto.ContactDto;
import com.paymybuddy.webapp.entity.User;
import com.paymybuddy.webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ContactController {

    @Autowired
    private UserService userService;


    @GetMapping("/contact")
    public ModelAndView showContactPage(@RequestParam(required = false) String action,
                                            @RequestParam(required = false) Long contactId) {

        User user = userService.getLoggedUser();

        List<User> contact = user.getContact();

        Map<String, Object> model = new HashMap<>();
        model.put("contactList", contact);
        model.put("contactDto", new ContactDto());
        model.put("pseudo", user.getPseudo());
        model.put("balance", user.getBalance());
        model.put("hasContact", !contact.isEmpty());

        if ((action != null && contactId != null) && action.equals("delete")) {
            boolean deleteContact = userService.deleteContact(user.getId(), contactId);
            RedirectView redirect = new RedirectView();
            if (deleteContact) {
                redirect.setUrl("contact" + "?delete_contact_success");
            } else {
                redirect.setUrl("contact" + "?delete_contact_error");
            }
            return new ModelAndView(redirect, model);
        }

        return new ModelAndView("contact", model);
    }

    @PostMapping("/contact")
    public ModelAndView addContactForm(@Valid ContactDto contactDto, BindingResult bindingResult) {

        User user = userService.getLoggedUser();
        Map<String, Object> model = new HashMap<>();
        List<User> contacts = user.getContact();

        model.put("contactList", contacts);
        model.put("pseudo", user.getPseudo());
        model.put("balance", user.getBalance());
        model.put("hasContact", !contacts.isEmpty());

        if (bindingResult.hasErrors()) {
            return new ModelAndView("contact", model);
        }

        RedirectView redirect = new RedirectView();
        try {

            boolean contactOk = userService.addContactByEmail(user.getId(), contactDto.getContactEmail());
            if (contactOk) {
                redirect.setUrl("contact" + "?success");
                return new ModelAndView(redirect, new HashMap<>());
            }

        } catch (UsernameNotFoundException error) {
            bindingResult.rejectValue("contactEmail", "", error.getMessage());
            return new ModelAndView("contact", model);
        }

        return new ModelAndView("contact", model);

    }
}
