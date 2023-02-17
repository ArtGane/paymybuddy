package com.paymybuddy.webapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardDto {

    @NotEmpty()
    @Length(min = 16)
    private String cardNumbers;

    @Digits(integer = 3, fraction = 0) //add conventional limit but length isn't work for int
    private int cryptogram;

    @JsonFormat(pattern = "MM/YY")
    private String endValidity;
}
