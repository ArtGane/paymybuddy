package com.paymybuddy.webapp.repository;

import com.paymybuddy.webapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmail(String email);
    User findUserById(Long id);

    @Modifying
    @Query(value = "DELETE FROM User WHERE user_id = userId AND contact_id = contactId")
    void deleteContact(@Param("userId") Long userId, @Param("contactId") Long contactId);
}
