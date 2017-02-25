create database uetchat;
use uetchat;

ALTER TABLE `user` ADD (`first_name` VARCHAR(255) DEFAULT NULL, `last_name` VARCHAR(255) DEFAULT NULL);

CREATE TABLE IF NOT EXISTS `user` (
  `user_id` varchar(20) NOT NULL,
  `in_chat` char(1) NOT NULL,
  `gender` char(1) DEFAULT NULL,
  `update_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `chat` (
  `lhs` varchar(20) NOT NULL,
  `rhs` varchar(20) NOT NULL,
  `update_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
