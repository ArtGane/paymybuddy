package com.paymybuddy.webapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "friendship")
public class Friendship {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "friend_id")
    private Long friendId;
}
