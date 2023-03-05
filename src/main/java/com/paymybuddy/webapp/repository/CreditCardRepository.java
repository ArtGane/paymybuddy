package com.paymybuddy.webapp.repository;

import com.paymybuddy.webapp.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

    CreditCard findByUserId(Long userId);

    @Modifying
    @Query(value = "UPDATE User u SET u.balance = :balance WHERE u.id = :userId")
    void updateBalance(@Param("userId") Long userId, @Param("balance") Double balance);

    @Modifying
    @Query(value = "DELETE CreditCard c WHERE c.user.id=:userId")
    void deleteCreditCardByUserId(@Param("userId") Long userId);
}
