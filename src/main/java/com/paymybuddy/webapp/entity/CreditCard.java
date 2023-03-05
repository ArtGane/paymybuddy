package com.paymybuddy.webapp.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "credit_card")
public class CreditCard {

    public CreditCard(User user, String cardNumbers, int cryptogram, String endValidity) {
        this.user = user;
        this.cardNumbers = cardNumbers;
        this.cryptogram = cryptogram;
        this.endValidity = endValidity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @NotNull
    @Column(name = "card_numbers")
    @Length(min = 16)
    private String cardNumbers;

    @NotNull
    @Column
    @Digits(integer = 3, fraction = 0)
    private int cryptogram;

    @NotNull
    @Column(name = "end_validity")
    @JsonFormat(pattern = "MM/YY")
    private String endValidity;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;


    //Recursivity
    @Override
    public String toString() {
        return "*************";
    }
}
