package com.paymybuddy.webapp.entity;

import com.paymybuddy.webapp.entity.compositekey.FriendshipId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "friendship")
@IdClass(FriendshipId.class)
public class Friendship {

    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Id
    @Column(name = "friend_id")
    private Integer friendId;
}
