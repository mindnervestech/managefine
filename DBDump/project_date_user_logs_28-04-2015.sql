-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.1.44-community - MySQL Community Server (GPL)
-- Server OS:                    Win32
-- HeidiSQL Version:             8.0.0.4396
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for time
CREATE DATABASE IF NOT EXISTS `time` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `time`;


-- Dumping structure for table time.project_date_user_logs
CREATE TABLE IF NOT EXISTS `project_date_user_logs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `old_start_date` datetime DEFAULT NULL,
  `old_end_date` datetime DEFAULT NULL,
  `new_start_date` datetime DEFAULT NULL,
  `new_end_date` datetime DEFAULT NULL,
  `change_date` datetime DEFAULT NULL,
  `projectinstancenode_id` bigint(20) DEFAULT '0',
  `user_id` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_project_date_user_logs_projectinstancenode` (`projectinstancenode_id`),
  KEY `FK_project_date_user_logs_user` (`user_id`),
  CONSTRAINT `FK_project_date_user_logs_projectinstancenode` FOREIGN KEY (`projectinstancenode_id`) REFERENCES `projectinstancenode` (`id`),
  CONSTRAINT `FK_project_date_user_logs_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Dumping data for table time.project_date_user_logs: ~2 rows (approximately)
/*!40000 ALTER TABLE `project_date_user_logs` DISABLE KEYS */;
INSERT INTO `project_date_user_logs` (`id`, `old_start_date`, `old_end_date`, `new_start_date`, `new_end_date`, `change_date`, `projectinstancenode_id`, `user_id`) VALUES
	(1, '2015-04-26 00:00:00', '2015-05-13 00:00:00', '2015-04-26 00:00:00', '2015-05-13 00:00:00', NULL, 37, 2),
	(2, '2015-04-26 00:00:00', '2015-05-13 00:00:00', '2015-04-28 00:00:00', '2015-05-11 00:00:00', NULL, 37, 2),
	(3, '2015-04-28 00:00:00', '2015-05-11 00:00:00', '2015-04-24 00:00:00', '2015-05-13 00:00:00', '0033-10-06 00:00:00', 37, 2);
/*!40000 ALTER TABLE `project_date_user_logs` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
