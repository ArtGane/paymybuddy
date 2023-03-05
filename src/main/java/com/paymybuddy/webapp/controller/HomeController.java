package com.paymybuddy.webapp.controller;

import com.paymybuddy.webapp.dto.TransactionCreatorDto;
import com.paymybuddy.webapp.entity.Transaction;
import com.paymybuddy.webapp.entity.User;
import com.paymybuddy.webapp.service.TransactionService;
import com.paymybuddy.webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.datetime.standard.InstantFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    private Integer itemsPerPages = 5;

    @GetMapping("/home")
    public ModelAndView showHomePage(@RequestParam(required = false) Integer pageId) {

        User user = userService.getLoggedUser();
        Long userId = user.getId();
        List<User> contactList = user.getContact();

        Map<String, Object> model = new HashMap<>();

        // Logged user related datas
        model.put("userId", user.getId());
        model.put("pseudo", user.getPseudo());
        model.put("balance", user.getBalance());
        model.put("userService", userService);
        // Creating an utility class InstantFormatter to format the date displayed
        model.put("instantFormatter", new InstantFormatter());

        model.put("contactList", contactList);
        model.put("hasContact", !contactList.isEmpty());

        model.put("transactionCreatorDto", new TransactionCreatorDto());
        Integer numberOfTransactions = transactionService.getCountOfTransactionsForUserId(userId);

        int numberOfPages = (int) Math.ceil((double) numberOfTransactions / itemsPerPages);
        model.put("numberOfPages", numberOfPages);
        model.put("hasTransactions", numberOfTransactions > 0);
        model.put("numberOfTransactions", numberOfTransactions);

        List<Transaction> transactions;
        if (pageId != null && ((pageId > numberOfPages) || pageId <= 0)) {
            return new ModelAndView(new RedirectView("/home"));
        } else if (pageId == null || pageId == 1) {
            transactions = transactionService.getUserTransactionsPage(userId, itemsPerPages);
        } else {
            transactions = transactionService.getTransactionsByPage(userId, itemsPerPages, pageId);
        }

        model.put("transactions", transactions);

        return new ModelAndView("index", model);
    }

    @PostMapping("/begin-transaction")
    public ModelAndView beginTransaction(@Valid TransactionCreatorDto transactionCreatorDto, BindingResult bindingResult,
                                         RedirectAttributes redirectAttributes) {

        User user = userService.getLoggedUser();
        Long userId = user.getId();
        List<User> contactList = user.getContact();

        Map<String, Object> model = new HashMap<>();

        model.put("userId", user.getId());
        model.put("pseudo", user.getPseudo());
        model.put("balance", user.getBalance());

        model.put("userService", userService);
        model.put("instantFormatter", new InstantFormatter());

        model.put("contactList", contactList);
        model.put("hasContact", !contactList.isEmpty());

        List<Transaction> transactions = transactionService.getUserTransactionsPage(userId, itemsPerPages);
        Integer numberOfTransactions = transactionService.getCountOfTransactionsForUserId(userId);

        model.put("transactions", transactions);
        model.put("hasTransactions", numberOfTransactions > 0);
        model.put("numberOfTransactions", numberOfTransactions);


        int numberOfPages = (int) Math.ceil((double) numberOfTransactions / itemsPerPages);
        model.put("numberOfPages", numberOfPages);

        if (bindingResult.hasErrors()) {
            return new ModelAndView("index", model);
        }

        redirectAttributes.addFlashAttribute("userReceiverId", transactionCreatorDto.getContactId());
        redirectAttributes.addFlashAttribute("amount", transactionCreatorDto.getAmount());
        redirectAttributes.addFlashAttribute("userReceiverName", transactionCreatorDto.getUserReceiverName());
        RedirectView redirect = new RedirectView("/create-transaction");
        return new ModelAndView(redirect);
    }
}
