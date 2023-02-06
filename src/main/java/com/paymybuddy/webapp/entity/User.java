package com.paymybuddy.webapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private String pseudo;

    @Email
    @NotNull
    private String email;

    @NotNull
    @Size(min = 8)
    @Column
    private String password;

    @NotNull
    @OneToOne()
    @PrimaryKeyJoinColumn
    private CreditCard creditCard;

    @OneToMany
    private List<Friendship> friendsList;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Transaction> transactionsUserMade;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Transaction> transactionsUserReceived;
}
