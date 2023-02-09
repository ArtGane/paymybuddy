package com.paymybuddy.webapp.repository;

import com.paymybuddy.webapp.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRpository extends JpaRepository<CreditCard, Long> {
}
