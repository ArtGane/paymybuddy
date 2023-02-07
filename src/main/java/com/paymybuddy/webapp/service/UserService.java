package com.paymybuddy.webapp.service;

import com.paymybuddy.webapp.entity.User;
import com.paymybuddy.webapp.repository.UserRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Data
@Slf4j
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
            log.info("User " + user.getPseudo() + " est bien enregistré dans la base de données");

        } else {
            log.error("User non enregistré dans la base de données");
        }
    }
}
