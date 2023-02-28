package com.paymybuddy.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCreatorDto {

    @NotNull(message = "Please select a contact")
    private Long contactId;

    private int amount;
}
