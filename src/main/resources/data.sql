-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Host: localhost
--
-- Database: `grofers_contest`
--

-- --------------------------------------------------------

-- Table structure for table `users`

create database grofers_contest;
use grofers_contest;

CREATE TABLE `users` (
   `id` varchar(50) NOT NULL PRIMARY KEY,
   `NAME` varchar(255) DEFAULT NULL,
   `MOBILE` BIGINT(20) DEFAULT NULL
);

-- Table structure for table `tickets`


CREATE TABLE `tickets` (
   `USER_ID` varchar(50) ,
   `CONTEST` varchar(255) DEFAULT NULL,
   `TICKET_ID` varchar(50),
   PRIMARY KEY (USER_ID, TICKET_ID)
);

-- Table structure for table `contest`


CREATE TABLE `contest` (
    `CONTEST_NAME`  varchar(255) NOT NULL PRIMARY KEY,
    `CONTEST_PRIZE` varchar(255) DEFAULT NULL,
    `WINNER`        varchar(255) DEFAULT "",
    `START_TIME`    TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    `END_TIME`      TIMESTAMP
);
