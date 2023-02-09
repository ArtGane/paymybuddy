package com.paymybuddy.webapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistration {

    private String pseudo;
    private String email;
    private String password;
    private String passwordConfirmation;

}
