package com.paymybuddy.webapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardDto {

    @NotEmpty(message = "Merci de renseigner les numéros figurant sur votre carte de crédit")
    @Column
    @Length(min = 16)
    private String cardNumbers;

    @NotNull
    @Column
    @Size(max = 3)
    private int cryptogram;

    @NotNull
    @Column
    private Date endValidity;
}
