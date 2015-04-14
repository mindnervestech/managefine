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
CREATE DATABASE IF NOT EXISTS `timemgmt` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `timemgmt`;


-- Dumping structure for table timemgmt.apply_leave
CREATE TABLE IF NOT EXISTS `apply_leave` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `start_date` datetime DEFAULT NULL,
  `no_of_days` varchar(255) DEFAULT NULL,
  `type_of_leave` varchar(255) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `status` varchar(9) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `pending_with_id` bigint(20) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `process_instance_id` varchar(255) DEFAULT NULL,
  `leave_guid` varchar(255) DEFAULT NULL,
  `last_update_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_apply_leave_user_1` (`user_id`),
  KEY `ix_apply_leave_pendingWith_2` (`pending_with_id`),
  CONSTRAINT `fk_apply_leave_pendingWith_2` FOREIGN KEY (`pending_with_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_apply_leave_user_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.apply_leave: ~0 rows (approximately)
/*!40000 ALTER TABLE `apply_leave` DISABLE KEYS */;
/*!40000 ALTER TABLE `apply_leave` ENABLE KEYS */;


-- Dumping structure for table timemgmt.case_data
CREATE TABLE IF NOT EXISTS `case_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL DEFAULT '0',
  `description` varchar(255) NOT NULL DEFAULT '0',
  `status` varchar(50) NOT NULL DEFAULT '0',
  `notes` varchar(255) NOT NULL DEFAULT '0',
  `projects_id` bigint(20) NOT NULL DEFAULT '0',
  `assignto_id` bigint(20) DEFAULT NULL,
  `userid` bigint(20) DEFAULT NULL,
  `due_date` datetime DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_case_project` (`projects_id`),
  KEY `FK_case_user` (`assignto_id`),
  KEY `FK_case_data_company` (`company_id`),
  CONSTRAINT `FK_case_data_company` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_case_project` FOREIGN KEY (`projects_id`) REFERENCES `project` (`id`),
  CONSTRAINT `FK_case_user` FOREIGN KEY (`assignto_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- Dumping data for table timemgmt.case_data: ~10 rows (approximately)
/*!40000 ALTER TABLE `case_data` DISABLE KEYS */;
INSERT INTO `case_data` (`id`, `title`, `description`, `status`, `notes`, `projects_id`, `assignto_id`, `userid`, `due_date`, `company_id`) VALUES
	(1, 'dfsvcds', 'sdcsd', 'Approved', 'sdcsdc', 3, 4, NULL, '2015-04-01 00:00:00', 2),
	(2, 'wed', 'wed', 'Disapproved', 'sdcsd', 3, 7, NULL, NULL, 2),
	(3, 'sdfcdsc', 'sdfcdsc', 'Disapproved', '{"currentuser":"4","notedate":"Mon Apr 13 15:01:32 IST 2015","note":"sdcsdc"}', 3, 5, NULL, '2015-04-28 00:00:00', 1),
	(4, 'NEWW', 'erfer', 'Approved', 'efvcefc', 3, 7, NULL, '2015-04-29 00:00:00', 2),
	(5, 'AAASSSS', 'wdwexc', 'Disapproved', 'ED', 3, 7, NULL, '2015-04-16 00:00:00', 2),
	(12, 'sdcsd', 'sdcsd', 'Approved', 'dscsdc', 3, 7, NULL, '2015-04-28 00:00:00', 1),
	(13, 'EFWD', 'ewfwed', 'PendingApproval', '{"currentuser":"4","notedate":"Mon Apr 13 17:17:54 IST 2015","note":"wefwe"}', 3, 8, NULL, '2015-04-23 00:00:00', 2),
	(14, 'SDSC', 'wdwe', 'Approved', 'dscsc', 3, 7, NULL, '2015-04-16 00:00:00', 2),
	(15, 'SDSC', 'wdwe', 'Approved', 'dscsc', 3, 7, NULL, '2015-04-16 00:00:00', NULL),
	(16, 'OOOO', 'dsada', 'Approved', 'KKKKK', 3, 7, NULL, '2015-04-06 00:00:00', 2);
/*!40000 ALTER TABLE `case_data` ENABLE KEYS */;


-- Dumping structure for table timemgmt.case_flexi
CREATE TABLE IF NOT EXISTS `case_flexi` (
  `case_id` bigint(20) NOT NULL,
  `flexi_id` bigint(20) NOT NULL,
  `value` varchar(1255) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `caseflexi` (`case_id`),
  KEY `caseflexi2` (`flexi_id`),
  CONSTRAINT `caseflexi` FOREIGN KEY (`case_id`) REFERENCES `case_data` (`id`),
  CONSTRAINT `caseflexi2` FOREIGN KEY (`flexi_id`) REFERENCES `flexi_attribute` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.case_flexi: ~8 rows (approximately)
/*!40000 ALTER TABLE `case_flexi` DISABLE KEYS */;
INSERT INTO `case_flexi` (`case_id`, `flexi_id`, `value`, `id`) VALUES
	(1, 18, '[{"n":"Sunset.jpg","s":71189,"ct":"image/jpeg"}]', 1),
	(4, 18, '[{"n":"Water lilies.jpg","s":83794,"ct":"image/jpeg"}]', 2),
	(5, 18, '[{"n":"Water lilies.jpg","s":83794,"ct":"image/jpeg"}]', 3),
	(12, 18, '[{"n":"Water lilies.jpg","s":83794,"ct":"image/jpeg"}]', 4),
	(13, 18, '[{"n":"Blue hills.jpg","s":28521,"ct":"image/jpeg"}]', 5),
	(14, 18, '', 6),
	(15, 18, '', 7),
	(16, 18, '', 8);
/*!40000 ALTER TABLE `case_flexi` ENABLE KEYS */;


-- Dumping structure for table timemgmt.case_notes
CREATE TABLE IF NOT EXISTS `case_notes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `casedata_id` bigint(20) NOT NULL DEFAULT '0',
  `casenote` varchar(255) NOT NULL DEFAULT '0',
  `note_user` bigint(20) NOT NULL DEFAULT '0',
  `note_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_case_notes_case_data` (`casedata_id`),
  CONSTRAINT `FK_case_notes_case_data` FOREIGN KEY (`casedata_id`) REFERENCES `case_data` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumping data for table timemgmt.case_notes: ~3 rows (approximately)
/*!40000 ALTER TABLE `case_notes` DISABLE KEYS */;
INSERT INTO `case_notes` (`id`, `casedata_id`, `casenote`, `note_user`, `note_date`) VALUES
	(1, 15, 'dscsc', 0, NULL),
	(2, 16, 'asxasx', 4, '2015-04-13 18:35:26'),
	(3, 16, 'KKKKK', 4, '2015-04-13 18:41:04');
/*!40000 ALTER TABLE `case_notes` ENABLE KEYS */;


-- Dumping structure for table timemgmt.client
CREATE TABLE IF NOT EXISTS `client` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `client_name` varchar(255) DEFAULT NULL,
  `phone_no` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fax` int(11) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `pin` varchar(255) DEFAULT NULL,
  `contact_name` varchar(255) DEFAULT NULL,
  `contact_phone` varchar(255) DEFAULT NULL,
  `contact_email` varchar(255) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_client_company_3` (`company_id`),
  CONSTRAINT `fk_client_company_3` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.client: ~0 rows (approximately)
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` (`id`, `client_name`, `phone_no`, `email`, `fax`, `address`, `street`, `city`, `country`, `pin`, `contact_name`, `contact_phone`, `contact_email`, `company_id`) VALUES
	(1, 'abcd', '8787878', 'yy@yy.com', 45678, 'jug', 'jhj', 'jkh', 'kjhj', '87678', 'iuyiu', '986789', 'yy@yy.com', 2);
/*!40000 ALTER TABLE `client` ENABLE KEYS */;


-- Dumping structure for table timemgmt.client_flexi
CREATE TABLE IF NOT EXISTS `client_flexi` (
  `client_id` bigint(20) NOT NULL,
  `flexi_id` bigint(20) NOT NULL,
  `value` varchar(1255) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `clientflexi` (`client_id`),
  KEY `clientflexi2` (`flexi_id`),
  CONSTRAINT `clientflexi` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`),
  CONSTRAINT `clientflexi2` FOREIGN KEY (`flexi_id`) REFERENCES `flexi_attribute` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.client_flexi: ~2 rows (approximately)
/*!40000 ALTER TABLE `client_flexi` DISABLE KEYS */;
INSERT INTO `client_flexi` (`client_id`, `flexi_id`, `value`, `id`) VALUES
	(3, 9, 'fsdfasd', 42),
	(3, 10, '[{"n":"Winter.jpg","s":105542,"ct":"image/jpeg"}]', 43);
/*!40000 ALTER TABLE `client_flexi` ENABLE KEYS */;


-- Dumping structure for table timemgmt.company
CREATE TABLE IF NOT EXISTS `company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(255) DEFAULT NULL,
  `company_code` varchar(255) DEFAULT NULL,
  `company_email` varchar(255) DEFAULT NULL,
  `company_phone` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `company_status` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.company: ~2 rows (approximately)
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` (`id`, `company_name`, `company_code`, `company_email`, `company_phone`, `address`, `company_status`) VALUES
	(1, 'mindnerves', 'gmail.com', 'mindnervestech@gmail.com', '223445', 'hadapser,pune-411001', 'Approved'),
	(2, 'google', 'ggg.com', 'google@ggg.com', '2342534', 'asdasd,asdfsfds,sdfs', 'Approved');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;


-- Dumping structure for table timemgmt.delegate
CREATE TABLE IF NOT EXISTS `delegate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `from_date` datetime DEFAULT NULL,
  `to_date` datetime DEFAULT NULL,
  `delegator_id` bigint(20) DEFAULT NULL,
  `delegated_to_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_delegate_delegator_4` (`delegator_id`),
  KEY `ix_delegate_delegatedTo_5` (`delegated_to_id`),
  CONSTRAINT `fk_delegate_delegatedTo_5` FOREIGN KEY (`delegated_to_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_delegate_delegator_4` FOREIGN KEY (`delegator_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.delegate: ~0 rows (approximately)
/*!40000 ALTER TABLE `delegate` DISABLE KEYS */;
/*!40000 ALTER TABLE `delegate` ENABLE KEYS */;


-- Dumping structure for table timemgmt.department
CREATE TABLE IF NOT EXISTS `department` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.department: ~0 rows (approximately)
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` (`id`, `name`) VALUES
	(4, 'cool');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;


-- Dumping structure for table timemgmt.feedback
CREATE TABLE IF NOT EXISTS `feedback` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `feedback_date` datetime DEFAULT NULL,
  `feedback` longtext,
  `rating` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `manager_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_feedback_user_6` (`user_id`),
  KEY `ix_feedback_manager_7` (`manager_id`),
  CONSTRAINT `fk_feedback_manager_7` FOREIGN KEY (`manager_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_feedback_user_6` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.feedback: ~0 rows (approximately)
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;


-- Dumping structure for table timemgmt.flexi_attribute
CREATE TABLE IF NOT EXISTS `flexi_attribute` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `model` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `uniqueid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.flexi_attribute: ~14 rows (approximately)
/*!40000 ALTER TABLE `flexi_attribute` DISABLE KEYS */;
INSERT INTO `flexi_attribute` (`id`, `model`, `type`, `name`, `uniqueid`) VALUES
	(1, 'models.UserFlexi', 'string', 'Name', 1),
	(2, 'models.UserFlexi', 'string', 'Address', 1),
	(3, 'models.UserFlexi', 'textarea', 'Address 1', 1),
	(4, 'models.UserFlexi', 'date', 'Address 2', 1),
	(5, 'models.UserFlexi', 'FILE', 'Doc', 1),
	(6, 'models.ProjectFlexi', 'string', 'address', 2),
	(7, 'models.ProjectFlexi', 'FILE', 'Doc', 2),
	(9, 'models.ClientFlexi', 'string', 'address', 3),
	(10, 'models.ClientFlexi', 'FILE', 'Doc', 3),
	(13, 'models.TaskFlexi', 'string', 'adress', 4),
	(14, 'models.TaskFlexi', 'FILE', 'doc', 5),
	(15, 'models.ProjectFlexi', 'textarea', 'sadas', 2),
	(16, 'models.UserFlexi', 'date', 'asdas', 1),
	(17, 'models.caseFlexi', 'FILE', 'Attachment', 6);
/*!40000 ALTER TABLE `flexi_attribute` ENABLE KEYS */;


-- Dumping structure for table timemgmt.leaves_credit
CREATE TABLE IF NOT EXISTS `leaves_credit` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `policy_name` varchar(50) NOT NULL DEFAULT '0',
  `companyobject_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_leaves_credit_company` (`companyobject_id`),
  CONSTRAINT `FK_leaves_credit_company` FOREIGN KEY (`companyobject_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Dumping data for table timemgmt.leaves_credit: ~2 rows (approximately)
/*!40000 ALTER TABLE `leaves_credit` DISABLE KEYS */;
INSERT INTO `leaves_credit` (`id`, `policy_name`, `companyobject_id`) VALUES
	(3, 'Annual Credit Policy', 2),
	(4, 'Pro rata basis', 1);
/*!40000 ALTER TABLE `leaves_credit` ENABLE KEYS */;


-- Dumping structure for table timemgmt.leave_balance
CREATE TABLE IF NOT EXISTS `leave_balance` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `employee_id` bigint(20) DEFAULT NULL,
  `leave_level_id` bigint(20) DEFAULT NULL,
  `balance` float NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_leave_balance_employee_8` (`employee_id`),
  KEY `ix_leave_balance_leaveLevel_9` (`leave_level_id`),
  CONSTRAINT `fk_leave_balance_employee_8` FOREIGN KEY (`employee_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_leave_balance_leaveLevel_9` FOREIGN KEY (`leave_level_id`) REFERENCES `leave_level` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.leave_balance: ~4 rows (approximately)
/*!40000 ALTER TABLE `leave_balance` DISABLE KEYS */;
INSERT INTO `leave_balance` (`id`, `employee_id`, `leave_level_id`, `balance`) VALUES
	(1, 2, 1, 5),
	(2, 3, 1, 7),
	(3, 4, 1, 9),
	(4, 5, 1, 11);
/*!40000 ALTER TABLE `leave_balance` ENABLE KEYS */;


-- Dumping structure for table timemgmt.leave_level
CREATE TABLE IF NOT EXISTS `leave_level` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `leave_type` varchar(255) DEFAULT NULL,
  `carry_forward` varchar(255) DEFAULT NULL,
  `leave_x_id` bigint(20) DEFAULT NULL,
  `role_leave_id` bigint(20) DEFAULT NULL,
  `last_update` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_leave_level_leaveX_10` (`leave_x_id`),
  KEY `ix_leave_level_roleLeave_11` (`role_leave_id`),
  CONSTRAINT `fk_leave_level_leaveX_10` FOREIGN KEY (`leave_x_id`) REFERENCES `leave_x` (`id`),
  CONSTRAINT `fk_leave_level_roleLeave_11` FOREIGN KEY (`role_leave_id`) REFERENCES `role_leave` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.leave_level: ~0 rows (approximately)
/*!40000 ALTER TABLE `leave_level` DISABLE KEYS */;
INSERT INTO `leave_level` (`id`, `leave_type`, `carry_forward`, `leave_x_id`, `role_leave_id`, `last_update`) VALUES
	(1, 'Sick', 'YES', 1, NULL, '2015-04-09 16:48:45');
/*!40000 ALTER TABLE `leave_level` ENABLE KEYS */;


-- Dumping structure for table timemgmt.leave_x
CREATE TABLE IF NOT EXISTS `leave_x` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_leave_x_company_12` (`company_id`),
  CONSTRAINT `fk_leave_x_company_12` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.leave_x: ~0 rows (approximately)
/*!40000 ALTER TABLE `leave_x` DISABLE KEYS */;
INSERT INTO `leave_x` (`id`, `company_id`) VALUES
	(1, 2);
/*!40000 ALTER TABLE `leave_x` ENABLE KEYS */;


-- Dumping structure for table timemgmt.mail_setting
CREATE TABLE IF NOT EXISTS `mail_setting` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `host_name` varchar(255) DEFAULT NULL,
  `port_number` varchar(255) DEFAULT NULL,
  `ssl_port` tinyint(1) DEFAULT '0',
  `tls_port` tinyint(1) DEFAULT '0',
  `user_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `company_object_id` bigint(20) DEFAULT NULL,
  `last_update` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_mail_setting_companyObject_13` (`company_object_id`),
  CONSTRAINT `fk_mail_setting_companyObject_13` FOREIGN KEY (`company_object_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.mail_setting: ~2 rows (approximately)
/*!40000 ALTER TABLE `mail_setting` DISABLE KEYS */;
INSERT INTO `mail_setting` (`id`, `host_name`, `port_number`, `ssl_port`, `tls_port`, `user_name`, `password`, `company_object_id`, `last_update`) VALUES
	(4, 'harsh', '11', NULL, NULL, 'dhairyashil.bankar@gmail.com', 'abcd123', 1, '2014-02-25 10:23:21'),
	(5, NULL, NULL, NULL, NULL, 'google@ggg.com', NULL, 2, '2015-02-23 11:18:10');
/*!40000 ALTER TABLE `mail_setting` ENABLE KEYS */;


-- Dumping structure for table timemgmt.notification
CREATE TABLE IF NOT EXISTS `notification` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `setting_as_json` longtext,
  `company_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_notification_company_14` (`company_id`),
  CONSTRAINT `fk_notification_company_14` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.notification: ~2 rows (approximately)
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` (`id`, `setting_as_json`, `company_id`) VALUES
	(4, NULL, 1),
	(5, NULL, 2);
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;


-- Dumping structure for table timemgmt.organization
CREATE TABLE IF NOT EXISTS `organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `organization_name` varchar(255) DEFAULT NULL,
  `organization_type` varchar(255) DEFAULT NULL,
  `organization_location` varchar(255) DEFAULT NULL,
  `organization_profile_url` varchar(255) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `parent` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.organization: ~16 rows (approximately)
/*!40000 ALTER TABLE `organization` DISABLE KEYS */;
INSERT INTO `organization` (`id`, `organization_name`, `organization_type`, `organization_location`, `organization_profile_url`, `company_id`, `parent`) VALUES
	(13, 'aaa', 'aaa', 'aaa', 'org13.jpg', 2, NULL),
	(32, 'tttt55yyyy', 'yyyyy', 'uuuu', 'org2_32.jpg', 2, 13),
	(35, 'ttt1', 'yyy555', 'kkkk6', 'org2_35.jpg', 2, 13),
	(36, 'ttt1', 'yyy1', 'kkkk1', 'org2_36.jpg', 2, 13),
	(37, '2222', '3333', '4444 ffffff ddddddd sssss', 'org2_37.jpg', 2, 13),
	(38, '777', '777', '777', 'org2_38.jpg', 2, 13),
	(39, 'dfg', 'dfg', 'cvb', 'org2_39.jpg', 2, 13),
	(41, 'dfg65', 'dfg56', 'cvb86', 'org2_41.jpg', 2, 13),
	(43, 'dfg651', 'dfg56g', 'cvb86', 'org2_43.jpg', 2, 13),
	(51, 'dfgfgh', 'trjyuj', 'ujuj', 'org2_51.jpg', 2, 32),
	(52, 'dfg65', 'sas', 'asd', 'org2_52.jpg', 2, 43),
	(53, 'ttt2', 'fgfg', 'fgfg', 'org2_53.jpg', 2, 52),
	(54, 't66', 'y66', 'y77', 'org2_54.jpg', 2, 36),
	(55, 'dddde', 'ddddde', 'ddddddd eeeeee dddd', 'org2_55.jpg', 2, 36),
	(56, 'uuu', 'jjjjjjj', 'ooooooo', 'org2_56.jpg', 2, 54),
	(57, 'uuii', 'uuii', 'uuuii', 'org2_57.jpg', 2, 51);
/*!40000 ALTER TABLE `organization` ENABLE KEYS */;


-- Dumping structure for table timemgmt.project
CREATE TABLE IF NOT EXISTS `project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `client_name_id` bigint(20) DEFAULT NULL,
  `project_name` varchar(255) DEFAULT NULL,
  `project_code` varchar(255) DEFAULT NULL,
  `project_description` varchar(255) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `budget` double DEFAULT NULL,
  `currency` varchar(6) DEFAULT NULL,
  `efforts` int(11) DEFAULT NULL,
  `project_manager_id` bigint(20) DEFAULT NULL,
  `company_obj_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_project_clientName_15` (`client_name_id`),
  KEY `ix_project_projectManager_16` (`project_manager_id`),
  KEY `ix_project_companyObj_17` (`company_obj_id`),
  CONSTRAINT `fk_project_clientName_15` FOREIGN KEY (`client_name_id`) REFERENCES `client` (`id`),
  CONSTRAINT `fk_project_companyObj_17` FOREIGN KEY (`company_obj_id`) REFERENCES `company` (`id`),
  CONSTRAINT `fk_project_projectManager_16` FOREIGN KEY (`project_manager_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.project: ~2 rows (approximately)
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` (`id`, `client_name_id`, `project_name`, `project_code`, `project_description`, `start_date`, `end_date`, `budget`, `currency`, `efforts`, `project_manager_id`, `company_obj_id`) VALUES
	(1, 1, 'Aptitude Test 2', 'AT-2', 'exam 2', '2014-01-20 00:00:00', '2014-02-10 00:00:00', 123456, 'INR', 2, 5, 1),
	(2, 1, 'hkj', '1', 'Test', '2014-02-04 00:00:00', '2014-02-27 00:00:00', 568, 'INR', 769, 7, 1);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;


-- Dumping structure for table timemgmt.projectclass
CREATE TABLE IF NOT EXISTS `projectclass` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_types` varchar(50) DEFAULT NULL,
  `project_description` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.projectclass: ~9 rows (approximately)
/*!40000 ALTER TABLE `projectclass` DISABLE KEYS */;
INSERT INTO `projectclass` (`id`, `project_types`, `project_description`) VALUES
	(4, 'time', 'iuhyuiy sdfsd'),
	(6, 'MNT', 'sf s dfsdf sdfsdfdsfds'),
	(7, 'infosys', 'infosys project'),
	(8, 'constrution', 'this is construction'),
	(9, 'dd', 'ddd'),
	(10, 'Project', 'Description'),
	(11, 'Car', 'bulid a car'),
	(12, 'Project xyzz', 'project abcd'),
	(13, 'Software Project', 'Project Software time sheet');
/*!40000 ALTER TABLE `projectclass` ENABLE KEYS */;


-- Dumping structure for table timemgmt.projectclassnode
CREATE TABLE IF NOT EXISTS `projectclassnode` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_types` varchar(50) DEFAULT NULL,
  `project_description` varchar(50) DEFAULT NULL,
  `project_id_id` bigint(20) DEFAULT NULL,
  `parent_id` varchar(50) DEFAULT NULL,
  `project_color` varchar(50) DEFAULT NULL,
  `level` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_projectclassnode_projectclass` (`project_id_id`),
  CONSTRAINT `FK_projectclassnode_projectclass` FOREIGN KEY (`project_id_id`) REFERENCES `projectclass` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.projectclassnode: ~46 rows (approximately)
/*!40000 ALTER TABLE `projectclassnode` DISABLE KEYS */;
INSERT INTO `projectclassnode` (`id`, `project_types`, `project_description`, `project_id_id`, `parent_id`, `project_color`, `level`) VALUES
	(1, 'time', 'xx', 4, NULL, '#ff0000', '0'),
	(2, 'MNT', 'tttt', 6, NULL, NULL, '0'),
	(3, 'yogesh', 'cc', 4, '1', NULL, '1'),
	(5, 'yoge', 'qq', 4, '1', NULL, '1'),
	(6, 'ooo', 'oooo', 4, '5', NULL, '2'),
	(7, 'tttttt', 'tttt', 4, '5', NULL, '2'),
	(8, 'suit', 'sui', 4, '7', NULL, '3'),
	(9, '111', 'ww', 4, '1', NULL, '1'),
	(10, 'rrr', 'tt', 4, '9', NULL, '2'),
	(15, 'infosys', 'infosys project', 7, NULL, NULL, '0'),
	(19, 'constrution', 'this is construction', 8, NULL, NULL, '0'),
	(20, 'dsds', 'this is construction', 8, '19', NULL, '1'),
	(21, 'dsdsdsds', 'this is constructiondsds', 8, '19', NULL, '1'),
	(32, 'gfdg', 'fdsf', 4, '1', NULL, '1'),
	(33, 'AAAZZZZ12', 'AAAASSSSS12', 7, '15', NULL, '1'),
	(38, 'ssss', 'sss', 8, '19', NULL, '1'),
	(39, 'ddd', 'ddd', 7, '15', NULL, '1'),
	(45, 'ffytr', 'fff', 7, '15', NULL, '1'),
	(46, 'rtyu', 'sss', 6, '2', NULL, '1'),
	(48, 'mmmm', 'mmmm', 7, '15', NULL, '1'),
	(50, 'zzz', 'zz', 6, '2', NULL, '1'),
	(52, 'qqq', 'wwww123', 7, '39', NULL, '2'),
	(53, 'rrrr', 'tttttt', 7, '39', NULL, '2'),
	(54, 'ggg', 'gggg', 4, '6', NULL, '3'),
	(55, 'sads', 'sdf', 4, '5', '#ffff80', '2'),
	(56, 'rrrrrrt', 'rtrtrtrt', 4, '3', NULL, '2'),
	(57, 'lklklk', 'klklk', 4, '7', '#ff80ff', '3'),
	(58, 'dd', 'ddd', 9, NULL, NULL, '0'),
	(59, 'qqq', 'qqq', 9, '58', '#8000ff', '1'),
	(60, 'www', 'www', 9, '59', '#ffff00', '2'),
	(61, 'rrr', 'rrr', 6, '50', '#00ffff', '2'),
	(62, 'Project', 'Description', 10, NULL, NULL, '0'),
	(63, 'P1`', 'D1', 10, '62', NULL, '1'),
	(64, 'P2', 'D2', 10, '62', NULL, '1'),
	(65, 'P3', 'D3', 10, '64', NULL, '2'),
	(66, 'sdfds', 'sdfds', 6, '61', NULL, '3'),
	(67, 'Car', 'bulid a car', 11, NULL, NULL, '0'),
	(68, 'wheel', 'car wheel', 11, '67', NULL, '1'),
	(69, 'body', 'car body', 11, '67', NULL, '1'),
	(70, 'engin', 'car engin', 11, '67', NULL, '1'),
	(71, 'Project xyzz', 'project abcd', 12, NULL, NULL, '0'),
	(72, 'Software Project', 'Project Software time sheet', 13, NULL, NULL, '0'),
	(73, 'desiging', 'Project Software desiging', 13, '72', NULL, '1'),
	(74, 'Coding', 'Project Software coding', 13, '72', NULL, '1'),
	(75, 'Testing', 'Project Software Testing', 13, '72', NULL, '1'),
	(76, 'MRF', 'MRF wheel', 11, '68', NULL, '2');
/*!40000 ALTER TABLE `projectclassnode` ENABLE KEYS */;


-- Dumping structure for table timemgmt.projectclassnodeattribut
CREATE TABLE IF NOT EXISTS `projectclassnodeattribut` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `value` varchar(50) DEFAULT NULL,
  `projectnode_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK__projectclassnode` (`projectnode_id`),
  CONSTRAINT `FK__projectclassnode` FOREIGN KEY (`projectnode_id`) REFERENCES `projectclassnode` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.projectclassnodeattribut: ~51 rows (approximately)
/*!40000 ALTER TABLE `projectclassnodeattribut` DISABLE KEYS */;
INSERT INTO `projectclassnodeattribut` (`id`, `name`, `type`, `value`, `projectnode_id`) VALUES
	(4, 'rrrr', 'String', NULL, 10),
	(21, 'ds', 'String', NULL, 20),
	(33, 'dsf', 'String', NULL, 32),
	(43, 'sss', 'Radio', 'grgrgrgrgrg', 38),
	(44, 'eeee', 'Integer', NULL, 38),
	(45, 'ddd1', 'String', NULL, 39),
	(46, 'ddd2', 'Checkbox', 'ddd\nfffff', 39),
	(47, 'YYYYY!@@@12', 'Integer', NULL, 33),
	(48, '12', 'Dropdown', 'eeeee', 33),
	(54, 'fds', 'Integer', NULL, 45),
	(58, 'mmm', 'Dropdown', 'mmmmm', 48),
	(60, 'zz', 'Integer', 'zzz', 50),
	(64, 'uikk', 'Integer', NULL, 53),
	(65, 'www', 'Date', NULL, 52),
	(74, '44', 'String', NULL, 54),
	(76, 'rtrtr', 'Integer', NULL, 56),
	(77, 'erer', 'Radio', 'eredfd\ndfdfd', 56),
	(78, 'klklk', 'Integer', NULL, 57),
	(80, 'sdf', 'Integer', NULL, 55),
	(84, 'Date', 'Date', NULL, 46),
	(85, 'Name', 'String', NULL, 46),
	(86, 'Skill', 'Dropdown', 'java\nSQL', 46),
	(87, 'Edu', 'Checkbox', 'MCA\nBE', 46),
	(88, 'Name', 'String', NULL, 59),
	(89, 'Last Name', 'Integer', NULL, 60),
	(90, 'rrr', 'String', NULL, 61),
	(91, 'N1', 'String', NULL, 63),
	(92, 'N2', 'Integer', NULL, 63),
	(93, 'N2', 'String', NULL, 64),
	(94, 'N3', 'Radio', 'M\nP', 64),
	(95, 'p3', 'Checkbox', 'abc\nxyz', 65),
	(96, 'Name', 'String', NULL, 66),
	(97, 'age', 'Integer', NULL, 66),
	(99, 'body', 'Integer', NULL, 69),
	(100, 'engin', 'String', NULL, 70),
	(104, 'Start Date', 'Date', NULL, 72),
	(105, 'End Date', 'Date', NULL, 72),
	(106, 'Description', 'String', NULL, 72),
	(107, 'Start Date', 'Date', NULL, 73),
	(108, 'End Date', 'Date', NULL, 73),
	(109, 'Description', 'String', NULL, 73),
	(110, 'Start', 'Date', NULL, 74),
	(111, 'End', 'Date', NULL, 74),
	(112, 'Technology', 'String', NULL, 74),
	(113, 'Start Date', 'Date', NULL, 75),
	(114, 'End Date', 'Date', NULL, 75),
	(115, 'Testing Type', 'Radio', 'manual\nautomation', 75),
	(116, 'wheel', 'String', NULL, 68),
	(117, 'company', 'Checkbox', 'Honda\nHiro', 68),
	(118, 'tech', 'Radio', 'TCS\nMNT', 68),
	(119, 'Price', 'Integer', NULL, 76);
/*!40000 ALTER TABLE `projectclassnodeattribut` ENABLE KEYS */;


-- Dumping structure for table timemgmt.projectinstance
CREATE TABLE IF NOT EXISTS `projectinstance` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_name` varchar(50) DEFAULT NULL,
  `project_description` varchar(50) DEFAULT NULL,
  `projectid` bigint(20) DEFAULT NULL,
  `client_id` bigint(20) DEFAULT NULL,
  `client_name` varchar(50) DEFAULT NULL,
  `start_date` varchar(50) DEFAULT NULL,
  `end_date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.projectinstance: ~6 rows (approximately)
/*!40000 ALTER TABLE `projectinstance` DISABLE KEYS */;
INSERT INTO `projectinstance` (`id`, `project_name`, `project_description`, `projectid`, `client_id`, `client_name`, `start_date`, `end_date`) VALUES
	(1, 'maruti', 'ww ww', 11, 1, 'abcd', '02-02-2016', '04-04-2016'),
	(2, 'swift', 'ttt yyyy', 11, 1, 'abcd', '2015-02-03', '2015-06-10'),
	(23, 'farrari', 'rr rrr', 11, 1, 'abcd', NULL, NULL),
	(27, 'yogesh', 'fff ggg g g g g g g g g g  g g g g g', 6, 1, 'abcd', NULL, NULL),
	(36, 'Client', 'Client in India', 13, 1, 'abcd', NULL, NULL),
	(37, 'sdfs', 'sdf', 6, 1, 'abcd', NULL, NULL);
/*!40000 ALTER TABLE `projectinstance` ENABLE KEYS */;


-- Dumping structure for table timemgmt.projectinstancenode
CREATE TABLE IF NOT EXISTS `projectinstancenode` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Projectclassnode_id` bigint(20) DEFAULT NULL,
  `projecttypeid` bigint(20) DEFAULT NULL,
  `projectinstanceid` bigint(20) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `task_compilation` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_projectinstancenode_projectclassnodeattribut` (`Projectclassnode_id`),
  KEY `FK_projectinstancenode_projectinstance` (`projectinstanceid`),
  CONSTRAINT `FK_projectinstancenode_projectclassnodeattribut` FOREIGN KEY (`Projectclassnode_id`) REFERENCES `projectclassnode` (`id`),
  CONSTRAINT `FK_projectinstancenode_projectinstance` FOREIGN KEY (`projectinstanceid`) REFERENCES `projectinstance` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.projectinstancenode: ~10 rows (approximately)
/*!40000 ALTER TABLE `projectinstancenode` DISABLE KEYS */;
INSERT INTO `projectinstancenode` (`id`, `Projectclassnode_id`, `projecttypeid`, `projectinstanceid`, `start_date`, `end_date`, `task_compilation`) VALUES
	(2, 69, 11, 1, NULL, NULL, 0),
	(11, 68, 11, 23, NULL, NULL, 54),
	(12, 72, 13, 36, '2015-02-02', '2017-07-02', 0),
	(13, 73, 13, 36, '2015-03-03', '2015-04-04', 0),
	(15, 68, 11, 2, '2015-05-04', '2015-06-05', 0),
	(16, 67, 11, 2, '2015-02-03', '2015-06-10', 0),
	(17, 69, 11, 2, '2015-02-01', '2015-03-03', 0),
	(18, 67, 11, 1, '2016-02-02', '2016-04-04', 0),
	(19, 68, 11, 1, '2015-03-03', '2015-03-03', 0),
	(20, 69, 11, 23, '2015-03-03', '2015-04-03', 0);
/*!40000 ALTER TABLE `projectinstancenode` ENABLE KEYS */;


-- Dumping structure for table timemgmt.project_attachment
CREATE TABLE IF NOT EXISTS `project_attachment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `doc_path` varchar(255) DEFAULT NULL,
  `doc_name` varchar(50) DEFAULT NULL,
  `doc_date` date DEFAULT NULL,
  `projectinstanceid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.project_attachment: ~5 rows (approximately)
/*!40000 ALTER TABLE `project_attachment` DISABLE KEYS */;
INSERT INTO `project_attachment` (`id`, `doc_path`, `doc_name`, `doc_date`, `projectinstanceid`) VALUES
	(10, 'D:\\dev\\time-images\\attachment\\7579_09022015_23.pdf', '7579_09022015.pdf', '2015-04-13', 11),
	(11, 'D:\\dev\\time-images\\attachment\\bootstrap-datepicker (1)_23.js', 'bootstrap-datepicker (1).js', '2015-04-13', 11),
	(12, 'D:\\dev\\time-images\\attachment\\CNPJ_23.txt', 'CNPJ.txt', '2015-04-13', 20),
	(13, 'D:\\dev\\time-images\\attachment\\Yogesh P_23.docx', 'Yogesh P.docx', '2015-04-13', 20),
	(14, 'D:\\dev\\time-images\\attachment\\IE_20.txt', 'IE.txt', '2015-04-13', 20);
/*!40000 ALTER TABLE `project_attachment` ENABLE KEYS */;


-- Dumping structure for table timemgmt.project_comment
CREATE TABLE IF NOT EXISTS `project_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_comment` text,
  `commet_date` date DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `projectinstanceid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK__user` (`user_id`),
  CONSTRAINT `FK__user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.project_comment: ~4 rows (approximately)
/*!40000 ALTER TABLE `project_comment` DISABLE KEYS */;
INSERT INTO `project_comment` (`id`, `project_comment`, `commet_date`, `user_id`, `projectinstanceid`) VALUES
	(2, 'sd sd sds ds sdsds ds ds', '2015-04-12', 2, 11),
	(3, 'hi Bye hi Byehi Byehi Byehi Byehi Byehi Byehi Byehi Byehi Byehi Byehi Byehi Byehi Byehi Byehi Byehi Byehi Byehi Byehi Byehi Byehi Byehi Byehi Byehi Byehi Byehi Byehi Byehi Byehi Bye', '2015-04-13', 2, 11),
	(4, 'hi yogesh bye yogesh', '2015-04-13', 2, 20),
	(5, 'ggggg', '2015-04-13', 2, 20);
/*!40000 ALTER TABLE `project_comment` ENABLE KEYS */;


-- Dumping structure for table timemgmt.project_flexi
CREATE TABLE IF NOT EXISTS `project_flexi` (
  `project_id` bigint(20) NOT NULL,
  `flexi_id` bigint(20) NOT NULL,
  `value` varchar(1255) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `projectflexi` (`project_id`),
  KEY `projectflexi2` (`flexi_id`),
  CONSTRAINT `projectflexi` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  CONSTRAINT `projectflexi2` FOREIGN KEY (`flexi_id`) REFERENCES `flexi_attribute` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.project_flexi: ~0 rows (approximately)
/*!40000 ALTER TABLE `project_flexi` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_flexi` ENABLE KEYS */;


-- Dumping structure for table timemgmt.project_task
CREATE TABLE IF NOT EXISTS `project_task` (
  `project_id` bigint(20) NOT NULL,
  `task_id` bigint(20) NOT NULL,
  PRIMARY KEY (`project_id`,`task_id`),
  KEY `fk_project_task_task_02` (`task_id`),
  CONSTRAINT `fk_project_task_project_01` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  CONSTRAINT `fk_project_task_task_02` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.project_task: ~0 rows (approximately)
/*!40000 ALTER TABLE `project_task` DISABLE KEYS */;
INSERT INTO `project_task` (`project_id`, `task_id`) VALUES
	(1, 1),
	(2, 1);
/*!40000 ALTER TABLE `project_task` ENABLE KEYS */;


-- Dumping structure for table timemgmt.project_user
CREATE TABLE IF NOT EXISTS `project_user` (
  `project_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`project_id`,`user_id`),
  KEY `fk_project_user_user_02` (`user_id`),
  CONSTRAINT `fk_project_user_project_01` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  CONSTRAINT `fk_project_user_user_02` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.project_user: ~6 rows (approximately)
/*!40000 ALTER TABLE `project_user` DISABLE KEYS */;
INSERT INTO `project_user` (`project_id`, `user_id`) VALUES
	(2, 3),
	(2, 4),
	(1, 5),
	(1, 6),
	(2, 7),
	(1, 9);
/*!40000 ALTER TABLE `project_user` ENABLE KEYS */;


-- Dumping structure for table timemgmt.role
CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) DEFAULT NULL,
  `role_description` varchar(50) DEFAULT NULL,
  `parent_id` varchar(50) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_role_company` (`company_id`),
  CONSTRAINT `FK_role_company` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.role: ~6 rows (approximately)
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`id`, `role_name`, `role_description`, `parent_id`, `company_id`) VALUES
	(1, 'root', 'rootDescription', NULL, NULL),
	(10, '1234567', '96667777777777', '1', NULL),
	(11, 'ghg', 'fgff', '1', 2),
	(13, '77', '888', '10', 2),
	(14, 'yyyy', 'yyyy', '10', 2),
	(15, 'SANGU', 'SANGU SANGU1', '11', 2);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;


-- Dumping structure for table timemgmt.role_leave
CREATE TABLE IF NOT EXISTS `role_leave` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) DEFAULT NULL,
  `role_level_id` bigint(20) DEFAULT NULL,
  `leave_level_id` bigint(20) DEFAULT NULL,
  `total_leave` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_role_leave_company_18` (`company_id`),
  KEY `ix_role_leave_roleLevel_19` (`role_level_id`),
  KEY `ix_role_leave_leaveLevel_20` (`leave_level_id`),
  CONSTRAINT `fk_role_leave_company_18` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `fk_role_leave_leaveLevel_20` FOREIGN KEY (`leave_level_id`) REFERENCES `leave_level` (`id`),
  CONSTRAINT `fk_role_leave_roleLevel_19` FOREIGN KEY (`role_level_id`) REFERENCES `role_level` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.role_leave: ~20 rows (approximately)
/*!40000 ALTER TABLE `role_leave` DISABLE KEYS */;
INSERT INTO `role_leave` (`id`, `company_id`, `role_level_id`, `leave_level_id`, `total_leave`) VALUES
	(1, 2, 1, 1, 0),
	(2, 2, 2, 1, 0),
	(3, 2, 3, 1, 0),
	(4, 2, 4, 1, 0),
	(5, 2, 5, 1, 0),
	(6, 2, 6, 1, 0),
	(7, 2, 7, 1, 0),
	(8, 2, 8, 1, 0),
	(9, 2, 9, 1, 0),
	(10, 2, 10, 1, 0),
	(11, 2, 11, 1, 0),
	(12, 2, 12, 1, 0),
	(13, 2, 13, 1, 0),
	(14, 2, 14, 1, 0),
	(15, 2, 15, 1, 0),
	(16, 2, 16, 1, 0),
	(17, 2, 17, 1, 0),
	(18, 2, 18, 1, 0),
	(19, 2, 19, 1, 0),
	(20, 2, 20, 1, 0);
/*!40000 ALTER TABLE `role_leave` ENABLE KEYS */;


-- Dumping structure for table timemgmt.role_level
CREATE TABLE IF NOT EXISTS `role_level` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_x_id` bigint(20) DEFAULT NULL,
  `role_level` int(11) DEFAULT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  `reporting_to` varchar(255) DEFAULT NULL,
  `final_approval` varchar(255) DEFAULT NULL,
  `permissions` varchar(700) DEFAULT NULL,
  `last_update` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_role_level_roleX_21` (`role_x_id`),
  CONSTRAINT `fk_role_level_roleX_21` FOREIGN KEY (`role_x_id`) REFERENCES `role_x` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.role_level: ~20 rows (approximately)
/*!40000 ALTER TABLE `role_level` DISABLE KEYS */;
INSERT INTO `role_level` (`id`, `role_x_id`, `role_level`, `role_name`, `reporting_to`, `final_approval`, `permissions`, `last_update`) VALUES
	(1, 1, 9, 'Subject Mater Expert', 'Senior Subject Mater Expert', 'Team Lead', 'Home|ManageUser|ManageClient|ManageProject|ManageTask|Delegate|FeedBackCreate|FeedBackView|RolePermissions|UserPermissions|UserRequest|MyBucket|LeaveBucket|TeamRate|ProjectReport|Mail|Notification|DefineRoles|OrgHierarchy|', '2014-02-25 13:46:45'),
	(2, 1, 6, 'Senior Subject Mater Expert', 'Team Lead', 'Project Manager', 'ManageUser|ManageClient|ManageProject|ManageTask|Delegate|FeedBackCreate|RolePermissions|UserPermissions|UserRequest|MyBucket|LeaveBucket|TeamRate|ProjectReport|Mail|Notification|DefineRoles|OrgHierarchy|', '2014-02-25 13:46:45'),
	(3, 1, 2, 'Team Lead', 'Expert', 'Project Manager', 'ManageUser|ManageClient|ManageProject|ManageTask|Delegate|RolePermissions|UserPermissions|UserRequest|TeamRate|ProjectReport|Mail|Notification|DefineRoles|OrgHierarchy|', '2014-02-25 13:46:45'),
	(4, 1, 4, 'Expert', 'Senior Expert', 'Director', 'Home|', '2014-02-25 13:46:45'),
	(5, 1, 4, 'Project Manager', 'Director', 'CEO', 'ManageUser|ManageClient|ManageProject|ManageTask|Delegate|Mail|Notification|DefineRoles|OrgHierarchy|', '2014-02-25 13:46:45'),
	(6, 1, 5, 'Senior Expert', 'Director', 'CEO', 'FeedBackCreate|FeedBackView|', '2014-02-25 13:46:45'),
	(7, 1, 6, 'Director', 'CEO', 'CEO', 'ManageUser|ManageClient|ManageProject|ManageTask|UserRequest|Mail|Notification|DefineRoles|OrgHierarchy|', '2014-02-25 13:46:45'),
	(8, 1, 7, 'CEO', 'CEO', 'CEO', 'FeedBackCreate|FeedBackView|', '2014-02-25 13:46:45'),
	(9, 1, 8, 'Owner', 'Subject Mater Expert', 'Subject Mater Expert', 'ManageUser|ManageClient|ManageProject|ManageTask|ApplyLeave|CreateTimesheet|SearchTimesheet|FeedBackCreate|FeedBackView|RolePermissions|UserPermissions|UserRequest|MyBucket|LeaveBucket|TeamRate|ProjectReport|Mail|Notification|DefineRoles|OrgHierarchy|', '2014-02-25 13:46:45'),
	(10, 1, 9, 'Co-Owner', 'Subject Mater Expert', 'Subject Mater Expert', 'ManageTask|', '2014-02-25 13:46:45'),
	(11, 1, 0, '', 'Subject Mater Expert', 'Subject Mater Expert', '', '2014-02-25 13:46:45'),
	(12, 1, 0, '', 'Subject Mater Expert', 'Subject Mater Expert', '', '2014-02-25 13:46:45'),
	(13, 1, 0, '', 'Subject Mater Expert', 'Subject Mater Expert', '', '2014-02-25 13:46:45'),
	(14, 1, 0, '', 'Subject Mater Expert', 'Subject Mater Expert', '', '2014-02-25 13:46:45'),
	(15, 1, 0, '', 'Subject Mater Expert', 'Subject Mater Expert', '', '2014-02-25 13:46:45'),
	(16, 1, 0, '', 'Subject Mater Expert', 'Subject Mater Expert', '', '2014-02-25 13:46:45'),
	(17, 2, 0, 'Software Engineer', 'Senior Software Engineer', 'Senior Software Engineer', '', '2015-02-23 11:22:36'),
	(18, 2, 1, 'Senior Software Engineer', 'Group Lead', 'Group Lead', '', '2015-02-23 11:22:36'),
	(19, 2, 2, 'Group Lead', 'Project Manager', 'Project Manager', 'Home|ManageUser|ManageClient|ManageProject|ManageTask|CreateTimesheet|SearchTimesheet|CreateTimesheets|Today|Week|Month|Holiday|TodayAll|WeekReport|Delegate|RolePermissions|UserPermissions|UserRequest|MyBucket|LeaveBucket|TeamRate|ProjectReport|Mail|Notification|DefineRoles|DefineFlexiAttribute|OrgHierarchy|', '2015-04-10 17:53:03'),
	(20, 2, 3, 'Project Manager', 'Project Manager', 'Project Manager', 'ManageUser|ManageClient|ManageProject|ManageTask|ApplyLeave|CreateTimesheet|SearchTimesheet|FeedBackCreate|FeedBackView|UserRequest|MyBucket|LeaveBucket|TeamRate|ProjectReport|', '2015-02-23 11:29:12');
/*!40000 ALTER TABLE `role_level` ENABLE KEYS */;


-- Dumping structure for table timemgmt.role_x
CREATE TABLE IF NOT EXISTS `role_x` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_role_x_company_22` (`company_id`),
  CONSTRAINT `fk_role_x_company_22` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.role_x: ~2 rows (approximately)
/*!40000 ALTER TABLE `role_x` DISABLE KEYS */;
INSERT INTO `role_x` (`id`, `company_id`) VALUES
	(1, 1),
	(2, 2);
/*!40000 ALTER TABLE `role_x` ENABLE KEYS */;


-- Dumping structure for table timemgmt.saveattributes
CREATE TABLE IF NOT EXISTS `saveattributes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `attribut_value` varchar(50) DEFAULT NULL,
  `projectattrid` bigint(20) DEFAULT NULL,
  `projectinstancenode_id` bigint(20) DEFAULT NULL,
  `attribut_name` varchar(50) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_saveattributes_projectinstancenode` (`projectinstancenode_id`),
  CONSTRAINT `FK_saveattributes_projectinstancenode` FOREIGN KEY (`projectinstancenode_id`) REFERENCES `projectinstancenode` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.saveattributes: ~15 rows (approximately)
/*!40000 ALTER TABLE `saveattributes` DISABLE KEYS */;
INSERT INTO `saveattributes` (`id`, `attribut_value`, `projectattrid`, `projectinstancenode_id`, `attribut_name`, `type`) VALUES
	(2, 'MRF', 99, 2, NULL, NULL),
	(11, '', 98, 11, NULL, NULL),
	(12, '2-4-2015', 104, 12, NULL, NULL),
	(13, '2-5-2015', 105, 12, NULL, NULL),
	(14, '"Time Software" project', 106, 12, NULL, NULL),
	(15, '12-09-2015', 107, 13, NULL, NULL),
	(16, '15-10-2015', 108, 13, NULL, NULL),
	(17, 'Flash', 109, 13, NULL, NULL),
	(19, 'abcdYogesh', 116, 15, NULL, NULL),
	(20, 'Hiro,', 117, 15, NULL, NULL),
	(21, 'TCS', 118, 15, NULL, NULL),
	(22, 'suit', 99, 17, NULL, NULL),
	(23, 'r666', 116, 19, NULL, NULL),
	(24, 'Hiro,', 117, 19, NULL, NULL),
	(25, 'MNT', 118, 19, NULL, NULL);
/*!40000 ALTER TABLE `saveattributes` ENABLE KEYS */;


-- Dumping structure for table timemgmt.supplier
CREATE TABLE IF NOT EXISTS `supplier` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `supplier_name` varchar(255) DEFAULT NULL,
  `phone_no` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fax` int(11) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `pin` varchar(255) DEFAULT NULL,
  `contact_name` varchar(255) DEFAULT NULL,
  `contact_phone` varchar(255) DEFAULT NULL,
  `contact_email` varchar(255) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_supplier_company_3` (`company_id`),
  CONSTRAINT `fk_supplier_company_3` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.supplier: ~0 rows (approximately)
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` (`id`, `supplier_name`, `phone_no`, `email`, `fax`, `address`, `street`, `city`, `country`, `pin`, `contact_name`, `contact_phone`, `contact_email`, `company_id`) VALUES
	(1, 'kkkk', '98909', 'jhjhj@uyt.com', 99, 'kjjkj', 'kjnj', 'kjnkj', 'kjloiiu', '7788', 'yyyyy', '00000', 'vgvg@gfv.com', 2);
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;


-- Dumping structure for table timemgmt.supplier_flexi
CREATE TABLE IF NOT EXISTS `supplier_flexi` (
  `supplier_id` bigint(20) NOT NULL,
  `flexi_id` bigint(20) NOT NULL,
  `value` varchar(1255) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `supplierflexi` (`supplier_id`),
  KEY `supplierflexi2` (`flexi_id`),
  CONSTRAINT `supplierflexi` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`),
  CONSTRAINT `supplierflexi2` FOREIGN KEY (`flexi_id`) REFERENCES `flexi_attribute` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.supplier_flexi: ~0 rows (approximately)
/*!40000 ALTER TABLE `supplier_flexi` DISABLE KEYS */;
/*!40000 ALTER TABLE `supplier_flexi` ENABLE KEYS */;


-- Dumping structure for table timemgmt.task
CREATE TABLE IF NOT EXISTS `task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(255) DEFAULT NULL,
  `task_code` varchar(255) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `effort` int(11) DEFAULT NULL,
  `is_billable` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.task: ~2 rows (approximately)
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` (`id`, `task_name`, `task_code`, `start_date`, `end_date`, `effort`, `is_billable`) VALUES
	(1, 'CSS', 't-2', '2014-01-29 00:00:00', '2014-01-30 00:00:00', 1, 'Yes'),
	(2, 'Billing 1', 'PS 1', '2014-01-30 00:00:00', '2014-02-28 00:00:00', 1, 'Yes');
/*!40000 ALTER TABLE `task` ENABLE KEYS */;


-- Dumping structure for table timemgmt.task_flexi
CREATE TABLE IF NOT EXISTS `task_flexi` (
  `task_id` bigint(20) NOT NULL,
  `flexi_id` bigint(20) NOT NULL,
  `value` varchar(1255) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `taskflexi` (`task_id`),
  KEY `taskflexi2` (`flexi_id`),
  CONSTRAINT `taskflexi` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`),
  CONSTRAINT `taskflexi2` FOREIGN KEY (`flexi_id`) REFERENCES `flexi_attribute` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.task_flexi: ~0 rows (approximately)
/*!40000 ALTER TABLE `task_flexi` DISABLE KEYS */;
/*!40000 ALTER TABLE `task_flexi` ENABLE KEYS */;


-- Dumping structure for table timemgmt.task_project
CREATE TABLE IF NOT EXISTS `task_project` (
  `task_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  PRIMARY KEY (`task_id`,`project_id`),
  KEY `fk_task_project_project_02` (`project_id`),
  CONSTRAINT `fk_task_project_project_02` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  CONSTRAINT `fk_task_project_task_01` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.task_project: ~2 rows (approximately)
/*!40000 ALTER TABLE `task_project` DISABLE KEYS */;
INSERT INTO `task_project` (`task_id`, `project_id`) VALUES
	(1, 1),
	(1, 2),
	(2, 2);
/*!40000 ALTER TABLE `task_project` ENABLE KEYS */;


-- Dumping structure for table timemgmt.timesheet
CREATE TABLE IF NOT EXISTS `timesheet` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `week_of_year` int(11) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `timesheet_with_id` bigint(20) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `process_instance_id` varchar(255) DEFAULT NULL,
  `tid` varchar(255) DEFAULT NULL,
  `last_update_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_timesheet_user_23` (`user_id`),
  KEY `ix_timesheet_timesheetWith_24` (`timesheet_with_id`),
  CONSTRAINT `fk_timesheet_timesheetWith_24` FOREIGN KEY (`timesheet_with_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_timesheet_user_23` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.timesheet: ~10 rows (approximately)
/*!40000 ALTER TABLE `timesheet` DISABLE KEYS */;
INSERT INTO `timesheet` (`id`, `user_id`, `status`, `week_of_year`, `year`, `timesheet_with_id`, `level`, `process_instance_id`, `tid`, `last_update_date`) VALUES
	(1, 1, 1, 5, 2014, 6, 2, '101', 'ae8e02b3-6577-4665-bb32-ba4ad109bc4b', '2014-01-29 00:00:00'),
	(2, 1, 3, 6, 2014, 5, 1, '201', 'b3be1ef1-934e-4369-ba56-852bdb2e2d61', '2014-02-03 00:00:00'),
	(3, 1, 0, 9, 2014, 9, 0, '416', 'b837f4dc-7d15-4705-ae34-f0c7c232ff88', '2014-03-01 00:00:00'),
	(4, 1, 0, 12, 2014, 9, 0, NULL, 'bf422fed-9c37-41d3-8980-1d2bca1af5b4', '2014-03-17 00:00:00'),
	(5, 4, 0, 12, 2015, 4, 0, NULL, '2bc9cee9-9ff4-4678-9960-36a35cbd6a1c', '2015-03-19 12:05:33'),
	(21, 4, 3, 13, 2015, 3, 0, NULL, NULL, '2015-04-03 13:16:47'),
	(22, 4, 0, 14, 2015, 3, 0, NULL, NULL, '2015-03-25 11:21:09'),
	(25, 4, 0, 11, 2015, 3, 0, NULL, NULL, '2015-03-25 12:24:38'),
	(26, 4, 0, 15, 2015, 4, 0, NULL, NULL, '2015-04-08 11:45:10'),
	(27, 3, 0, 15, 2015, 3, 0, NULL, NULL, '2015-04-10 11:19:06'),
	(28, 3, 0, 16, 2015, 3, 0, NULL, NULL, '2015-04-13 10:34:46');
/*!40000 ALTER TABLE `timesheet` ENABLE KEYS */;


-- Dumping structure for table timemgmt.timesheet_actual
CREATE TABLE IF NOT EXISTS `timesheet_actual` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `week_of_year` int(11) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `timesheet_with_id` bigint(20) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `process_instance_id` varchar(255) DEFAULT NULL,
  `tid` varchar(255) DEFAULT NULL,
  `last_update_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `timesheet_with_id` (`timesheet_with_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- Dumping data for table timemgmt.timesheet_actual: ~3 rows (approximately)
/*!40000 ALTER TABLE `timesheet_actual` DISABLE KEYS */;
INSERT INTO `timesheet_actual` (`id`, `user_id`, `status`, `week_of_year`, `year`, `timesheet_with_id`, `level`, `process_instance_id`, `tid`, `last_update_date`) VALUES
	(7, 4, 3, 14, 2015, 3, 0, NULL, NULL, '2015-04-03 18:48:45'),
	(8, 4, 3, 15, 2015, 3, 0, NULL, NULL, '2015-04-08 11:40:03'),
	(9, 3, 0, 15, 2015, 3, 0, NULL, NULL, '2015-04-10 11:18:06'),
	(10, 3, 0, 16, 2015, 3, 0, NULL, NULL, '2015-04-13 14:33:14');
/*!40000 ALTER TABLE `timesheet_actual` ENABLE KEYS */;


-- Dumping structure for table timemgmt.timesheet_days
CREATE TABLE IF NOT EXISTS `timesheet_days` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `day` varchar(255) DEFAULT NULL,
  `timesheet_date` date DEFAULT NULL,
  `time_from` varchar(100) DEFAULT NULL,
  `time_to` varchar(100) DEFAULT NULL,
  `work_minutes` int(20) DEFAULT NULL,
  `timesheet_row_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `timesheet_row_id` (`timesheet_row_id`)
) ENGINE=InnoDB AUTO_INCREMENT=141 DEFAULT CHARSET=utf8;

-- Dumping data for table timemgmt.timesheet_days: ~67 rows (approximately)
/*!40000 ALTER TABLE `timesheet_days` DISABLE KEYS */;
INSERT INTO `timesheet_days` (`id`, `day`, `timesheet_date`, `time_from`, `time_to`, `work_minutes`, `timesheet_row_id`) VALUES
	(43, 'monday', '2015-03-16', '11:00', '12:00', 60, 22),
	(44, 'tuesday', '2015-03-17', NULL, NULL, NULL, 22),
	(45, 'wednesday', '2015-03-18', NULL, NULL, NULL, 22),
	(46, 'thursday', '2015-03-19', '13:00', '14:00', 60, 22),
	(47, 'friday', '2015-03-20', NULL, NULL, NULL, 22),
	(48, 'saturday', '2015-03-21', NULL, NULL, NULL, 22),
	(49, 'sunday', '2015-03-22', NULL, NULL, NULL, 22),
	(57, 'monday', '2015-03-23', '11:00', '12:00', 60, 24),
	(58, 'tuesday', '2015-03-24', '12:00', '14:00', 120, 24),
	(59, 'wednesday', '2015-03-25', NULL, NULL, NULL, 24),
	(60, 'thursday', '2015-03-26', '13:00', '14:00', 60, 24),
	(61, 'friday', '2015-03-27', NULL, NULL, NULL, 24),
	(62, 'saturday', '2015-03-28', NULL, NULL, NULL, 24),
	(63, 'sunday', '2015-03-29', NULL, NULL, NULL, 24),
	(64, 'monday', '2015-03-30', '10:00', '11:00', 60, 25),
	(65, 'tuesday', '2015-03-31', NULL, NULL, NULL, 25),
	(66, 'wednesday', '2015-04-01', NULL, NULL, NULL, 25),
	(67, 'thursday', '2015-04-02', '13:00', '14:00', 60, 25),
	(68, 'friday', '2015-04-03', '12:00', '13:00', 60, 25),
	(69, 'saturday', '2015-04-04', NULL, NULL, NULL, 25),
	(70, 'sunday', '2015-04-05', '11:00', '12:00', 60, 25),
	(92, 'monday', '2015-03-09', '10:00', '11:00', 60, 29),
	(93, 'tuesday', '2015-03-10', NULL, NULL, NULL, 29),
	(94, 'wednesday', '2015-03-11', NULL, NULL, NULL, 29),
	(95, 'thursday', '2015-03-12', NULL, NULL, NULL, 29),
	(96, 'friday', '2015-03-13', NULL, NULL, NULL, 29),
	(97, 'saturday', '2015-03-14', NULL, NULL, NULL, 29),
	(98, 'sunday', '2015-03-15', NULL, NULL, NULL, 29),
	(106, 'monday', '2015-03-09', '11:00', '12:00', 60, 31),
	(107, 'tuesday', '2015-03-10', '13:25', '13:45', 20, 31),
	(108, 'wednesday', '2015-03-11', NULL, NULL, NULL, 31),
	(109, 'thursday', '2015-03-12', NULL, NULL, NULL, 31),
	(110, 'friday', '2015-03-13', NULL, NULL, NULL, 31),
	(111, 'saturday', '2015-03-14', NULL, NULL, NULL, 31),
	(112, 'sunday', '2015-03-15', NULL, NULL, NULL, 31),
	(113, 'monday', '2015-03-30', '11:00', '12:30', 90, 32),
	(114, 'tuesday', '2015-03-31', NULL, NULL, NULL, 32),
	(115, 'wednesday', '2015-04-01', NULL, NULL, NULL, 32),
	(116, 'thursday', '2015-04-02', NULL, NULL, NULL, 32),
	(117, 'friday', '2015-04-03', NULL, NULL, NULL, 32),
	(118, 'saturday', '2015-04-04', NULL, NULL, NULL, 32),
	(119, 'sunday', '2015-04-05', '13:00', '13:30', 30, 32),
	(120, 'monday', '2015-04-06', '10:00', '11:00', 60, 33),
	(121, 'tuesday', '2015-04-07', NULL, NULL, NULL, 33),
	(122, 'wednesday', '2015-04-08', '13:00', '14:00', 60, 33),
	(123, 'thursday', '2015-04-09', NULL, NULL, NULL, 33),
	(124, 'friday', '2015-04-10', NULL, NULL, NULL, 33),
	(125, 'saturday', '2015-04-11', NULL, NULL, NULL, 33),
	(126, 'sunday', '2015-04-12', NULL, NULL, NULL, 33),
	(127, 'monday', '2015-04-06', '9:00', '10:00', 60, 34),
	(128, 'tuesday', '2015-04-07', '11:00', '12:00', 60, 34),
	(129, 'wednesday', '2015-04-08', NULL, NULL, NULL, 34),
	(130, 'thursday', '2015-04-09', NULL, NULL, NULL, 34),
	(131, 'friday', '2015-04-10', NULL, NULL, NULL, 34),
	(132, 'saturday', '2015-04-11', NULL, NULL, NULL, 34),
	(133, 'sunday', '2015-04-12', NULL, NULL, NULL, 34),
	(134, 'monday', '2015-04-13', '10:00', '11:00', 60, 35),
	(135, 'tuesday', '2015-04-14', '11:00', '12:00', 60, 35),
	(136, 'wednesday', '2015-04-15', NULL, NULL, NULL, 35),
	(137, 'thursday', '2015-04-16', NULL, NULL, NULL, 35),
	(138, 'friday', '2015-04-17', '5:00', '6:00', 60, 35),
	(139, 'saturday', '2015-04-18', NULL, NULL, NULL, 35),
	(140, 'sunday', '2015-04-19', '15:30', '16:30', 60, 35);
/*!40000 ALTER TABLE `timesheet_days` ENABLE KEYS */;


-- Dumping structure for table timemgmt.timesheet_days_actual
CREATE TABLE IF NOT EXISTS `timesheet_days_actual` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `day` varchar(255) DEFAULT NULL,
  `timesheet_date` date DEFAULT NULL,
  `time_from` varchar(100) DEFAULT NULL,
  `time_to` varchar(100) DEFAULT NULL,
  `work_minutes` int(20) DEFAULT NULL,
  `timesheet_row_actual_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `timesheet_row_actual_id` (`timesheet_row_actual_id`)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8;

-- Dumping data for table timemgmt.timesheet_days_actual: ~49 rows (approximately)
/*!40000 ALTER TABLE `timesheet_days_actual` DISABLE KEYS */;
INSERT INTO `timesheet_days_actual` (`id`, `day`, `timesheet_date`, `time_from`, `time_to`, `work_minutes`, `timesheet_row_actual_id`) VALUES
	(50, 'monday', '2015-03-30', '10:00', '11:00', 60, 8),
	(51, 'tuesday', '2015-03-31', '11:00', '12:00', 60, 8),
	(52, 'wednesday', '2015-04-01', NULL, NULL, NULL, 8),
	(53, 'thursday', '2015-04-02', NULL, NULL, NULL, 8),
	(54, 'friday', '2015-04-03', NULL, NULL, NULL, 8),
	(55, 'saturday', '2015-04-04', NULL, NULL, NULL, 8),
	(56, 'sunday', '2015-04-05', NULL, NULL, NULL, 8),
	(64, 'monday', '2015-04-06', '9:00', '10:00', 60, 10),
	(65, 'tuesday', '2015-04-07', NULL, NULL, NULL, 10),
	(66, 'wednesday', '2015-04-08', NULL, NULL, NULL, 10),
	(67, 'thursday', '2015-04-09', NULL, NULL, NULL, 10),
	(68, 'friday', '2015-04-10', NULL, NULL, NULL, 10),
	(69, 'saturday', '2015-04-11', NULL, NULL, NULL, 10),
	(70, 'sunday', '2015-04-05', NULL, NULL, NULL, 10),
	(71, 'monday', '2015-04-06', '10:00', '11:00', 60, 11),
	(72, 'tuesday', '2015-04-07', NULL, NULL, NULL, 11),
	(73, 'wednesday', '2015-04-08', NULL, NULL, NULL, 11),
	(74, 'thursday', '2015-04-09', NULL, NULL, NULL, 11),
	(75, 'friday', '2015-04-10', NULL, NULL, NULL, 11),
	(76, 'saturday', '2015-04-11', NULL, NULL, NULL, 11),
	(77, 'sunday', '2015-04-12', NULL, NULL, NULL, 11),
	(78, 'monday', '2015-04-13', '9:00', '10:00', 60, 12),
	(79, 'tuesday', '2015-04-14', NULL, NULL, NULL, 12),
	(80, 'wednesday', '2015-04-15', NULL, NULL, NULL, 12),
	(81, 'thursday', '2015-04-16', NULL, NULL, NULL, 12),
	(82, 'friday', '2015-04-17', NULL, NULL, NULL, 12),
	(83, 'saturday', '2015-04-18', NULL, NULL, NULL, 12),
	(84, 'sunday', '2015-04-12', NULL, NULL, NULL, 12),
	(85, 'monday', '2015-04-06', '10:00', '11:00', 60, 13),
	(86, 'tuesday', '2015-04-07', NULL, NULL, NULL, 13),
	(87, 'wednesday', '2015-04-08', NULL, NULL, NULL, 13),
	(88, 'thursday', '2015-04-09', NULL, NULL, NULL, 13),
	(89, 'friday', '2015-04-10', NULL, NULL, NULL, 13),
	(90, 'saturday', '2015-04-11', NULL, NULL, NULL, 13),
	(91, 'sunday', '2015-04-12', NULL, NULL, NULL, 13),
	(92, 'monday', '2015-04-06', '10:00', '11:00', 60, 14),
	(93, 'tuesday', '2015-04-07', '9:00', '10:00', 60, 14),
	(94, 'wednesday', '2015-04-08', NULL, NULL, NULL, 14),
	(95, 'thursday', '2015-04-09', NULL, NULL, NULL, 14),
	(96, 'friday', '2015-04-10', NULL, NULL, NULL, 14),
	(97, 'saturday', '2015-04-11', NULL, NULL, NULL, 14),
	(98, 'sunday', '2015-04-12', NULL, NULL, NULL, 14),
	(99, 'monday', '2015-04-13', '11:00', '12:30', 90, 15),
	(100, 'tuesday', '2015-04-14', NULL, NULL, NULL, 15),
	(101, 'wednesday', '2015-04-15', '13:00', '14:00', 60, 15),
	(102, 'thursday', '2015-04-16', NULL, NULL, NULL, 15),
	(103, 'friday', '2015-04-17', NULL, NULL, NULL, 15),
	(104, 'saturday', '2015-04-18', NULL, NULL, NULL, 15),
	(105, 'sunday', '2015-04-19', NULL, NULL, NULL, 15);
/*!40000 ALTER TABLE `timesheet_days_actual` ENABLE KEYS */;


-- Dumping structure for table timemgmt.timesheet_row
CREATE TABLE IF NOT EXISTS `timesheet_row` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `timesheet_id` bigint(20) DEFAULT NULL,
  `project_code` varchar(255) DEFAULT NULL,
  `task_code` varchar(255) DEFAULT NULL,
  `over_time` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_timesheet_row_timesheet_25` (`timesheet_id`),
  CONSTRAINT `fk_timesheet_row_timesheet_25` FOREIGN KEY (`timesheet_id`) REFERENCES `timesheet` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.timesheet_row: ~13 rows (approximately)
/*!40000 ALTER TABLE `timesheet_row` DISABLE KEYS */;
INSERT INTO `timesheet_row` (`id`, `timesheet_id`, `project_code`, `task_code`, `over_time`) VALUES
	(1, 1, 'AT-1', 't-1', NULL),
	(2, 2, 'AT-1', 't-1', NULL),
	(8, 4, 'AT-2', 't-2', NULL),
	(9, 3, 'AT-2', 't-2', NULL),
	(22, 5, '1', 't-2', 0),
	(24, 21, '1', 't-2', 1),
	(25, 22, '1', 't-2', 1),
	(29, 25, '1', 't-2', 0),
	(31, 25, '1', 't-2', 0),
	(32, 22, '1', 't-2', 0),
	(33, 26, '1', 't-2', 0),
	(34, 27, '1', 't-2', 0),
	(35, 28, '1', 't-2', 0);
/*!40000 ALTER TABLE `timesheet_row` ENABLE KEYS */;


-- Dumping structure for table timemgmt.timesheet_row_actual
CREATE TABLE IF NOT EXISTS `timesheet_row_actual` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `timesheet_actual_id` bigint(20) DEFAULT NULL,
  `project_code` varchar(255) DEFAULT NULL,
  `task_code` varchar(255) DEFAULT NULL,
  `over_time` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `timesheet_actual_id` (`timesheet_actual_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- Dumping data for table timemgmt.timesheet_row_actual: ~8 rows (approximately)
/*!40000 ALTER TABLE `timesheet_row_actual` DISABLE KEYS */;
INSERT INTO `timesheet_row_actual` (`id`, `timesheet_actual_id`, `project_code`, `task_code`, `over_time`) VALUES
	(8, 7, '1', 't-2', 0),
	(10, 7, '1', 't-2', 0),
	(11, 8, '1', 't-2', 0),
	(12, 8, '1', 't-2', 0),
	(13, 9, '1', 't-2', 0),
	(14, 9, '1', 't-2', 0),
	(15, 10, '1', 't-2', 0);
/*!40000 ALTER TABLE `timesheet_row_actual` ENABLE KEYS */;


-- Dumping structure for table timemgmt.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `salutation` varchar(4) DEFAULT NULL,
  `employee_id` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `gender` varchar(6) DEFAULT NULL,
  `status` varchar(8) DEFAULT NULL,
  `hire_date` datetime DEFAULT NULL,
  `annual_income` double DEFAULT NULL,
  `hourlyrate` double DEFAULT NULL,
  `companyobject_id` bigint(20) DEFAULT NULL,
  `designation` varchar(255) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `level_id` bigint(20) DEFAULT NULL,
  `manager_id` bigint(20) DEFAULT NULL,
  `release_date` datetime DEFAULT NULL,
  `hrmanager_id` bigint(20) DEFAULT NULL,
  `permissions` varchar(700) DEFAULT NULL,
  `temp_password` int(11) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `reset_flag` tinyint(1) DEFAULT '0',
  `failed_login_attempt` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `password_reset` tinyint(1) DEFAULT '0',
  `user_status` varchar(15) DEFAULT NULL,
  `process_instance_id` varchar(255) DEFAULT NULL,
  `last_update` datetime NOT NULL,
  `department` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_user_email` (`email`),
  KEY `ix_user_companyobject_26` (`companyobject_id`),
  KEY `ix_user_role_27` (`role_id`),
  KEY `ix_user_level_28` (`level_id`),
  KEY `ix_user_manager_29` (`manager_id`),
  KEY `ix_user_hrmanager_30` (`hrmanager_id`),
  CONSTRAINT `fk_user_companyobject_26` FOREIGN KEY (`companyobject_id`) REFERENCES `company` (`id`),
  CONSTRAINT `fk_user_hrmanager_30` FOREIGN KEY (`hrmanager_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_user_level_28` FOREIGN KEY (`level_id`) REFERENCES `leave_level` (`id`),
  CONSTRAINT `fk_user_manager_29` FOREIGN KEY (`manager_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_user_role_27` FOREIGN KEY (`role_id`) REFERENCES `role_level` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.user: ~6 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `salutation`, `employee_id`, `first_name`, `middle_name`, `last_name`, `email`, `gender`, `status`, `hire_date`, `annual_income`, `hourlyrate`, `companyobject_id`, `designation`, `role_id`, `level_id`, `manager_id`, `release_date`, `hrmanager_id`, `permissions`, `temp_password`, `password`, `reset_flag`, `failed_login_attempt`, `create_date`, `modified_date`, `password_reset`, `user_status`, `process_instance_id`, `last_update`, `department`) VALUES
	(1, 'Mr', '1', 'jagbir', 'singh', 'paul', 'mindnervestech@gmail.com', NULL, 'OnRolls', NULL, NULL, NULL, 1, 'SuperAdmin', NULL, NULL, NULL, NULL, NULL, 'SearchTimesheet|CreateTimesheet|Delegate|Leaves|ApplyLeave|FeedBackCreate|FeedBackView|UserPermissions|LeaveBucket|MyBucket|TeamRate|ProjectReport|ManageUser|ManageProject|ManageTask|ManageClient|Notification|DefineRoles|OrgHierarchy|Mail|RolePermissions', 0, 'Qwe123', NULL, NULL, NULL, NULL, 0, 'Approved', NULL, '2015-02-19 15:32:01', NULL),
	(2, NULL, NULL, 'Google', NULL, 'Admin', 'google@ggg.com', NULL, NULL, NULL, NULL, NULL, 2, 'Admin', NULL, NULL, NULL, NULL, NULL, 'Delegate|FeedBackCreate|FeedBackView|TeamRate|ProjectReport|Notification|ApplyLeave|Leaves|Timesheet|CreateTimesheet|SearchTimesheet|Mail|UserRequest|DefineRoles|DefineDepartments|OrgHierarchy|UserPermissions|ManageUser|ManageProject|ManageTask|ManageClient|LeaveSettings|RolePermissions|DefineLeaves', 0, 'qwe123', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2015-02-23 11:19:49', NULL),
	(3, 'Mr', '1', 'Project', '', 'Manager', 'pm@ggg.com', 'Male', 'OnRolls', '2015-02-23 00:00:00', 1000000, 480.77, 2, 'Project Manager', 20, NULL, 2, NULL, NULL, 'ManageUser|ManageClient|ManageProject|ManageTask|ApplyLeave|CreateTimesheet|SearchTimesheet|CreateTimesheets|Today|Week|Month|Holiday|TodayAll|WeekReport|UserRequest|MyBucket|DefineRoles|DefineLeaves|', 0, 'qwe123', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2015-04-14 12:24:11', NULL),
	(4, 'Mr', '2', 'Group', '', 'Lead', 'gl@ggg.com', 'Male', 'OnRolls', '2015-02-23 00:00:00', 500000, 240.38, 2, 'Group Lead', 19, NULL, 3, NULL, NULL, 'ManageUser|ManageClient|ManageProject|ManageTask|ApplyLeave|CreateTimesheet|SearchTimesheet|Today|Week|Month|Holiday|Delegate|RolePermissions|UserPermissions|MyBucket|LeaveBucket|TeamRate|ProjectReport|Mail|Notification|DefineRoles|DefineLeaves|OrgHierarchy|', 0, 'qwe123', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2015-04-10 17:52:25', NULL),
	(5, 'Mr', '3', 'Senior', '', 'Software', 'sse@ggg.com', 'Male', 'OnRolls', '2015-02-23 00:00:00', 250000, 120.19, 2, 'Senior Software Engineer', 18, NULL, 4, NULL, NULL, '', 1, 'm8ct27', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2015-02-23 11:31:46', NULL),
	(6, NULL, NULL, 'abc', 'pqr', 'xyz', 'abc@gmail.com', 'Male', 'OnRolls', NULL, NULL, NULL, 1, 'Software Engineer', NULL, NULL, 3, NULL, NULL, NULL, 0, 'qwe123', NULL, NULL, NULL, NULL, NULL, 'PendingApproval', NULL, '2015-04-10 13:33:26', NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;


-- Dumping structure for table timemgmt.user_flexi
CREATE TABLE IF NOT EXISTS `user_flexi` (
  `user_id` bigint(20) NOT NULL,
  `flexi_id` bigint(20) NOT NULL,
  `value` varchar(1255) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `userflexi` (`user_id`),
  KEY `userflexi2` (`flexi_id`),
  CONSTRAINT `userflexi` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `userflexi2` FOREIGN KEY (`flexi_id`) REFERENCES `flexi_attribute` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.user_flexi: ~40 rows (approximately)
/*!40000 ALTER TABLE `user_flexi` DISABLE KEYS */;
INSERT INTO `user_flexi` (`user_id`, `flexi_id`, `value`, `id`) VALUES
	(2, 1, 'jagbir', 1),
	(18, 1, 'Jagbir Singh', 3),
	(18, 2, 'B3 - Rakshak Nagar Gold', 4),
	(24, 1, 'Jagbir Singh', 5),
	(24, 2, 'B3 - Rakshak Nagar Gold', 6),
	(24, 3, 'Khardi', 7),
	(24, 4, '', 8),
	(24, 5, '', 9),
	(24, 1, 'Jagbir Singh', 10),
	(24, 2, 'B3 - Rakshak Nagar Gold', 11),
	(24, 3, 'Khardi', 12),
	(24, 4, 'koko', 13),
	(24, 5, '', 14),
	(24, 1, 'Jagbir Singh', 15),
	(24, 2, 'B3 - Rakshak Nagar Gold', 16),
	(24, 3, 'Khardi', 17),
	(24, 4, 'koko', 18),
	(24, 5, 'kokoko', 19),
	(29, 1, 'Jagbir Singh', 37),
	(29, 2, 'B3 - Rakshak Nagar Gold', 38),
	(29, 3, 'Khardi', 39),
	(29, 4, 'asd', 40),
	(29, 5, '[{"n":"giraffe-306478_640.png","s":86773,"ct":"image/png"}]', 41),
	(30, 1, 'sdcds', 42),
	(30, 2, 'asxas', 43),
	(30, 3, 'asxas', 44),
	(30, 4, 'asxasxa', 45),
	(30, 5, '[{"n":"Sunset.jpg","s":71189,"ct":"image/jpeg"}]', 46),
	(31, 1, 'wefdw', 47),
	(31, 2, 'sda', 48),
	(31, 3, 'asdasd', 49),
	(31, 4, 'sdacsdc', 50),
	(31, 5, '[{"n":"Water lilies.jpg","s":83794,"ct":"image/jpeg"}]', 51),
	(31, 16, 'defvfdv', 52),
	(6, 1, 'yog', 53),
	(6, 2, 'navi peth', 54),
	(6, 3, 'nana peth', 55),
	(6, 4, 'raje', 56),
	(6, 5, '[{"n":"Tulips.jpg","s":620888,"ct":"image/jpeg"}]', 57),
	(6, 16, '235', 58);
/*!40000 ALTER TABLE `user_flexi` ENABLE KEYS */;


-- Dumping structure for table timemgmt.user_leave
CREATE TABLE IF NOT EXISTS `user_leave` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `reason` varchar(255) DEFAULT NULL,
  `leave_type` int(11) DEFAULT NULL,
  `from_date` datetime DEFAULT NULL,
  `to_date` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- Dumping data for table timemgmt.user_leave: ~3 rows (approximately)
/*!40000 ALTER TABLE `user_leave` DISABLE KEYS */;
INSERT INTO `user_leave` (`id`, `reason`, `leave_type`, `from_date`, `to_date`, `status`, `user_id`) VALUES
	(13, 'Weekly Leaves', 0, NULL, NULL, NULL, 4),
	(15, 'holiday', 7, '2015-04-14 00:00:00', '2015-04-16 00:00:00', 'un-assigned', 4),
	(16, 'Weekly Leaves', 0, NULL, NULL, NULL, 3);
/*!40000 ALTER TABLE `user_leave` ENABLE KEYS */;


-- Dumping structure for table timemgmt.user_project
CREATE TABLE IF NOT EXISTS `user_project` (
  `user_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`project_id`),
  KEY `fk_user_project_project_02` (`project_id`),
  CONSTRAINT `fk_user_project_project_02` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  CONSTRAINT `fk_user_project_user_01` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt.user_project: ~0 rows (approximately)
/*!40000 ALTER TABLE `user_project` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_project` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
