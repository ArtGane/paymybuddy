package com.paymybuddy.webapp.service;

import com.paymybuddy.webapp.entity.CreditCard;
import com.paymybuddy.webapp.entity.User;
import com.paymybuddy.webapp.dto.CreditCardDto;
import com.paymybuddy.webapp.repository.CreditCardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
public class CreditCardService {

    @Autowired
    private CreditCardRepository creditCardRpository;

    @Autowired
    private UserService userService;

    public boolean checkIfUserCreditCardExists(Long userId) {
        return (creditCardRpository.findByUserId(userId) != null);
    }

    public CreditCard getCreditCardInformations(Long userId) {
        if (checkIfUserCreditCardExists(userId)) {
            return creditCardRpository.findByUserId(userId);
        } else {
            return null;
        }
    }

    public boolean saveCreditCardInformation(CreditCard creditCard) {
        if (creditCard != null) {
            creditCardRpository.save(creditCard);
            log.info(creditCard.getId() + " bien enregistré dans la base de données");
        }
        log.info("Son détenteur est " + creditCard.getUser().getEmail());
        return true;
    }

    public boolean updateCreditCardInformation(Long userId, CreditCardDto creditCardDto) {
        if (checkIfUserCreditCardExists(userId)) {
            CreditCard creditCard = creditCardRpository.findByUserId(userId);
            creditCard.setCardNumbers(creditCardDto.getCardNumbers());
            creditCard.setCryptogram(creditCardDto.getCryptogram());
            creditCard.setEndValidity(creditCardDto.getEndValidity());

            creditCardRpository.save(creditCard);
            log.info("Carte bien enregistrée dans la base de données");
        }
        return true;
    }

    public boolean withdrawMoneyAndUpdateBalance(Long userId, double amountToAdd) {
        User user = userService.findUserById(userId);
        if (user != null) { // Check if user is correct
            double balance = user.getBalance();
            double totalBalance = balance + amountToAdd;
            creditCardRpository.updateBalance(userId, totalBalance); //Save in db
            return true;
        }
        throw new UsernameNotFoundException("User was not found");
    }

    public boolean depositMoneyAndUpdateBalance(Long userId, double amountToDeposit) throws Exception {
        User user = userService.findUserById(userId);
        if (user != null) {
            double balance = user.getBalance();
            if (amountToDeposit <= balance) {
                double totalBalance = balance - amountToDeposit;
                creditCardRpository.updateBalance(userId, totalBalance);
                return true;
            }
            throw new Exception("Amount to deposit is greater than your current account balance");
        }
        throw new UsernameNotFoundException("User was not found");//Add some exception if something wrong
    }

    public boolean deleteCreditCard(Long userId) {
        if (checkIfUserCreditCardExists(userId)) {
            creditCardRpository.deleteCreditCardByUserId(userId);
        }
        log.info("La carte a été supprimée");
        return true;
    }
}
