/*
SQLyog Community Edition- MySQL GUI v8.14 
MySQL - 5.1.41 : Database - craftbukkit
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `home` */

DROP TABLE IF EXISTS `home`;

CREATE TABLE `home` (
  `name` varchar(30) CHARACTER SET utf8 NOT NULL,
  `world` varchar(30) COLLATE utf8_bin NOT NULL,
  `x` varchar(30) CHARACTER SET utf8 NOT NULL DEFAULT '0',
  `y` varchar(30) CHARACTER SET utf8 NOT NULL DEFAULT '0',
  `z` varchar(30) CHARACTER SET utf8 NOT NULL DEFAULT '0',
  `yaw` varchar(30) CHARACTER SET utf8 NOT NULL DEFAULT '0',
  `pitch` varchar(30) CHARACTER SET utf8 NOT NULL DEFAULT '0',
  KEY `WorldIndex` (`world`),
  KEY `NameIndex` (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*Table structure for table `kit` */

DROP TABLE IF EXISTS `kit`;

CREATE TABLE `kit` (
  `id` int(15) NOT NULL AUTO_INCREMENT,
  `name` varchar(15) NOT NULL,
  `rank` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `NewIndex1` (`name`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Table structure for table `kit_items` */

DROP TABLE IF EXISTS `kit_items`;

CREATE TABLE `kit_items` (
  `id` int(10) NOT NULL,
  `item` int(15) NOT NULL DEFAULT '1',
  `quanity` int(15) NOT NULL DEFAULT '64'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Table structure for table `mail` */

DROP TABLE IF EXISTS `mail`;

CREATE TABLE `mail` (
  `sender` varchar(15) NOT NULL,
  `reciever` varchar(15) NOT NULL,
  `text` varchar(150) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Table structure for table `warp` */

DROP TABLE IF EXISTS `warp`;

CREATE TABLE `warp` (
  `name` varchar(30) NOT NULL,
  `world` varchar(30) NOT NULL,
  `x` varchar(30) NOT NULL,
  `y` varchar(30) NOT NULL,
  `z` varchar(30) NOT NULL,
  `yaw` varchar(30) NOT NULL,
  `pitch` varchar(30) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
