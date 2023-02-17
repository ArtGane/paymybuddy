package com.paymybuddy.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardDtoDeposit {

    //Have to make a different DTO from withdraw because we can't have unique movements dto
    @Min(value = 1, message = "Minimum deposit from credit card is 1 €")
    private String depositMoney = "1.00";

}
