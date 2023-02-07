package com.paymybuddy.webapp.service;

import com.paymybuddy.webapp.entity.User;
import com.paymybuddy.webapp.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Data
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void createUser(User user) {
        if (user != null) {
            String pseudo = user.getPseudo();
            String email = user.getEmail();
            String password = user.getPassword();

            // Every password will be crypt because of security
            String cryptPass = new BCryptPasswordEncoder().encode(password);
            user.setPassword(cryptPass);

            userRepository.save(user);
        }
    }
}
