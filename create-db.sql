create database uetchat2;
use uetchat2;

CREATE TABLE `user_java` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` varchar(30) DEFAULT NULL,
  `first_name` varchar(200) DEFAULT NULL,
  `last_name` varchar(200) DEFAULT NULL,
  `gender` varchar(200) DEFAULT NULL,
  `in_chat` varchar(1) NOT NULL DEFAULT 'N',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_java_user_id_unique` (`user_id`),
  KEY `user_java_id_index` (`id`),
  KEY `user_java_user_id_index` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2193 DEFAULT CHARSET=utf8;

CREATE TABLE `chat_sessions` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `lhs_fb_id` varchar(200) DEFAULT NULL,
  `rhs_fb_id` varchar(200) DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `chat_sessions_id_index` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2291 DEFAULT CHARSET=utf8;
