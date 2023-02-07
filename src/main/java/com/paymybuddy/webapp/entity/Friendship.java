package com.paymybuddy.webapp.entity;

import com.paymybuddy.webapp.entity.compositekey.FriendshipId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "friendship")
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @EmbeddedId
    private FriendshipId friendshipId;
}
