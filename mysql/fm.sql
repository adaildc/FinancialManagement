# MySQL-Front 5.0  (Build 1.126)

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE */;
/*!40101 SET SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES */;
/*!40103 SET SQL_NOTES='ON' */;


# Host: localhost    Database: cky
# ------------------------------------------------------
# Server version 5.1.32-community

USE `cky`;

#
# Table structure for table fm
#

CREATE TABLE `fm` (
  `resion` text NOT NULL,
  `money` double unsigned NOT NULL DEFAULT '0',
  `datepicker` date NOT NULL DEFAULT '0000-00-00',
  `addtext` text,
  `time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`resion`(10),`datepicker`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table fm
#
LOCK TABLES `fm` WRITE;

UNLOCK TABLES;

/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
