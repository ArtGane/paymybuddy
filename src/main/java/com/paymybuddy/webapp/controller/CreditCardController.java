package com.paymybuddy.webapp.controller;

import com.paymybuddy.webapp.entity.CreditCard;
import com.paymybuddy.webapp.entity.User;
import com.paymybuddy.webapp.dto.CreditCardDto;
import com.paymybuddy.webapp.dto.CreditCardDtoDeposit;
import com.paymybuddy.webapp.dto.CreditCardDtoWithdraw;
import com.paymybuddy.webapp.service.CreditCardService;
import com.paymybuddy.webapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CreditCardController {

    @Autowired
    private UserService userService;

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private ModelMapper modelMapper;

    private CreditCardDto getCreditCardForm(Long userId) {
        CreditCardDto creditCardDto;
        CreditCard creditCard = creditCardService.getCreditCardInformations(userId);
        if (creditCard != null) {
            creditCardDto = modelMapper.map(creditCard, CreditCardDto.class);
        } else {
            creditCardDto = new CreditCardDto();
        }
        return creditCardDto;
    }

    @GetMapping("/creditcard")
    public ModelAndView showCreditCard(@RequestParam(required = false) String action) {

        User user = userService.getLoggedUser(); // User who's log
        Long userId = user.getId();

        Map<String, Object> model = new HashMap<>(); //Add all information in form page
        model.put("creditCardDto", getCreditCardForm(userId));
        model.put("accountIsSet", creditCardService.checkIfUserCreditCardExists(userId));
        model.put("balance", user.getBalance());
        model.put("pseudo", user.getPseudo());
        model.put("creditCardDtoWithdraw", new CreditCardDtoWithdraw());
        model.put("creditCardDtoDeposit", new CreditCardDtoDeposit());

        if (action != null && action.equals("delete")) { // delete credit card
            boolean deleteCreditCard = creditCardService.deleteCreditCard(userId);
            RedirectView redirect = new RedirectView();
            if (deleteCreditCard) {
                redirect.setUrl("creditcard" + "?delete_sucess");
            } else {
                redirect.setUrl("credicard" + "?delete_error");
            }
            return new ModelAndView(redirect, new HashMap<>());
        }

        return new ModelAndView("creditcard", model);
    }

    @PostMapping("/creditcard")
    public ModelAndView addCreditCard(@Valid CreditCardDto creditCardDto,
                                    BindingResult bindingResult) {

        User user = userService.getLoggedUser();
        Long userId = user.getId();

        Map<String, Object> model = new HashMap<>();
        model.put("accountIsSet", creditCardService.checkIfUserCreditCardExists(userId));
        model.put("balance", user.getBalance());
        model.put("peusdo", user.getPseudo());
        model.put("creditCardDtoWithdraw", new CreditCardDtoWithdraw());
        model.put("creditCardDtoDeposit", new CreditCardDtoDeposit());

        if (bindingResult.hasErrors()) {  // If there are errors when add or update credit card
            return new ModelAndView("creditcard", model);
        }

        RedirectView redirect = new RedirectView();

        if (creditCardService.checkIfUserCreditCardExists(userId)) {
            creditCardService.updateCreditCardInformation(userId, creditCardDto);
            redirect.setUrl("creditcard" + "?new");
        }
        else
        {
            CreditCard creditCard = new CreditCard(user, creditCardDto.getCardNumbers(),
                    creditCardDto.getCryptogram(), creditCardDto.getEndValidity());
            creditCardService.saveCreditCardInformation(creditCard);
            redirect.setUrl("creditcard" + "?success");
        }
        return new ModelAndView(redirect, model);
    }

    @PostMapping("/creditcard-withdraw")
    public ModelAndView submitCreditCardWithdrawForm(@Valid CreditCardDtoWithdraw creditCardDtoWithdraw,
                                               BindingResult bindingResult) {

        User user = userService.getLoggedUser();
        Long userId = user.getId();

        Map<String, Object> model = new HashMap<>();
        model.put("creditCardDto", getCreditCardForm(userId));
        model.put("accountIsSet", creditCardService.checkIfUserCreditCardExists(userId));
        model.put("balance", user.getBalance());
        model.put("pseudo", user.getPseudo());
        model.put("creditCardDtoWithdraw", new CreditCardDtoWithdraw());

        if (bindingResult.hasErrors()) { // If the view response doesn't good
            return new ModelAndView("creditcard", model);
        }

        // Update user balance
        double money = Double.parseDouble(creditCardDtoWithdraw.getWithdrawMoney());
        creditCardService.withdrawMoneyAndUpdateBalance(userId, money);

        RedirectView redirect = new RedirectView();
        redirect.setUrl("creditcard" + "?withdraw");

        return new ModelAndView(redirect, model);
    }

    @PostMapping("/creditcard-deposit")
    public ModelAndView submitCreditCardForm(@Valid CreditCardDtoDeposit creditCardDtoDeposit,
                                              BindingResult bindingResult) {

        User user = userService.getLoggedUser();
        Long userId = user.getId();

        Map<String, Object> model = new HashMap<>();
        model.put("creditCardDto", getCreditCardForm(userId));
        model.put("accountIsSet", creditCardService.checkIfUserCreditCardExists(userId));
        model.put("balance", user.getBalance());
        model.put("pseudo", user.getPseudo());
        model.put("creditCardDtoDeposit", new CreditCardDtoDeposit());

        if (bindingResult.hasErrors()) {
            return new ModelAndView("creditcard", model);
        }

        try {
            double money = Double.parseDouble(creditCardDtoDeposit.getDepositMoney());
            boolean moneySent = creditCardService.depositMoneyAndUpdateBalance(userId, money);
            RedirectView redirect = new RedirectView();
            if (moneySent) {
                redirect.setUrl("creditcard" + "?deposit");
                return new ModelAndView(redirect, new HashMap<>());
            }
        } catch (Exception error) {
            bindingResult.rejectValue("depositMoney", "", error.getMessage());
            return new ModelAndView("creditcard", model);
        }
        return new ModelAndView("creditcard", model);
    }
}
