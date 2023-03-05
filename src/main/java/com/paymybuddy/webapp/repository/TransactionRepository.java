package com.paymybuddy.webapp.repository;

import com.paymybuddy.webapp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Modifying
    @Query(value = "UPDATE User u SET u.balance = (u.balance - :amount) WHERE u.id = :user_sender", nativeQuery = true)
    void updateSenderBalance(@Param("user_sender") Long userSenderId, @Param("amount") Double amountWithoutPurcent);

    @Modifying
    @Query(value = "UPDATE User u SET u.balance = (u.balance + :amount) WHERE u.id = :user_receiver", nativeQuery = true)
    void updateReceiverBalance(@Param("user_receiver") Long userReceiverId, @Param("amount") Double amountWithoutPurcent);

    @Query(value = "SELECT * FROM transaction t WHERE t.user_sender = :userSenderId OR t.user_receiver = :userSenderId ORDER BY t.creationDate DESC LIMIT :itemsPerPages OFFSET :offset", nativeQuery = true)
    List<Transaction> getUserTransactionsPages(@Param("userSenderId") Long userSenderId,
                                               @Param("itemsPerPages") Integer itemsPerPages, @Param("offset") Integer offset);

    @Query(value = "SELECT * FROM transaction t WHERE t.user_sender = :userSenderId OR t.user_receiver = :userSenderId ORDER BY t.creationDate DESC LIMIT :itemsPerPages", nativeQuery = true)
    List<Transaction> getUserTransactionsPage(@Param("userSenderId") Long userSenderId,
                                                    @Param("itemsPerPages") Integer itemsPerPages);

    @Query(value = "SELECT COUNT(*) FROM transaction t WHERE t.user_sender = :userSenderId OR t.user_receiver = :userSenderId ", nativeQuery = true)
    Integer getCountOfTransactionsForUserId(@Param("userSenderId") Long userSenderId);

}
