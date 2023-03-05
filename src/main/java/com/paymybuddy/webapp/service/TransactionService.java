package com.paymybuddy.webapp.service;

import com.paymybuddy.webapp.entity.Transaction;
import com.paymybuddy.webapp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.TransactionRequiredException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Transactional
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserService userService;

    public boolean makeTransaction(Transaction transaction) throws TransactionRequiredException {
        if (transaction != null) {
            Long senderId = transaction.getUserSenderId();
            Long receiverId = transaction.getUserReceiverId();

            if (senderId != receiverId) {
                transactionRepository.save(transaction);

                // Updating user balances
                transactionRepository.updateSenderBalance(transaction.getUserSenderId(), transaction.getAmount() + transaction.getPurcent());
                transactionRepository.updateReceiverBalance(transaction.getUserReceiverId(), transaction.getAmount());
                return true;
            }

            throw new UsernameNotFoundException( "User is trying to send money to himself");
        }
        throw new TransactionRequiredException("There was a unexpected error while trying to make the transaction");
    }

    private void updateReceiverBalance(Long receiverId, double amountWithoutFee) {
        transactionRepository.updateReceiverBalance(receiverId, amountWithoutFee);
    }

    public double purcent(double amount) {
        if (amount > 0) {
            // Calculate the fee amount
            amount = (amount * 0.5) / 100;
            return amount;
        }
        throw new UsernameNotFoundException("The amount entered is incorrect");
    }

    public List<Transaction> getTransactionsByPage(Long userSenderId, Integer numberOfItemsPerPage, Integer pageId) {
        return transactionRepository.getUserTransactionsPages(userSenderId, numberOfItemsPerPage,
                numberOfItemsPerPage * (pageId - 1));
    }

    public List<Transaction> getUserTransactionsPage(Long userSenderId, Integer numberOfItemsPerPage) {
        return transactionRepository.getUserTransactionsPage(userSenderId, numberOfItemsPerPage);
    }

    public Integer getCountOfTransactionsForUserId(Long userSenderId) {
        return transactionRepository.getCountOfTransactionsForUserId(userSenderId);
    }
}
