package com.paymybuddy.webapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

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
    private Long userSenderId;

    @Column(name = "user_receiver")
    private Long userReceiverId;

    @Column(name = "name_receiver")
    private String userReceiverName;

    @Column
    private String description;

    @Column
    private Double amount;

    @Column
    private LocalDateTime creationDate;

    @Column
    private Double purcent;

}
