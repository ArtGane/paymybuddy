package com.paymybuddy.webapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    @Column
    private String pseudo;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8)
    @Column
    private String password;

    @NotBlank
    @OneToOne()
    @PrimaryKeyJoinColumn
    private CreditCard creditCard;

    @OneToMany
    private List<Friendship> friendsList;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_sender")
    private List<Transaction> transactionsUserMade;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_receiver")
    private List<Transaction> transactionsUserReceived;
}
