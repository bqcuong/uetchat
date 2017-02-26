create database uetchat;
use uetchat;

CREATE TABLE IF NOT EXISTS `user_java` (
  `id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `user_id` varchar(20) NOT NULL UNIQUE,
  `in_chat` char(1) NOT NULL,
  `gender` char(1) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `chat_sessions` (
  `id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `lhs` varchar(20) NOT NULL,
  `rhs` varchar(20) NOT NULL,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
