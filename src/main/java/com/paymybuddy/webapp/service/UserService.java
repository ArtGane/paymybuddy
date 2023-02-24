package com.paymybuddy.webapp.service;

import com.paymybuddy.webapp.entity.User;
import com.paymybuddy.webapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public User getLoggedUser() {
        String email = null;
        SecurityContext context = SecurityContextHolder.getContext();
        Object user = context.getAuthentication().getPrincipal();
        if (user instanceof UserDetails) {
            email = ((UserDetails) user).getUsername();
        } else {
            email = user.toString();
        }

        return userRepository.findUserByEmail(email);
    }

    public User findUserByMail(String mail) {
        User user = userRepository.findUserByEmail(mail);
        if (user != null) {
            return user;
        }
        return null;
    }

    public User findUserById(Long id) {
        User user = userRepository.findUserById(id);
        if (user != null) {
            return user;
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user != null) {
            return new User(user);
        }
        throw new UsernameNotFoundException("User not found : " + email);
    }

    public void createUser(User user) {
        if (user != null) {
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

    public boolean isAnExistingMail(String mail) {
        return (userRepository.findUserByEmail(mail) != null);
    }

    public boolean addContactByEmail(Long userId, String contactEmail) {
        User user = userRepository.findUserById(userId);
        User contact = userRepository.findUserByEmail(contactEmail);
        List<User> contacts = user.getContact();

        if (isAnExistingMail(contactEmail)) {
            if (!user.equals(contact)) {
                if (!contacts.contains(contact.getId())) {
                    contacts.add(contact);
                    user.setContact(contacts);
                    userRepository.save(user);
                    return true;
                }
                throw new UsernameNotFoundException("You are already connected with this user");
            }
            throw new UsernameNotFoundException("You can't add yourself");
        }
        throw new UsernameNotFoundException("Your buddy email isn't recognize in your data base");
    }

    public boolean deleteContact(Long userId, Long contactId) {

        User currentUser = getLoggedUser();
        User contactUser = userRepository.findUserById(contactId);

        List<User> contacts = currentUser.getContact();

        if (contacts.contains(contactUser)) {
            contacts.remove(contactUser);
            currentUser.setContact(contacts);
            userRepository.save(currentUser);
            log.info("User {} delete {}", userId, contactId);
            return true;
        }

        return false;
    }

}
