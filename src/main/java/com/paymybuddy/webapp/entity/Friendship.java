package com.paymybuddy.webapp.entity;

import com.paymybuddy.webapp.entity.compositekey.FriendshipId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;


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
