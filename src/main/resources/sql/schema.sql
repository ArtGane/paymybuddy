-- Create User
CREATE TABLE user (
  id BIGINT AUTO_INCREMENT NOT NULL,
   pseudo VARCHAR(255) NOT NULL,
   email VARCHAR(255) NOT NULL,
   password VARCHAR(255) NOT NULL,
   credit_card_id BIGINT NOT NULL,
   CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE user ADD CONSTRAINT FK_USER_ON_CREDITCARD FOREIGN KEY (credit_card_id) REFERENCES credit_card (id);
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

-- Create CreditCard
CREATE TABLE credit_card (
  id BIGINT AUTO_INCREMENT NOT NULL,
   card_numbers INT NOT NULL,
   cryptogram INT NOT NULL,
   end_validity datetime NOT NULL,
   CONSTRAINT pk_credit_card PRIMARY KEY (id)
);
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --


-- Create Transaction
CREATE TABLE transaction (
  id BIGINT AUTO_INCREMENT NOT NULL,
   user_sender BIGINT NULL,
   user_receiver BIGINT NULL,
   `description` VARCHAR(255) NULL,
   amount DOUBLE NULL,
   date datetime NULL,
   purcent DOUBLE NULL,
   CONSTRAINT pk_transaction PRIMARY KEY (id)
);
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --


-- Create Friendship
CREATE TABLE friendship (
  user_id BIGINT NOT NULL,
   friend_id BIGINT NOT NULL,
   CONSTRAINT pk_friendship PRIMARY KEY (user_id, friend_id)
);
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
