create user `jpashop`@`localhost` identified by '12341234';
create database jpashop CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;
grant all privileges on jpashop.* to `jpashop`@`localhost` ;