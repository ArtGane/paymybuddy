package com.paymybuddy.webapp.entity;

import jakarta.persistence.*;
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

    @Column
    private Long userSender;

    @Column
    private Long userReceiver;

    @Column
    private String description;

    @Column
    private Double amount;

    @Column
    private Date date;

    @Column
    private Double purcent;
}
