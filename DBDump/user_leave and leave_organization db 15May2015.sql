-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.6.19 - MySQL Community Server (GPL)
-- Server OS:                    Win32
-- HeidiSQL Version:             8.0.0.4396
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for timemgmt
DROP DATABASE IF EXISTS `timemgmt`;
CREATE DATABASE IF NOT EXISTS `timemgmt` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `timemgmt`;


-- Dumping structure for table timemgmt.user_leave
DROP TABLE IF EXISTS `user_leave`;
CREATE TABLE IF NOT EXISTS `user_leave` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `leave_type` int(11) DEFAULT NULL,
  `from_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_user_leave_user_56` (`user_id`),
  CONSTRAINT `fk_user_leave_user_56` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.user_leave: ~14 rows (approximately)
/*!40000 ALTER TABLE `user_leave` DISABLE KEYS */;
INSERT INTO `user_leave` (`id`, `user_id`, `reason`, `leave_type`, `from_date`) VALUES
	(6, NULL, 'Independence Day', 7, '2015-08-15 00:00:00'),
	(18, NULL, 'holiday', 7, '2015-06-17 00:00:00'),
	(20, NULL, 'Festival', 7, '2015-07-22 00:00:00'),
	(21, NULL, 'diwali', 7, '2015-10-30 00:00:00'),
	(22, NULL, 'Maharashtra Day', 7, '2015-05-01 00:00:00'),
	(26, NULL, 'Weekly Leaves', 0, NULL);
/*!40000 ALTER TABLE `user_leave` ENABLE KEYS */;


-- Dumping structure for table timemgmt.user_leave_organization
DROP TABLE IF EXISTS `user_leave_organization`;
CREATE TABLE IF NOT EXISTS `user_leave_organization` (
  `user_leave_id` bigint(20) NOT NULL,
  `organization_id` bigint(20) NOT NULL,
  KEY `FK_user_leave_organization_user_leave` (`user_leave_id`),
  KEY `FK_user_leave_organization_organization` (`organization_id`),
  CONSTRAINT `FK_user_leave_organization_organization` FOREIGN KEY (`organization_id`) REFERENCES `organization` (`id`),
  CONSTRAINT `FK_user_leave_organization_user_leave` FOREIGN KEY (`user_leave_id`) REFERENCES `user_leave` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table timemgmt.user_leave_organization: ~9 rows (approximately)
/*!40000 ALTER TABLE `user_leave_organization` DISABLE KEYS */;
INSERT INTO `user_leave_organization` (`user_leave_id`, `organization_id`) VALUES
	(18, 1),
	(20, 1),
	(20, 2),
	(20, 3),
	(20, 4),
	(20, 5),
	(21, 5),
	(22, 2),
	(6, 1);
/*!40000 ALTER TABLE `user_leave_organization` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
