package com.paymybuddy.webapp.repository;

import com.paymybuddy.webapp.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRpository extends JpaRepository<CreditCard, Long> {

    CreditCard findByUserId(Long userId);

    @Modifying
    void deleteCreditCardFromIdUser(@Param("user_id") Long userId);
}
