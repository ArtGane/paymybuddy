package com.paymybuddy.webapp.entity.compositekey;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Embeddable
public class FriendshipId implements Serializable {

    private Long userId;
    private Long friendId;

    public FriendshipId(Long userId, Long friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }
}
