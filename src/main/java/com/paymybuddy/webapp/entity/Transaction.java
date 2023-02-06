package com.paymybuddy.webapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@Table(name = "transaction")
@NoArgsConstructor
public class Transaction {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_sender")
    @NotNull
    private Long userSender;

    @Column(name = "user_receiver")
    @NotNull
    private Long userReceiver;

    @Column
    private String description;

    @Column
    @NotNull
    private Double amount;

    @Column
    private Date date;

    @Column
    @NotNull
    private Double purcent;
}
