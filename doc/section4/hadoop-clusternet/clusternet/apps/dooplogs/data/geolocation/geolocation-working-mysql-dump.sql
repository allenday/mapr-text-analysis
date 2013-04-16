# Sequel Pro dump
# Version 2492
# http://code.google.com/p/sequel-pro
#
# Host: 192.168.48.11 (MySQL 5.1.49-1ubuntu8.1)
# Database: geoips
# Generation Time: 2011-04-27 00:10:26 -0700
# Marcello de Sales (marcello.desales@gmail.com)
# ************************************************************

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table country
# ------------------------------------------------------------

DROP TABLE IF EXISTS `country`;

CREATE TABLE `country` (
  `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `code` char(2) NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `code_index` (`code`),
  KEY `name_index` (`name`)
) ENGINE=MyISAM AUTO_INCREMENT=243 DEFAULT CHARSET=utf8;



# Dump of table geocity_block
# ------------------------------------------------------------

DROP TABLE IF EXISTS `geocity_block`;

CREATE TABLE `geocity_block` (
  `start_int` int(10) unsigned NOT NULL,
  `end_int` int(10) unsigned NOT NULL,
  `location_id` int(11) NOT NULL,
  KEY `location_id_index` (`location_id`),
  KEY `start_int_index` (`start_int`),
  KEY `end_int_index` (`end_int`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


# Dump of table geocity_location
# ------------------------------------------------------------

DROP TABLE IF EXISTS `geocity_location`;

CREATE TABLE `geocity_location` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `country_code` char(2) NOT NULL,
  `region` varchar(100) CHARACTER SET latin1 DEFAULT NULL,
  `city` varchar(100) CHARACTER SET latin1 DEFAULT NULL,
  `zip_code` varchar(15) DEFAULT NULL,
  `latitude` float NOT NULL,
  `longitude` float NOT NULL,
  `metro_code` smallint(6) DEFAULT NULL,
  `area_code` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `country_code` (`country_code`),
  KEY `region_index` (`region`),
  KEY `city_index` (`city`),
  KEY `zip_code_index` (`zip_code`),
  KEY `lat_index` (`latitude`),
  KEY `long_index` (`longitude`),
  KEY `area_code_index` (`area_code`)
) ENGINE=MyISAM AUTO_INCREMENT=294018 DEFAULT CHARSET=utf8;



# Dump of table geocountry
# ------------------------------------------------------------

DROP TABLE IF EXISTS `geocountry`;

CREATE TABLE `geocountry` (
  `start_ip` char(15) NOT NULL,
  `end_ip` char(15) NOT NULL,
  `start_int` int(10) unsigned NOT NULL,
  `end_int` int(10) unsigned NOT NULL,
  `code` char(2) NOT NULL,
  `name` varchar(50) NOT NULL,
  KEY `code_index` (`code`),
  KEY `name_index` (`name`),
  KEY `start_ip` (`start_ip`),
  KEY `end_ip` (`end_ip`),
  KEY `start_int` (`start_int`),
  KEY `end_int` (`end_int`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;



# Dump of table geoip
# ------------------------------------------------------------

DROP TABLE IF EXISTS `geoip`;

CREATE TABLE `geoip` (
  `country_id` tinyint(3) unsigned NOT NULL,
  `start` int(10) unsigned NOT NULL,
  `end` int(10) unsigned NOT NULL,
  KEY `country_id_index` (`country_id`),
  KEY `start_index` (`start`),
  KEY `end_index` (`end`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;