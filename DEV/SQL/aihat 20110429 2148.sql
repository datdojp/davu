-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.5.11


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema aihat
--

CREATE DATABASE IF NOT EXISTS aihat;
USE aihat;

--
-- Definition of table `clip`
--

DROP TABLE IF EXISTS `clip`;
CREATE TABLE `clip` (
  `id` int(10) unsigned NOT NULL,
  `title` varchar(1000) NOT NULL,
  `singer_id` int(10) unsigned zerofill NOT NULL,
  `composer_id` int(10) unsigned zerofill NOT NULL,
  `genre_id` int(10) unsigned zerofill NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `link` varchar(1000) NOT NULL,
  `display_on_homepage` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_clip_1` (`singer_id`),
  KEY `FK_clip_2` (`composer_id`),
  KEY `FK_clip_3` (`genre_id`),
  KEY `FK_clip_4` (`user_id`),
  CONSTRAINT `FK_clip_1` FOREIGN KEY (`singer_id`) REFERENCES `singer` (`id`),
  CONSTRAINT `FK_clip_2` FOREIGN KEY (`composer_id`) REFERENCES `composer` (`id`),
  CONSTRAINT `FK_clip_3` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`id`),
  CONSTRAINT `FK_clip_4` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `clip`
--

/*!40000 ALTER TABLE `clip` DISABLE KEYS */;
/*!40000 ALTER TABLE `clip` ENABLE KEYS */;


--
-- Definition of table `composer`
--

DROP TABLE IF EXISTS `composer`;
CREATE TABLE `composer` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` int(10) unsigned NOT NULL,
  `modified_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `birthday` datetime NOT NULL,
  `country` varchar(45) NOT NULL,
  `description` varchar(5000) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_composer_1` (`user_id`),
  CONSTRAINT `FK_composer_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `composer`
--

/*!40000 ALTER TABLE `composer` DISABLE KEYS */;
/*!40000 ALTER TABLE `composer` ENABLE KEYS */;


--
-- Definition of table `genre`
--

DROP TABLE IF EXISTS `genre`;
CREATE TABLE `genre` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name_vi` varchar(100) NOT NULL,
  `name_en` varchar(100) NOT NULL,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modified_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `user_id` int(10) unsigned NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_genre_1` (`user_id`),
  CONSTRAINT `FK_genre_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `genre`
--

/*!40000 ALTER TABLE `genre` DISABLE KEYS */;
/*!40000 ALTER TABLE `genre` ENABLE KEYS */;


--
-- Definition of table `password`
--

DROP TABLE IF EXISTS `password`;
CREATE TABLE `password` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `encrypted` varchar(1000) NOT NULL,
  `version` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `password`
--

/*!40000 ALTER TABLE `password` DISABLE KEYS */;
/*!40000 ALTER TABLE `password` ENABLE KEYS */;


--
-- Definition of table `playlist`
--

DROP TABLE IF EXISTS `playlist`;
CREATE TABLE `playlist` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(1000) NOT NULL,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modified_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `user_id` int(10) unsigned NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_playlist_1` (`user_id`),
  CONSTRAINT `FK_playlist_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `playlist`
--

/*!40000 ALTER TABLE `playlist` DISABLE KEYS */;
/*!40000 ALTER TABLE `playlist` ENABLE KEYS */;


--
-- Definition of table `playlist_clip`
--

DROP TABLE IF EXISTS `playlist_clip`;
CREATE TABLE `playlist_clip` (
  `playlist_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `clip_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`playlist_id`,`clip_id`),
  KEY `FK_playlist_clip_2` (`clip_id`),
  CONSTRAINT `FK_playlist_clip_1` FOREIGN KEY (`playlist_id`) REFERENCES `playlist` (`id`),
  CONSTRAINT `FK_playlist_clip_2` FOREIGN KEY (`clip_id`) REFERENCES `clip` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `playlist_clip`
--

/*!40000 ALTER TABLE `playlist_clip` DISABLE KEYS */;
/*!40000 ALTER TABLE `playlist_clip` ENABLE KEYS */;


--
-- Definition of table `singer`
--

DROP TABLE IF EXISTS `singer`;
CREATE TABLE `singer` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modified_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `deleted` tinyint(1) NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  `birthday` datetime NOT NULL,
  `company` varchar(100) NOT NULL,
  `website` varchar(100) NOT NULL,
  `image` blob NOT NULL,
  `description` varchar(5000) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_singer_1` (`user_id`),
  CONSTRAINT `FK_singer_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `singer`
--

/*!40000 ALTER TABLE `singer` DISABLE KEYS */;
/*!40000 ALTER TABLE `singer` ENABLE KEYS */;


--
-- Definition of table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modified_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `deleted` tinyint(1) NOT NULL,
  `blocked` tinyint(1) NOT NULL,
  `password_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_user_1` (`password_id`),
  CONSTRAINT `FK_user_1` FOREIGN KEY (`password_id`) REFERENCES `password` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;


--
-- Definition of table `user_like_clip`
--

DROP TABLE IF EXISTS `user_like_clip`;
CREATE TABLE `user_like_clip` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `composer_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`user_id`,`composer_id`),
  KEY `FK_user_like_clip_2` (`composer_id`),
  CONSTRAINT `FK_user_like_clip_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_user_like_clip_2` FOREIGN KEY (`composer_id`) REFERENCES `clip` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_like_clip`
--

/*!40000 ALTER TABLE `user_like_clip` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_like_clip` ENABLE KEYS */;


--
-- Definition of table `user_like_singer`
--

DROP TABLE IF EXISTS `user_like_singer`;
CREATE TABLE `user_like_singer` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `singer_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`user_id`,`singer_id`),
  KEY `FK_user_like_singer_2` (`singer_id`),
  CONSTRAINT `FK_user_like_singer_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_user_like_singer_2` FOREIGN KEY (`singer_id`) REFERENCES `singer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_like_singer`
--

/*!40000 ALTER TABLE `user_like_singer` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_like_singer` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
