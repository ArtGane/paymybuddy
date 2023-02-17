package com.paymybuddy.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardDtoWithdraw {

    @Min(value = 1, message = "Minimum withdrawal from credit card is 1 €")
    private String withdrawMoney = "1.00";

}
