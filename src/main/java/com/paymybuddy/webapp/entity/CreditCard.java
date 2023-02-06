package com.paymybuddy.webapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "credit_card")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @NotNull
    @Column
    @Size(min = 16, max = 16)
    private int cardNumbers;

    @NotNull
    @Column
    @Size(min = 4, max = 4)
    private int cryptogram;

    @NotNull
    @Column
    private Date endValidity;


}
