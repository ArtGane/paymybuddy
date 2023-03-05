package com.paymybuddy.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    private Long userSenderId;
    private Long userReceiverId;
    private String userReceiverName;
    @Size(max = 150, message = "Please make a description in 150 characters")
    private String description;
    private int amount;
    private LocalDateTime creationDate;
    private Double purcent;

}
