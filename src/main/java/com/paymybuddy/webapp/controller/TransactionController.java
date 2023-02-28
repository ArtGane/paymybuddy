package com.paymybuddy.webapp.controller;

import com.paymybuddy.webapp.dto.TransactionDto;
import com.paymybuddy.webapp.entity.Transaction;
import com.paymybuddy.webapp.entity.User;
import com.paymybuddy.webapp.service.TransactionService;
import com.paymybuddy.webapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.TransactionRequiredException;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
public class TransactionController {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/create-transaction")
    public ModelAndView getTransactionForm(HttpServletRequest request) {

        // Get user's parameters
        User user = userService.getLoggedUser();
        Map<String, Object> model = new HashMap<>();
        model.put("pseudo", user.getPseudo());
        model.put("balance", user.getBalance());

        model.put("transactionDto", new TransactionDto());

        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap != null) {

            Long contactId = (Long) inputFlashMap.get("contactId");
            User contact = userService.findUserById(contactId);
            model.put("contactPseudo", contact.getPseudo());
            model.put("contactId", contactId);

            // Amount
            int amount = (Integer) inputFlashMap.get("amount");
            model.put("amountToTransfer", amount);

            // Purcent
            double purcent = transactionService.purcent(amount);
            model.put("purcent", purcent);

            // Total amount and balance after payment
            model.put("totalAmount", (amount + purcent));
            model.put("balanceAfterPayment", (user.getBalance() - (amount + purcent)));
        } else {
            return new ModelAndView(new RedirectView("/?transaction_error"));
        }

        return new ModelAndView("transaction", model);
    }


    @PostMapping("/process-transaction")
    public ModelAndView submitCreditCardithdrawForm(HttpServletRequest request, @Valid TransactionDto transactionDto,
                                               BindingResult bindingResult) throws TransactionRequiredException {

        User user = userService.getLoggedUser();
        Long userId = user.getId();

        Map<String, Object> model = new HashMap<>();
        if (bindingResult.hasErrors()) {

            User contact = userService.findUserById(transactionDto.getUserReceiverId());
            model.put("receiverPseudo", contact.getPseudo());
            model.put("senderId", userId);
            model.put("receiverId", contact.getId());

            double amount = transactionDto.getAmount();
            double purcent = transactionService.purcent(amount);
            model.put("amountToTransfer", amount);
            model.put("purcent", purcent);

            model.put("totalAmount", amount + purcent);
            model.put("balanceAfterPayment", user.getBalance() - (amount + purcent));

            return new ModelAndView("transaction", model);
        }

        transactionDto.setUserSenderId(userId);
        transactionDto.setPurcent(transactionService.purcent(transactionDto.getAmount()));
        transactionDto.setCreationDate(LocalDateTime.now());

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Transaction transaction = modelMapper.map(transactionDto, Transaction.class);

        transactionService.makeTransaction(transaction);

        RedirectView redirect = new RedirectView();
        redirect.setUrl("/?transaction_success");

        return new ModelAndView(redirect, new HashMap<>());
    }

}
