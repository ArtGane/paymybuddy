package com.paymybuddy.webapp.service;

import com.paymybuddy.webapp.entity.CreditCard;
import com.paymybuddy.webapp.model.CreditCardDto;
import com.paymybuddy.webapp.repository.CreditCardRpository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
public class CreditCardService {

    @Autowired
    private CreditCardRpository creditCardRpository;

    @Autowired
    private UserService userService;

    public boolean checkIfUserCreditCardExists(Long userId) {
        return (creditCardRpository.findByUserId(userId) != null);
    }

    public CreditCard getCreditCardInformatins(Long userId) {
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
            log.info(creditCard.getId() + " bien enregistré dans la base de données");
        }
        return true;
    }

    public boolean deleteCreditCard(Long userId) {
        if (checkIfUserCreditCardExists(userId)) {
            creditCardRpository.deleteCreditCardFromIdUser(userId);
        }
        log.info("La carte a été supprimée");
        return true;
    }
}
