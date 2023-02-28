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

        // Get current logged user
        User user = userService.getLoggedUser();
        Long userId = user.getId();
        List<User> contactList = user.getContact();

        // Add some information in the model
        Map<String, Object> model = new HashMap<>();

        // Logged user related datas
        model.put("userId", user.getId());
        model.put("pseudo", user.getPseudo());
        model.put("balance", user.getBalance());

        // The userService is used in the index.html page to transform raw datas
        model.put("userService", userService);
        // Creating an utility class InstantFormatter to format the date displayed
        model.put("instantFormatter", new InstantFormatter());

        // Connections related datas
        model.put("contactList", contactList);
        model.put("hasContact", !contactList.isEmpty());

        // Calculate the number of pages to show for the user
        List<Transaction> transactions = transactionService.getUserTransactionsPage(userId, itemsPerPages);
        Integer numberOfTransactions = transactionService.getCountOfTransactionsForUserId(userId);

        model.put("transactions", transactions);
        model.put("hasTransactions", numberOfTransactions > 0);
        model.put("numberOfTransactions", numberOfTransactions);

        // Calculating the number of pages. We need to convert the integer to double
        // to get the decimal part. If decimal part is greater than 0, then we put the
        // upper limit to the next integer
        int numberOfPages = (int) Math.ceil((double) numberOfTransactions / itemsPerPages);
        model.put("numberOfPages", numberOfPages);

        // If there are errors when on the "Send money to a buddy" form
        if (bindingResult.hasErrors()) {
            return new ModelAndView("index", model);
        }

        // The attributes that we want to pass to the transaction controller :
        // - The buddy Id
        // - The amount of money to send (field is a String, so we need to convert it to
        // double)
        redirectAttributes.addFlashAttribute("contactId", transactionCreatorDto.getContactId());
        redirectAttributes.addFlashAttribute("amount", transactionCreatorDto.getAmount());
        RedirectView redirect = new RedirectView("/create-transaction");
        return new ModelAndView(redirect);
    }
}
