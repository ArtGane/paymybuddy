package com.paymybuddy.webapp.entity;


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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id") //user_id est instancié avec ce nom dans la classe FriendshipId
    private User user;

}
