package com.paymybuddy.webapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

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
