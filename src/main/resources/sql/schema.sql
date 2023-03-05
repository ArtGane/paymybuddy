-- Create User
CREATE TABLE user (
  id BIGINT AUTO_INCREMENT NOT NULL,
   pseudo VARCHAR(255) NOT NULL,
   email VARCHAR(255) NOT NULL,
   password VARCHAR(255) NOT NULL,
   CONSTRAINT pk_user PRIMARY KEY (id)
);

-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

-- Create CreditCard
CREATE TABLE credit_card (
  id BIGINT AUTO_INCREMENT NOT NULL,
   card_numbers VARCHAR(16) NOT NULL,
   cryptogram INT NOT NULL,
   end_validity VARCHAR(5) NOT NULL,
   user_id BIGINT NOT NULL,
   CONSTRAINT pk_credit_card PRIMARY KEY (id)
);

ALTER TABLE credit_card ADD CONSTRAINT FK_CREDITCARD_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

-- Create Transaction
CREATE TABLE transaction (
  id BIGINT AUTO_INCREMENT NOT NULL,
   user_sender BIGINT NOT NULL,
   user_receiver BIGINT NOT NULL,
   name_receiver VARCHAR(255) NOT NULL,
   `description` VARCHAR(255) NULL,
   amount DOUBLE NOT NULL,
   creation_date date NULL,
   purcent DOUBLE NOT NULL,
   CONSTRAINT pk_transaction PRIMARY KEY (id)
);

ALTER TABLE transaction ADD CONSTRAINT FK_TRANSACTION_ON_USER_RECEIVER FOREIGN KEY (user_receiver) REFERENCES user (id);
ALTER TABLE transaction ADD CONSTRAINT FK_TRANSACTION_ON_USER_SENDER FOREIGN KEY (user_sender) REFERENCES user (id);
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

