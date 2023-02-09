package com.paymybuddy.webapp.service;

import com.paymybuddy.webapp.repository.CreditCardRpository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CreditCardService {

    @Autowired
    private CreditCardRpository creditCardRpository;

    @Autowired
    private UserService userService;
}
