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

-- Dumping database structure for timemgmt1
CREATE DATABASE IF NOT EXISTS `timemgmt1` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `timemgmt1`;


-- Dumping structure for table timemgmt1.apply_leave
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

-- Dumping data for table timemgmt1.apply_leave: ~0 rows (approximately)
/*!40000 ALTER TABLE `apply_leave` DISABLE KEYS */;
/*!40000 ALTER TABLE `apply_leave` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.case_data
CREATE TABLE IF NOT EXISTS `case_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `projects_id` bigint(20) DEFAULT NULL,
  `assignto_id` bigint(20) DEFAULT NULL,
  `userid` bigint(20) DEFAULT NULL,
  `due_date` datetime DEFAULT NULL,
  `type` varchar(5) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_case_data_projects_3` (`projects_id`),
  KEY `ix_case_data_assignto_4` (`assignto_id`),
  KEY `ix_case_data_company_5` (`company_id`),
  CONSTRAINT `fk_case_data_assignto_4` FOREIGN KEY (`assignto_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_case_data_company_5` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `fk_case_data_projects_3` FOREIGN KEY (`projects_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.case_data: ~0 rows (approximately)
/*!40000 ALTER TABLE `case_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `case_data` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.case_flexi
CREATE TABLE IF NOT EXISTS `case_flexi` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `case_data_id` bigint(20) DEFAULT NULL,
  `flexi_id` bigint(20) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_case_flexi_CaseData_6` (`case_data_id`),
  CONSTRAINT `fk_case_flexi_CaseData_6` FOREIGN KEY (`case_data_id`) REFERENCES `case_data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.case_flexi: ~0 rows (approximately)
/*!40000 ALTER TABLE `case_flexi` DISABLE KEYS */;
/*!40000 ALTER TABLE `case_flexi` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.case_notes
CREATE TABLE IF NOT EXISTS `case_notes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `casedata_id` bigint(20) DEFAULT NULL,
  `casenote` varchar(255) DEFAULT NULL,
  `note_user` bigint(20) DEFAULT NULL,
  `note_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_case_notes_casedata_7` (`casedata_id`),
  CONSTRAINT `fk_case_notes_casedata_7` FOREIGN KEY (`casedata_id`) REFERENCES `case_data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.case_notes: ~0 rows (approximately)
/*!40000 ALTER TABLE `case_notes` DISABLE KEYS */;
/*!40000 ALTER TABLE `case_notes` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.client
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
  KEY `ix_client_company_8` (`company_id`),
  CONSTRAINT `fk_client_company_8` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.client: ~0 rows (approximately)
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` (`id`, `client_name`, `phone_no`, `email`, `fax`, `address`, `street`, `city`, `country`, `pin`, `contact_name`, `contact_phone`, `contact_email`, `company_id`) VALUES
	(1, 'Roque', '3228022291', 'roque.almedia@gmail.com', NULL, 'Putio Vittoin Lanea', 'St. Pullto ', 'Campolia', 'Brazil', '3213', 'Roque', '3232280222', 'roque.almedia@gmail.com', 1);
/*!40000 ALTER TABLE `client` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.client_flexi
CREATE TABLE IF NOT EXISTS `client_flexi` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `client_id` bigint(20) DEFAULT NULL,
  `flexi_id` bigint(20) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_client_flexi_client_9` (`client_id`),
  CONSTRAINT `fk_client_flexi_client_9` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.client_flexi: ~0 rows (approximately)
/*!40000 ALTER TABLE `client_flexi` DISABLE KEYS */;
/*!40000 ALTER TABLE `client_flexi` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.company
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

-- Dumping data for table timemgmt1.company: ~2 rows (approximately)
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` (`id`, `company_name`, `company_code`, `company_email`, `company_phone`, `address`, `company_status`) VALUES
	(1, 'Mindnerves', 'mindnerves.com', 'amit.goyal@mindnerves.com', '9028022291', ' Hadapsar, Pune  140028', 'Approved'),
	(2, 'Google', 'gmail.com', 'jagbir.paul@gmail.com', '9028022291', 'B3 - Rakshak Nagar Gold\r\nKhardi', 'Approved');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.delegate
CREATE TABLE IF NOT EXISTS `delegate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `from_date` datetime DEFAULT NULL,
  `to_date` datetime DEFAULT NULL,
  `delegator_id` bigint(20) DEFAULT NULL,
  `delegated_to_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_delegate_delegator_10` (`delegator_id`),
  KEY `ix_delegate_delegatedTo_11` (`delegated_to_id`),
  CONSTRAINT `fk_delegate_delegatedTo_11` FOREIGN KEY (`delegated_to_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_delegate_delegator_10` FOREIGN KEY (`delegator_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.delegate: ~0 rows (approximately)
/*!40000 ALTER TABLE `delegate` DISABLE KEYS */;
/*!40000 ALTER TABLE `delegate` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.department
CREATE TABLE IF NOT EXISTS `department` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.department: ~9 rows (approximately)
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` (`id`, `name`) VALUES
	(2, 'Sales'),
	(3, 'Marketing'),
	(4, 'Development'),
	(5, 'Testing'),
	(6, 'Core RnD'),
	(7, 'Finance'),
	(8, 'Human Resourse'),
	(9, 'Operational'),
	(10, 'Infra');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.feedback
CREATE TABLE IF NOT EXISTS `feedback` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `feedback_date` datetime DEFAULT NULL,
  `feedback` longtext,
  `rating` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `manager_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_feedback_user_12` (`user_id`),
  KEY `ix_feedback_manager_13` (`manager_id`),
  CONSTRAINT `fk_feedback_manager_13` FOREIGN KEY (`manager_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_feedback_user_12` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.feedback: ~0 rows (approximately)
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.flexi_attribute
CREATE TABLE IF NOT EXISTS `flexi_attribute` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `model` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `uniqueid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.flexi_attribute: ~0 rows (approximately)
/*!40000 ALTER TABLE `flexi_attribute` DISABLE KEYS */;
INSERT INTO `flexi_attribute` (`id`, `model`, `name`, `type`, `uniqueid`) VALUES
	(1, 'models.UserFlexi', 'Profile Picture', 'FILE', 1);
/*!40000 ALTER TABLE `flexi_attribute` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.leaves_credit
CREATE TABLE IF NOT EXISTS `leaves_credit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `policy_name` varchar(255) DEFAULT NULL,
  `companyobject_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_leaves_credit_companyobjec_19` (`companyobject_id`),
  CONSTRAINT `fk_leaves_credit_companyobjec_19` FOREIGN KEY (`companyobject_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.leaves_credit: ~0 rows (approximately)
/*!40000 ALTER TABLE `leaves_credit` DISABLE KEYS */;
/*!40000 ALTER TABLE `leaves_credit` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.leave_balance
CREATE TABLE IF NOT EXISTS `leave_balance` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `employee_id` bigint(20) DEFAULT NULL,
  `leave_level_id` bigint(20) DEFAULT NULL,
  `balance` float NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_leave_balance_employee_14` (`employee_id`),
  KEY `ix_leave_balance_leaveLevel_15` (`leave_level_id`),
  CONSTRAINT `fk_leave_balance_employee_14` FOREIGN KEY (`employee_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_leave_balance_leaveLevel_15` FOREIGN KEY (`leave_level_id`) REFERENCES `leave_level` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.leave_balance: ~0 rows (approximately)
/*!40000 ALTER TABLE `leave_balance` DISABLE KEYS */;
/*!40000 ALTER TABLE `leave_balance` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.leave_level
CREATE TABLE IF NOT EXISTS `leave_level` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `leave_type` varchar(255) DEFAULT NULL,
  `carry_forward` varchar(255) DEFAULT NULL,
  `leave_x_id` bigint(20) DEFAULT NULL,
  `role_leave_id` bigint(20) DEFAULT NULL,
  `last_update` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_leave_level_leaveX_16` (`leave_x_id`),
  KEY `ix_leave_level_roleLeave_17` (`role_leave_id`),
  CONSTRAINT `fk_leave_level_leaveX_16` FOREIGN KEY (`leave_x_id`) REFERENCES `leave_x` (`id`),
  CONSTRAINT `fk_leave_level_roleLeave_17` FOREIGN KEY (`role_leave_id`) REFERENCES `role_leave` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.leave_level: ~0 rows (approximately)
/*!40000 ALTER TABLE `leave_level` DISABLE KEYS */;
/*!40000 ALTER TABLE `leave_level` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.leave_x
CREATE TABLE IF NOT EXISTS `leave_x` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_leave_x_company_18` (`company_id`),
  CONSTRAINT `fk_leave_x_company_18` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.leave_x: ~0 rows (approximately)
/*!40000 ALTER TABLE `leave_x` DISABLE KEYS */;
/*!40000 ALTER TABLE `leave_x` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.mail_setting
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
  KEY `ix_mail_setting_companyObject_20` (`company_object_id`),
  CONSTRAINT `fk_mail_setting_companyObject_20` FOREIGN KEY (`company_object_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.mail_setting: ~2 rows (approximately)
/*!40000 ALTER TABLE `mail_setting` DISABLE KEYS */;
INSERT INTO `mail_setting` (`id`, `host_name`, `port_number`, `ssl_port`, `tls_port`, `user_name`, `password`, `company_object_id`, `last_update`) VALUES
	(1, NULL, NULL, NULL, NULL, 'amit.goyal@mindnerves.com', NULL, 1, '2015-04-22 12:18:36'),
	(2, NULL, NULL, NULL, NULL, 'jagbir.paul@gmail.com', NULL, 2, '2015-04-22 12:25:22');
/*!40000 ALTER TABLE `mail_setting` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.notification
CREATE TABLE IF NOT EXISTS `notification` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `setting_as_json` longtext,
  `company_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_notification_company_21` (`company_id`),
  CONSTRAINT `fk_notification_company_21` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.notification: ~2 rows (approximately)
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` (`id`, `setting_as_json`, `company_id`) VALUES
	(1, NULL, 1),
	(2, NULL, 2);
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.organization
CREATE TABLE IF NOT EXISTS `organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `organization_name` varchar(255) DEFAULT NULL,
  `organization_type` varchar(255) DEFAULT NULL,
  `organization_location` varchar(255) DEFAULT NULL,
  `organization_profile_url` varchar(255) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `parent` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.organization: ~3 rows (approximately)
/*!40000 ALTER TABLE `organization` DISABLE KEYS */;
INSERT INTO `organization` (`id`, `organization_name`, `organization_type`, `organization_location`, `organization_profile_url`, `company_id`, `parent`) VALUES
	(1, 'Mindnerves', 'Head Quater', 'Pune', NULL, 1, NULL),
	(2, 'Mindnerves Pune', 'Sale & Marketing', 'B3 - Rakshak Nagar Gold\r\nKhardi', 'org1_2.jpg', 1, 1),
	(3, 'Mindnerves Delhi', 'Development', 'B3 - Rakshak Nagar Gold\r\nKhardi', 'org1_3.jpg', 1, 1);
/*!40000 ALTER TABLE `organization` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.project
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
  KEY `ix_project_clientName_22` (`client_name_id`),
  KEY `ix_project_projectManager_23` (`project_manager_id`),
  KEY `ix_project_companyObj_24` (`company_obj_id`),
  CONSTRAINT `fk_project_clientName_22` FOREIGN KEY (`client_name_id`) REFERENCES `client` (`id`),
  CONSTRAINT `fk_project_companyObj_24` FOREIGN KEY (`company_obj_id`) REFERENCES `company` (`id`),
  CONSTRAINT `fk_project_projectManager_23` FOREIGN KEY (`project_manager_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.project: ~0 rows (approximately)
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
/*!40000 ALTER TABLE `project` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.projectclass
CREATE TABLE IF NOT EXISTS `projectclass` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_types` varchar(255) DEFAULT NULL,
  `project_description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.projectclass: ~0 rows (approximately)
/*!40000 ALTER TABLE `projectclass` DISABLE KEYS */;
INSERT INTO `projectclass` (`id`, `project_types`, `project_description`) VALUES
	(1, 'Software', 'This manages Software life cycle.');
/*!40000 ALTER TABLE `projectclass` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.projectclassnode
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
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.projectclassnode: ~49 rows (approximately)
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
	(76, 'MRF', 'MRF wheel', 11, '68', '#8000ff', '2'),
	(77, 'ProjectType Test1', 'This is test project type', 14, NULL, NULL, '0'),
	(78, 'Test Node 1', 'This is test project type', 14, '77', NULL, '1'),
	(79, 'Test Node 2', 'This is test project type', 14, '77', NULL, '1');
/*!40000 ALTER TABLE `projectclassnode` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.projectclassnodeattribut
CREATE TABLE IF NOT EXISTS `projectclassnodeattribut` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `projectnode_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_projectclassnodeattribut_p_28` (`projectnode_id`),
  CONSTRAINT `fk_projectclassnodeattribut_p_28` FOREIGN KEY (`projectnode_id`) REFERENCES `projectclassnode` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.projectclassnodeattribut: ~12 rows (approximately)
/*!40000 ALTER TABLE `projectclassnodeattribut` DISABLE KEYS */;
INSERT INTO `projectclassnodeattribut` (`id`, `name`, `type`, `value`, `projectnode_id`) VALUES
	(2, 'Language Used', 'String', NULL, 2),
	(3, 'Tools Used', 'String', NULL, 3),
	(4, 'Checklist', 'Radio', 'Need Mockups?\nNeed DFD Diagrams?', 4),
	(5, 'Checklist', 'Checkbox', 'Is Coding Standard followed?\nIs Code written in OOP way?\nIs indentation done?', 5),
	(6, 'Check list', 'Checkbox', 'Is manual Testing done?\nIs Junit Done?', 6),
	(7, 'How much Code coverage?', 'String', NULL, 6),
	(8, 'Checklist', 'Checkbox', 'PMD Passed?\nCode standards followed?', 7),
	(9, 'Comment for dev', 'String', NULL, 7),
	(10, '', NULL, NULL, 8),
	(11, NULL, NULL, NULL, 9),
	(12, NULL, NULL, NULL, 10),
	(13, NULL, NULL, NULL, 11);
/*!40000 ALTER TABLE `projectclassnodeattribut` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.projectinstance
CREATE TABLE IF NOT EXISTS `projectinstance` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_name` varchar(50) DEFAULT NULL,
  `project_description` varchar(50) DEFAULT NULL,
  `projectid` bigint(20) DEFAULT NULL,
  `client_id` bigint(20) DEFAULT NULL,
  `client_name` varchar(50) DEFAULT NULL,
  `start_date` varchar(50) DEFAULT NULL,
  `end_date` varchar(50) DEFAULT NULL,
  `userid_id` bigint(20) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_projectinstance_user` (`userid_id`),
  CONSTRAINT `FK_projectinstance_user` FOREIGN KEY (`userid_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.projectinstance: ~38 rows (approximately)
/*!40000 ALTER TABLE `projectinstance` DISABLE KEYS */;
INSERT INTO `projectinstance` (`id`, `project_name`, `project_description`, `projectid`, `client_id`, `client_name`, `start_date`, `end_date`, `userid_id`, `status`) VALUES
	(1, 'maruti', 'ww ww', 11, 1, 'abcd', '02-02-2016', '04-04-2016', NULL, NULL),
	(2, 'swift', 'ttt yyyy', 11, 1, 'abcd', '2015-02-03', '2015-06-10', NULL, NULL),
	(23, 'farrari', 'rr rrr', 11, 1, 'abcd', '02-04-2015', '23-04-2015', 3, NULL),
	(27, 'yogesh', 'fff ggg g g g g g g g g g  g g g g g', 6, 1, 'abcd', NULL, NULL, NULL, NULL),
	(36, 'Client', 'Client in India', 13, 1, 'abcd', NULL, NULL, NULL, NULL),
	(37, 'sdfs', 'sdf', 6, 1, 'abcd', NULL, NULL, NULL, NULL),
	(47, 'MyCar', 'car', 11, 2, 'Yogesh', NULL, NULL, NULL, NULL),
	(49, 'aksha', 'sapanch', 11, 2, 'Yogesh', NULL, NULL, NULL, NULL),
	(50, 'tata', 'ffrfrff', 6, 2, 'Yogesh', NULL, NULL, NULL, NULL),
	(51, 'dfgffggggg', 'dfgdf', 7, 2, 'Yogesh', NULL, NULL, NULL, NULL),
	(52, 'dddd', 'fr frfrf', 7, 1, 'abcd', NULL, NULL, NULL, NULL),
	(53, 'hh66', 'fg hh', 8, 1, 'abcd', NULL, NULL, NULL, NULL),
	(54, 'ert', 'ert', 7, 1, 'abcd', NULL, NULL, NULL, NULL),
	(55, 'sss', 'ddd', 6, 1, 'abcd', NULL, NULL, NULL, NULL),
	(56, 'dsfsd', 'sdf', 7, 2, 'Yogesh', NULL, NULL, NULL, NULL),
	(57, 'farrari', 'jijij', 6, 1, 'abcd', NULL, NULL, NULL, NULL),
	(58, 'asdsa0000', 'dd tt 666', 7, 1, 'abcd', NULL, NULL, NULL, NULL),
	(59, 'er231422', 'ff ggg5555', 6, 1, 'abcd', NULL, NULL, 2, NULL),
	(60, '666 555', 'fff', 11, 2, 'Yogesh', NULL, NULL, 2, NULL),
	(61, 'ttt6664422', 'aa', 7, 1, 'abcd', NULL, NULL, 5, NULL),
	(62, 'ttt6664422', 'aa', 7, 1, 'abcd', NULL, NULL, 5, NULL),
	(63, 'ttt6664422', 'aa', 7, 1, 'abcd', NULL, NULL, 5, NULL),
	(64, 'ttt6664422', 'aa', 7, 1, 'abcd', NULL, NULL, 5, NULL),
	(65, 'ggg', 'dd', 7, 1, 'abcd', NULL, NULL, 2, NULL),
	(66, 'sdsdf', 'sdf', 8, 1, 'abcd', NULL, NULL, 1, NULL),
	(67, 'sdsdf', 'sdf', 8, 1, 'abcd', NULL, NULL, 1, NULL),
	(68, 'sdsdf', 'sdf', 8, 1, 'abcd', NULL, NULL, 1, NULL),
	(69, 'dd', 'hsd sd', 8, 1, 'abcd', NULL, NULL, 1, NULL),
	(70, 'dd', 'hsd sd', 8, 1, 'abcd', NULL, NULL, 1, NULL),
	(71, 'dd', 'hsd sd', 8, 1, 'abcd', NULL, NULL, 1, NULL),
	(72, 'dd', 'hsd sd', 8, 1, 'abcd', NULL, NULL, 1, NULL),
	(73, 'dd', 'hsd sd', 8, 1, 'abcd', NULL, NULL, 1, NULL),
	(74, 'dd', 'hsd sd', 8, 1, 'abcd', '03-04-2015', '06-04-2015', 1, NULL),
	(75, 'dd', 'hsd sd', 8, 1, 'abcd', '02-03-2015', '04-04-2015', 1, NULL),
	(76, 'dd', 'hsd sd', 8, 1, 'abcd', NULL, NULL, 1, NULL),
	(77, 'weeer', 'ffrfrf', 10, 1, 'abcd', '02-04-2015', '03-04-2015', 2, NULL),
	(78, 'sdsd', 'sdf', 9, 1, 'abcd', '04-04-2015', '06-05-2015', 2, NULL),
	(79, 'sdsd', 'sdf', 9, 1, 'abcd', NULL, NULL, 2, NULL);
/*!40000 ALTER TABLE `projectinstance` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.projectinstancenode
CREATE TABLE IF NOT EXISTS `projectinstancenode` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Projectclassnode_id` bigint(20) DEFAULT NULL,
  `projecttypeid` bigint(20) DEFAULT NULL,
  `projectinstanceid` bigint(20) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `task_compilation` int(11) DEFAULT NULL,
  `weightage` int(11) DEFAULT NULL,
  `project_manager` varchar(50) DEFAULT NULL,
  `supplier_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `color` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_projectinstancenode_projectclassnodeattribut` (`Projectclassnode_id`),
  KEY `FK_projectinstancenode_projectinstance` (`projectinstanceid`),
  CONSTRAINT `FK_projectinstancenode_projectclassnodeattribut` FOREIGN KEY (`Projectclassnode_id`) REFERENCES `projectclassnode` (`id`),
  CONSTRAINT `FK_projectinstancenode_projectinstance` FOREIGN KEY (`projectinstanceid`) REFERENCES `projectinstance` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.projectinstancenode: ~12 rows (approximately)
/*!40000 ALTER TABLE `projectinstancenode` DISABLE KEYS */;
INSERT INTO `projectinstancenode` (`id`, `Projectclassnode_id`, `projecttypeid`, `projectinstanceid`, `start_date`, `end_date`, `task_compilation`, `weightage`, `project_manager`, `supplier_id`, `user_id`, `status`, `color`) VALUES
	(31, 83, 16, 41, '0006-07-08', '0035-07-08', 0, 2, NULL, NULL, NULL, NULL, NULL),
	(32, 83, 16, 42, '2016-01-01', '2016-01-30', 0, 4, NULL, NULL, NULL, NULL, NULL),
	(40, 2, 6, 46, '2015-04-02', '2015-04-25', 0, 3, NULL, NULL, NULL, NULL, NULL),
	(41, 67, 11, 23, '2015-04-02', '2015-04-23', 62, 2, NULL, NULL, NULL, 'Inprogress', NULL),
	(42, 68, 11, 23, '2015-04-13', '2015-04-24', 70, 3, NULL, NULL, NULL, 'Inprogress', NULL),
	(43, 69, 11, 23, '2015-04-14', '2015-04-25', 50, 4, NULL, NULL, NULL, 'Inprogress', NULL),
	(44, 70, 11, 23, '2015-04-16', '2015-04-27', 67, 5, NULL, NULL, NULL, 'Inprogress', NULL),
	(45, 76, 11, 23, '2015-04-15', '2015-04-22', 70, 2, NULL, NULL, NULL, 'Inprogress', NULL),
	(46, 19, 8, 75, '2015-03-02', '2015-04-04', 0, 3, NULL, 2, 2, NULL, NULL),
	(47, 20, 8, 76, '2015-03-03', '2015-04-04', 0, 3, NULL, 2, 2, NULL, NULL),
	(48, 62, 10, 77, '2015-04-02', '2015-04-03', 0, 2, NULL, 2, 3, NULL, NULL),
	(49, 58, 9, 78, '2015-04-04', '2015-05-06', 0, 3, NULL, 1, 1, NULL, NULL);
/*!40000 ALTER TABLE `projectinstancenode` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.projectinstance_supplier
CREATE TABLE IF NOT EXISTS `projectinstance_supplier` (
  `projectinstance_id` bigint(20) NOT NULL,
  `supplier_id` bigint(20) NOT NULL,
  PRIMARY KEY (`projectinstance_id`,`supplier_id`),
  KEY `fk_projectinstance_supplier_s_02` (`supplier_id`),
  CONSTRAINT `fk_projectinstance_supplier_p_01` FOREIGN KEY (`projectinstance_id`) REFERENCES `projectinstance` (`id`),
  CONSTRAINT `fk_projectinstance_supplier_s_02` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.projectinstance_supplier: ~0 rows (approximately)
/*!40000 ALTER TABLE `projectinstance_supplier` DISABLE KEYS */;
INSERT INTO `projectinstance_supplier` (`projectinstance_id`, `supplier_id`) VALUES
	(1, 1);
/*!40000 ALTER TABLE `projectinstance_supplier` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.projectinstance_user
CREATE TABLE IF NOT EXISTS `projectinstance_user` (
  `projectinstance_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  KEY `FK_projectinstance_user_projectinstance` (`projectinstance_id`),
  KEY `FK_projectinstance_user_user` (`user_id`),
  CONSTRAINT `FK_projectinstance_user_projectinstance` FOREIGN KEY (`projectinstance_id`) REFERENCES `projectinstance` (`id`),
  CONSTRAINT `FK_projectinstance_user_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table timemgmt1.projectinstance_user: ~35 rows (approximately)
/*!40000 ALTER TABLE `projectinstance_user` DISABLE KEYS */;
INSERT INTO `projectinstance_user` (`projectinstance_id`, `user_id`) VALUES
	(58, 2),
	(58, 3),
	(60, 2),
	(60, 3),
	(61, 3),
	(62, 3),
	(63, 3),
	(64, 3),
	(65, 2),
	(66, 2),
	(66, 3),
	(67, 2),
	(67, 3),
	(68, 2),
	(68, 3),
	(69, 2),
	(70, 2),
	(71, 2),
	(72, 2),
	(73, 2),
	(74, 2),
	(75, 2),
	(76, 2),
	(77, 2),
	(77, 3),
	(78, 1),
	(78, 2),
	(78, 3),
	(79, 1),
	(79, 2),
	(79, 3),
	(23, 3),
	(23, 4),
	(23, 6),
	(23, 7);
/*!40000 ALTER TABLE `projectinstance_user` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.project_attachment
CREATE TABLE IF NOT EXISTS `project_attachment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `doc_path` varchar(255) DEFAULT NULL,
  `doc_name` varchar(255) DEFAULT NULL,
  `doc_date` datetime DEFAULT NULL,
  `projectinstanceid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.project_attachment: ~0 rows (approximately)
/*!40000 ALTER TABLE `project_attachment` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_attachment` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.project_comment
CREATE TABLE IF NOT EXISTS `project_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_comment` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `commet_date` datetime DEFAULT NULL,
  `projectinstanceid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_project_comment_user_25` (`user_id`),
  CONSTRAINT `fk_project_comment_user_25` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.project_comment: ~0 rows (approximately)
/*!40000 ALTER TABLE `project_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_comment` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.project_flexi
CREATE TABLE IF NOT EXISTS `project_flexi` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) DEFAULT NULL,
  `flexi_id` bigint(20) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_project_flexi_project_26` (`project_id`),
  CONSTRAINT `fk_project_flexi_project_26` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.project_flexi: ~0 rows (approximately)
/*!40000 ALTER TABLE `project_flexi` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_flexi` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.project_task
CREATE TABLE IF NOT EXISTS `project_task` (
  `project_id` bigint(20) NOT NULL,
  `task_id` bigint(20) NOT NULL,
  PRIMARY KEY (`project_id`,`task_id`),
  KEY `fk_project_task_task_02` (`task_id`),
  CONSTRAINT `fk_project_task_project_01` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  CONSTRAINT `fk_project_task_task_02` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.project_task: ~0 rows (approximately)
/*!40000 ALTER TABLE `project_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_task` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.project_user
CREATE TABLE IF NOT EXISTS `project_user` (
  `project_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`project_id`,`user_id`),
  KEY `fk_project_user_user_02` (`user_id`),
  CONSTRAINT `fk_project_user_project_01` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  CONSTRAINT `fk_project_user_user_02` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.project_user: ~0 rows (approximately)
/*!40000 ALTER TABLE `project_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_user` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.role_leave
CREATE TABLE IF NOT EXISTS `role_leave` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) DEFAULT NULL,
  `role_level_id` bigint(20) DEFAULT NULL,
  `leave_level_id` bigint(20) DEFAULT NULL,
  `total_leave` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_role_leave_company_33` (`company_id`),
  KEY `ix_role_leave_roleLevel_34` (`role_level_id`),
  KEY `ix_role_leave_leaveLevel_35` (`leave_level_id`),
  CONSTRAINT `fk_role_leave_company_33` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `fk_role_leave_leaveLevel_35` FOREIGN KEY (`leave_level_id`) REFERENCES `leave_level` (`id`),
  CONSTRAINT `fk_role_leave_roleLevel_34` FOREIGN KEY (`role_level_id`) REFERENCES `role_level` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.role_leave: ~0 rows (approximately)
/*!40000 ALTER TABLE `role_leave` DISABLE KEYS */;
/*!40000 ALTER TABLE `role_leave` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.role_level
CREATE TABLE IF NOT EXISTS `role_level` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_x_id` bigint(20) DEFAULT NULL,
  `role_level` int(11) DEFAULT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  `reporting_to` varchar(255) DEFAULT NULL,
  `final_approval` varchar(255) DEFAULT NULL,
  `permissions` varchar(700) DEFAULT NULL,
  `role_description` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `department_id` bigint(20) DEFAULT NULL,
  `last_update` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_role_level_roleX_36` (`role_x_id`),
  KEY `ix_role_level_department_37` (`department_id`),
  CONSTRAINT `fk_role_level_department_37` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`),
  CONSTRAINT `fk_role_level_roleX_36` FOREIGN KEY (`role_x_id`) REFERENCES `role_x` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.role_level: ~15 rows (approximately)
/*!40000 ALTER TABLE `role_level` DISABLE KEYS */;
INSERT INTO `role_level` (`id`, `role_x_id`, `role_level`, `role_name`, `reporting_to`, `final_approval`, `permissions`, `role_description`, `parent_id`, `department_id`, `last_update`) VALUES
	(1, NULL, NULL, 'Head', NULL, NULL, NULL, 'Owner', NULL, NULL, '2015-04-23 18:18:26'),
	(2, NULL, NULL, 'CEO', NULL, NULL, NULL, 'Chief Executive Officer', 1, NULL, '2015-04-23 18:18:28'),
	(3, NULL, NULL, 'CTO', NULL, NULL, 'ManageUser|ManageClient|ManageProject|ManageTask|ApplyLeave|CreateTimesheet|SearchTimesheet|CreateTimesheets|Today|Week|Month|Holiday|TodayAll|WeekReport|Delegate|RolePermissions|UserPermissions|MyBucket|LeaveBucket|TeamRate|ProjectReport|Mail|Notification|DefineRoles|DefineLeaves|OrgHierarchy|ManageSupplier|ManageProjects|DefineDepartments|DefineFlexiAttribute|Case|LeaveSettings|CaseTo|', 'Chief technical Officer', 1, NULL, '2015-04-23 18:18:29'),
	(4, NULL, NULL, 'CFO', NULL, NULL, 'ManageUser|ManageClient|ManageProject|ManageTask|ApplyLeave|CreateTimesheet|SearchTimesheet|CreateTimesheets|Today|Week|Month|Holiday|TodayAll|WeekReport|Delegate|RolePermissions|UserPermissions|MyBucket|LeaveBucket|TeamRate|ProjectReport|Mail|Notification|DefineRoles|DefineLeaves|OrgHierarchy|ManageSupplier|ManageProjects|DefineDepartments|DefineFlexiAttribute|Case|LeaveSettings|CaseTo|', 'Chief Financial Officer', 1, NULL, '2015-04-23 18:18:30'),
	(5, NULL, NULL, 'VP (Managment)', NULL, NULL, NULL, 'Vice President - Management', 2, NULL, '2015-04-23 18:18:31'),
	(6, NULL, NULL, 'VP (Development)', NULL, NULL, NULL, 'Vice President - Development', 3, NULL, '2015-04-23 18:18:32'),
	(7, NULL, NULL, 'Director', NULL, NULL, NULL, 'Director', 6, NULL, '2015-04-23 18:18:33'),
	(8, NULL, NULL, 'Project Manager', NULL, NULL, NULL, 'Project Manager', 7, NULL, '2015-04-23 18:18:34'),
	(9, NULL, NULL, 'Sr Development Expert', NULL, NULL, NULL, 'Sr Development Expert', 7, NULL, '2015-04-23 18:18:35'),
	(10, NULL, NULL, 'Group Lead', NULL, NULL, NULL, 'Group Lead Development', 8, NULL, '2015-04-23 18:18:36'),
	(11, NULL, NULL, 'Sr Subject Mater Expert', NULL, NULL, NULL, 'Sr Subject Mater Expert', 10, NULL, '2015-04-23 18:18:37'),
	(12, NULL, NULL, 'Subject Mater Expert', NULL, NULL, NULL, 'Subject Mater Expert', 10, NULL, '2015-04-23 18:18:38'),
	(13, NULL, NULL, 'Development Expert', NULL, NULL, NULL, 'Development Expert', 8, NULL, '2015-04-23 18:18:39'),
	(14, NULL, NULL, 'VP', NULL, NULL, NULL, 'Vice President Finance', 4, 7, '2015-04-23 18:32:18'),
	(15, NULL, NULL, 'Manager', NULL, NULL, NULL, 'HR Manager', 5, 8, '2015-04-23 18:40:20');
/*!40000 ALTER TABLE `role_level` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.role_x
CREATE TABLE IF NOT EXISTS `role_x` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_role_x_company_38` (`company_id`),
  CONSTRAINT `fk_role_x_company_38` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.role_x: ~1 rows (approximately)
/*!40000 ALTER TABLE `role_x` DISABLE KEYS */;
INSERT INTO `role_x` (`id`, `company_id`) VALUES
	(2, 1),
	(1, 2),
	(3, 2);
/*!40000 ALTER TABLE `role_x` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.saveattributes
CREATE TABLE IF NOT EXISTS `saveattributes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `attribut_value` varchar(255) DEFAULT NULL,
  `attribut_name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `projectattrid` bigint(20) DEFAULT NULL,
  `projectinstancenode_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.saveattributes: ~0 rows (approximately)
/*!40000 ALTER TABLE `saveattributes` DISABLE KEYS */;
/*!40000 ALTER TABLE `saveattributes` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.supplier
CREATE TABLE IF NOT EXISTS `supplier` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `supplier_name` varchar(255) DEFAULT NULL,
  `phone_no` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
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
  KEY `ix_supplier_company_39` (`company_id`),
  CONSTRAINT `fk_supplier_company_39` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.supplier: ~2 rows (approximately)
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` (`id`, `supplier_name`, `phone_no`, `email`, `password`, `fax`, `address`, `street`, `city`, `country`, `pin`, `contact_name`, `contact_phone`, `contact_email`, `company_id`) VALUES
	(1, 'Liabily LLP', '9028022291', 'jagbir.paul@gmail.com', 'qwer123', NULL, 'Singapore', 'Singapore', 'Singapore', 'Singapore', '32123', 'Billy ', '9028022291', 'billy@liabilly.com', 1),
	(2, 'Strynet LLP', '4908789323', 'tarun.bansal@gmail.com', 'qwer123', NULL, 'Australia', 'Australia', 'Australia', 'Singapore', '32123', 'Tarun bansal', '9028022291', 'billy@liabilly.com', 1);
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.supplier_flexi
CREATE TABLE IF NOT EXISTS `supplier_flexi` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `supplier_id` bigint(20) DEFAULT NULL,
  `flexi_id` bigint(20) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_supplier_flexi_supplier_40` (`supplier_id`),
  CONSTRAINT `fk_supplier_flexi_supplier_40` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.supplier_flexi: ~0 rows (approximately)
/*!40000 ALTER TABLE `supplier_flexi` DISABLE KEYS */;
/*!40000 ALTER TABLE `supplier_flexi` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.task
CREATE TABLE IF NOT EXISTS `task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(255) DEFAULT NULL,
  `task_code` varchar(255) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `effort` int(11) DEFAULT NULL,
  `is_billable` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.task: ~0 rows (approximately)
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
/*!40000 ALTER TABLE `task` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.task_comment
CREATE TABLE IF NOT EXISTS `task_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  `task_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_task_comment_user` (`user_id`),
  CONSTRAINT `FK_task_comment_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- Dumping data for table timemgmt1.task_comment: ~4 rows (approximately)
/*!40000 ALTER TABLE `task_comment` DISABLE KEYS */;
INSERT INTO `task_comment` (`id`, `date`, `comment`, `project_id`, `task_id`, `user_id`) VALUES
	(5, '2015-04-24 15:32:14', 'This is comment 1', 23, 76, 4),
	(6, '2015-04-24 15:32:39', 'This is comment 2', 23, 76, 4),
	(7, '2015-04-24 18:48:17', 'This comment for MRF', 23, 70, 4),
	(8, '2015-04-24 18:50:42', 'week comment', 23, 70, 4);
/*!40000 ALTER TABLE `task_comment` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.task_details
CREATE TABLE IF NOT EXISTS `task_details` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(20) DEFAULT NULL,
  `task_id` bigint(20) DEFAULT NULL,
  `start_time` varchar(255) DEFAULT NULL,
  `end_time` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `file_path` varchar(255) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_task_details_user` (`user_id`),
  CONSTRAINT `FK_task_details_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- Dumping data for table timemgmt1.task_details: ~4 rows (approximately)
/*!40000 ALTER TABLE `task_details` DISABLE KEYS */;
INSERT INTO `task_details` (`id`, `project_id`, `task_id`, `start_time`, `end_time`, `status`, `date`, `file_path`, `file_name`, `comment`, `user_id`) VALUES
	(12, 23, 76, '12:00', '13:00', 'Inprogress', '2015-04-24 00:00:00', 'D:\\dev\\time-images\\taskAttachment\\Global_4.java', 'Global', NULL, 4),
	(13, 23, 70, '10:00', '11:00', 'Inprogress', '2015-04-20 00:00:00', 'D:\\dev\\time-images\\taskAttachment\\DailyReport_4.pdf', 'DailyReport', NULL, 4),
	(14, 23, 70, '10:00', '11:00', 'Inprogress', '2015-04-24 00:00:00', 'D:\\dev\\time-images\\taskAttachment\\bugs_4.txt', 'bugs', NULL, 4),
	(15, 23, 76, '11:00', '12:00', 'Inprogress', '2015-04-24 00:00:00', 'D:\\dev\\time-images\\taskAttachment\\add_01_4.png', 'add_01', NULL, 4);
/*!40000 ALTER TABLE `task_details` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.task_flexi
CREATE TABLE IF NOT EXISTS `task_flexi` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_id` bigint(20) DEFAULT NULL,
  `flexi_id` bigint(20) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_task_flexi_task_41` (`task_id`),
  CONSTRAINT `fk_task_flexi_task_41` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.task_flexi: ~0 rows (approximately)
/*!40000 ALTER TABLE `task_flexi` DISABLE KEYS */;
/*!40000 ALTER TABLE `task_flexi` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.task_project
CREATE TABLE IF NOT EXISTS `task_project` (
  `task_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  PRIMARY KEY (`task_id`,`project_id`),
  KEY `fk_task_project_project_02` (`project_id`),
  CONSTRAINT `fk_task_project_project_02` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  CONSTRAINT `fk_task_project_task_01` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.task_project: ~0 rows (approximately)
/*!40000 ALTER TABLE `task_project` DISABLE KEYS */;
/*!40000 ALTER TABLE `task_project` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.timesheet
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
  KEY `ix_timesheet_user_42` (`user_id`),
  KEY `ix_timesheet_timesheetWith_43` (`timesheet_with_id`),
  CONSTRAINT `fk_timesheet_timesheetWith_43` FOREIGN KEY (`timesheet_with_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_timesheet_user_42` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.timesheet: ~8 rows (approximately)
/*!40000 ALTER TABLE `timesheet` DISABLE KEYS */;
INSERT INTO `timesheet` (`id`, `user_id`, `status`, `week_of_year`, `year`, `timesheet_with_id`, `level`, `process_instance_id`, `tid`, `last_update_date`) VALUES
	(2, 4, 0, 17, 2015, 4, 0, NULL, NULL, '2015-04-24 18:18:45'),
	(3, 4, 0, 16, 2015, 4, 0, NULL, NULL, '2015-04-24 18:16:46'),
	(5, 4, 0, 18, 2015, 4, 0, NULL, NULL, '2015-04-24 17:11:56'),
	(6, 4, 0, 19, 2015, 4, 0, NULL, NULL, '2015-04-24 17:18:35'),
	(7, 4, 0, 20, 2015, 4, 0, NULL, NULL, '2015-04-24 19:31:39'),
	(8, 4, 0, 21, 2015, 4, 0, NULL, NULL, '2015-04-24 19:45:52'),
	(10, 6, 0, 18, 2015, 6, 0, NULL, NULL, '2015-04-25 18:16:40'),
	(11, 6, 0, 17, 2015, 6, 0, NULL, NULL, '2015-04-25 19:42:54');
/*!40000 ALTER TABLE `timesheet` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.timesheet_actual
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
  KEY `ix_timesheet_actual_user_44` (`user_id`),
  KEY `ix_timesheet_actual_timesheet_45` (`timesheet_with_id`),
  CONSTRAINT `fk_timesheet_actual_timesheet_45` FOREIGN KEY (`timesheet_with_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_timesheet_actual_user_44` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.timesheet_actual: ~4 rows (approximately)
/*!40000 ALTER TABLE `timesheet_actual` DISABLE KEYS */;
INSERT INTO `timesheet_actual` (`id`, `user_id`, `status`, `week_of_year`, `year`, `timesheet_with_id`, `level`, `process_instance_id`, `tid`, `last_update_date`) VALUES
	(1, 4, 0, 17, 2015, 4, 0, NULL, NULL, '2015-04-24 18:37:13'),
	(2, 4, 0, 16, 2015, 4, 0, NULL, NULL, '2015-04-24 16:26:35'),
	(3, 4, 0, 15, 2015, 4, 0, NULL, NULL, '2015-04-24 19:55:01'),
	(4, 4, 0, 18, 2015, 4, 0, NULL, NULL, '2015-04-24 19:58:39');
/*!40000 ALTER TABLE `timesheet_actual` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.timesheet_days
CREATE TABLE IF NOT EXISTS `timesheet_days` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `day` varchar(255) DEFAULT NULL,
  `timesheet_date` date DEFAULT NULL,
  `time_from` varchar(255) DEFAULT NULL,
  `time_to` varchar(255) DEFAULT NULL,
  `work_minutes` int(11) DEFAULT NULL,
  `timesheet_row_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_timesheet_days_timesheetRo_46` (`timesheet_row_id`),
  CONSTRAINT `fk_timesheet_days_timesheetRo_46` FOREIGN KEY (`timesheet_row_id`) REFERENCES `timesheet_row` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=153 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.timesheet_days: ~84 rows (approximately)
/*!40000 ALTER TABLE `timesheet_days` DISABLE KEYS */;
INSERT INTO `timesheet_days` (`id`, `day`, `timesheet_date`, `time_from`, `time_to`, `work_minutes`, `timesheet_row_id`) VALUES
	(1, 'monday', '2015-04-20', '10:00', '11:00', 60, 1),
	(2, 'tuesday', '2015-04-21', '11:00', '12:00', 60, 1),
	(3, 'wednesday', '2015-04-22', '12:00', '13:00', 60, 1),
	(4, 'thursday', '2015-04-23', '10:00', '11:00', 60, 1),
	(5, 'friday', '2015-04-24', '10:00', '16:30', 390, 1),
	(6, 'saturday', '2015-04-25', '10:00', '11:00', 60, 1),
	(7, 'sunday', '2015-04-26', '10:00', '12:00', 120, 1),
	(8, 'monday', '2015-04-13', NULL, NULL, NULL, 2),
	(9, 'tuesday', '2015-04-14', NULL, NULL, NULL, 2),
	(10, 'wednesday', '2015-04-15', '13:00', '14:00', 60, 2),
	(11, 'thursday', '2015-04-16', NULL, NULL, NULL, 2),
	(12, 'friday', '2015-04-17', '16:00', '17:00', 60, 2),
	(13, 'saturday', '2015-04-18', NULL, NULL, NULL, 2),
	(14, 'sunday', '2015-04-19', NULL, NULL, NULL, 2),
	(15, 'monday', '2015-04-13', NULL, NULL, NULL, 3),
	(16, 'tuesday', '2015-04-14', '7:00', '8:00', 60, 3),
	(17, 'wednesday', '2015-04-15', '12:00', '12:30', 30, 3),
	(18, 'thursday', '2015-04-16', NULL, NULL, NULL, 3),
	(19, 'friday', '2015-04-17', NULL, NULL, NULL, 3),
	(20, 'saturday', '2015-04-18', NULL, NULL, NULL, 3),
	(21, 'sunday', '2015-04-19', NULL, NULL, NULL, 3),
	(22, 'monday', '2015-04-20', '11:00', '12:00', 60, 4),
	(23, 'tuesday', '2015-04-21', '12:00', '13:00', 60, 4),
	(24, 'wednesday', '2015-04-22', '12:00', '13:00', 60, 4),
	(25, 'thursday', '2015-04-23', '12:00', '13:00', 60, 4),
	(26, 'friday', '2015-04-24', '12:00', '13:00', 60, 4),
	(27, 'saturday', '2015-04-25', '12:00', '13:00', 60, 4),
	(28, 'sunday', '2015-04-26', '12:00', '13:00', 60, 4),
	(41, 'monday', '2015-04-27', '11:00', '12:00', 60, 7),
	(42, 'tuesday', '2015-04-28', '12:00', '13:00', 60, 7),
	(43, 'wednesday', '2015-04-29', NULL, NULL, NULL, 7),
	(44, 'thursday', '2015-04-30', NULL, NULL, NULL, 7),
	(45, 'friday', '2015-05-01', NULL, NULL, NULL, 7),
	(46, 'saturday', '2015-05-02', NULL, NULL, NULL, 7),
	(47, 'sunday', '2015-05-03', NULL, NULL, NULL, 7),
	(55, 'monday', '2015-05-04', '10:00', '11:00', 60, 9),
	(56, 'tuesday', '2015-05-05', '11:00', '12:00', 60, 9),
	(57, 'wednesday', '2015-05-06', NULL, NULL, NULL, 9),
	(58, 'thursday', '2015-05-07', NULL, NULL, NULL, 9),
	(59, 'friday', '2015-05-08', NULL, NULL, NULL, 9),
	(60, 'saturday', '2015-05-09', NULL, NULL, NULL, 9),
	(61, 'sunday', '2015-05-10', NULL, NULL, NULL, 9),
	(69, 'monday', '2015-05-11', '11:00', '12:00', 60, 11),
	(70, 'tuesday', '2015-05-12', NULL, NULL, NULL, 11),
	(71, 'wednesday', '2015-05-13', NULL, NULL, NULL, 11),
	(72, 'thursday', '2015-05-14', NULL, NULL, NULL, 11),
	(73, 'friday', '2015-05-15', NULL, NULL, NULL, 11),
	(74, 'saturday', '2015-05-16', NULL, NULL, NULL, 11),
	(75, 'sunday', '2015-05-17', NULL, NULL, NULL, 11),
	(76, 'monday', '2015-05-18', '8:00', '9:00', 60, 12),
	(77, 'tuesday', '2015-05-19', NULL, NULL, NULL, 12),
	(78, 'wednesday', '2015-05-20', NULL, NULL, NULL, 12),
	(79, 'thursday', '2015-05-21', NULL, NULL, NULL, 12),
	(80, 'friday', '2015-05-22', NULL, NULL, NULL, 12),
	(81, 'saturday', '2015-05-23', NULL, NULL, NULL, 12),
	(82, 'sunday', '2015-05-17', NULL, NULL, NULL, 12),
	(83, 'monday', '2015-05-04', '11:00', '12:00', 60, 13),
	(84, 'tuesday', '2015-05-05', '12:00', '13:00', 60, 13),
	(85, 'wednesday', '2015-05-06', NULL, NULL, NULL, 13),
	(86, 'thursday', '2015-05-07', NULL, NULL, NULL, 13),
	(87, 'friday', '2015-05-08', NULL, NULL, NULL, 13),
	(88, 'saturday', '2015-05-09', NULL, NULL, NULL, 13),
	(89, 'sunday', '2015-05-10', NULL, NULL, NULL, 13),
	(90, 'monday', '2015-05-18', '10:00', '11:00', 60, 14),
	(91, 'tuesday', '2015-05-19', NULL, NULL, NULL, 14),
	(92, 'wednesday', '2015-05-20', NULL, NULL, NULL, 14),
	(93, 'thursday', '2015-05-21', NULL, NULL, NULL, 14),
	(94, 'friday', '2015-05-22', NULL, NULL, NULL, 14),
	(95, 'saturday', '2015-05-23', NULL, NULL, NULL, 14),
	(96, 'sunday', '2015-05-24', NULL, NULL, NULL, 14),
	(111, 'monday', '2015-04-27', '10:00', '11:00', 60, 17),
	(112, 'tuesday', '2015-04-28', '11:00', '12:00', 60, 17),
	(113, 'wednesday', '2015-04-29', NULL, NULL, NULL, 17),
	(114, 'thursday', '2015-04-30', NULL, NULL, NULL, 17),
	(115, 'friday', '2015-05-01', NULL, NULL, NULL, 17),
	(116, 'saturday', '2015-05-02', NULL, NULL, NULL, 17),
	(117, 'sunday', '2015-05-03', NULL, NULL, NULL, 17),
	(146, 'monday', '2015-04-20', '10:00', '11:00', 60, 22),
	(147, 'tuesday', '2015-04-21', '12:00', '13:00', 60, 22),
	(148, 'wednesday', '2015-04-22', '13:00', '14:00', 60, 22),
	(149, 'thursday', '2015-04-23', '14:00', '15:00', 60, 22),
	(150, 'friday', '2015-04-24', '15:00', '16:00', 60, 22),
	(151, 'saturday', '2015-04-25', '17:00', '18:00', 60, 22),
	(152, 'sunday', '2015-04-26', '18:00', '19:00', 60, 22);
/*!40000 ALTER TABLE `timesheet_days` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.timesheet_days_actual
CREATE TABLE IF NOT EXISTS `timesheet_days_actual` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `day` varchar(255) DEFAULT NULL,
  `timesheet_date` date DEFAULT NULL,
  `time_from` varchar(255) DEFAULT NULL,
  `time_to` varchar(255) DEFAULT NULL,
  `work_minutes` int(11) DEFAULT NULL,
  `timesheet_row_actual_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_timesheet_days_actual_time_47` (`timesheet_row_actual_id`),
  CONSTRAINT `fk_timesheet_days_actual_time_47` FOREIGN KEY (`timesheet_row_actual_id`) REFERENCES `timesheet_row_actual` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.timesheet_days_actual: ~37 rows (approximately)
/*!40000 ALTER TABLE `timesheet_days_actual` DISABLE KEYS */;
INSERT INTO `timesheet_days_actual` (`id`, `day`, `timesheet_date`, `time_from`, `time_to`, `work_minutes`, `timesheet_row_actual_id`) VALUES
	(1, 'monday', '2015-04-20', '12:00', '13:00', 60, 1),
	(2, 'tuesday', '2015-04-21', '14:00', '15:00', 60, 1),
	(3, 'wednesday', '2015-04-22', '13:00', '14:00', 60, 1),
	(4, 'thursday', '2015-04-23', '15:00', '16:00', 60, 1),
	(5, 'friday', '2015-04-24', NULL, NULL, NULL, 1),
	(6, 'saturday', '2015-04-25', NULL, NULL, NULL, 1),
	(7, 'sunday', '2015-04-26', NULL, NULL, NULL, 1),
	(8, 'monday', '2015-04-13', '12:00', '13:00', 60, 2),
	(9, 'tuesday', '2015-04-14', NULL, NULL, NULL, 2),
	(10, 'wednesday', '2015-04-15', NULL, NULL, NULL, 2),
	(11, 'thursday', '2015-04-16', NULL, NULL, NULL, 2),
	(12, 'friday', '2015-04-17', NULL, NULL, NULL, 2),
	(13, 'saturday', '2015-04-18', NULL, NULL, NULL, 2),
	(14, 'sunday', '2015-04-19', NULL, NULL, NULL, 2),
	(22, 'monday', '2015-04-06', '11:00', '12:00', 60, 4),
	(23, 'tuesday', '2015-04-07', NULL, NULL, NULL, 4),
	(24, 'wednesday', '2015-04-08', '13:00', '13:30', 30, 4),
	(25, 'thursday', '2015-04-09', NULL, NULL, NULL, 4),
	(26, 'friday', '2015-04-10', NULL, NULL, NULL, 4),
	(27, 'saturday', '2015-04-11', NULL, NULL, NULL, 4),
	(28, 'sunday', '2015-04-12', NULL, NULL, NULL, 4),
	(50, 'monday', '2015-04-06', '10:00', '11:00', 60, 8),
	(51, 'tuesday', '2015-04-07', NULL, NULL, NULL, 8),
	(52, 'wednesday', '2015-04-08', NULL, NULL, NULL, 8),
	(53, 'thursday', '2015-04-09', NULL, NULL, NULL, 8),
	(54, 'friday', '2015-04-10', NULL, NULL, NULL, 8),
	(55, 'saturday', '2015-04-11', NULL, NULL, NULL, 8),
	(56, 'sunday', '2015-04-12', NULL, NULL, NULL, 8),
	(57, 'monday', '2015-04-27', '11:00', '12:00', 60, 9),
	(58, 'tuesday', '2015-04-28', NULL, NULL, NULL, 9),
	(59, 'wednesday', '2015-04-29', NULL, NULL, NULL, 9),
	(60, 'thursday', '2015-04-30', NULL, NULL, NULL, 9),
	(61, 'friday', '2015-05-01', NULL, NULL, NULL, 9),
	(62, 'saturday', '2015-05-02', NULL, NULL, NULL, 9),
	(63, 'sunday', '2015-05-03', NULL, NULL, NULL, 9);
/*!40000 ALTER TABLE `timesheet_days_actual` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.timesheet_row
CREATE TABLE IF NOT EXISTS `timesheet_row` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `timesheet_id` bigint(20) DEFAULT NULL,
  `project_code` varchar(255) DEFAULT NULL,
  `task_code` varchar(255) DEFAULT NULL,
  `over_time` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `ix_timesheet_row_timesheet_48` (`timesheet_id`),
  CONSTRAINT `fk_timesheet_row_timesheet_48` FOREIGN KEY (`timesheet_id`) REFERENCES `timesheet` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.timesheet_row: ~12 rows (approximately)
/*!40000 ALTER TABLE `timesheet_row` DISABLE KEYS */;
INSERT INTO `timesheet_row` (`id`, `timesheet_id`, `project_code`, `task_code`, `over_time`) VALUES
	(1, 2, '23', '70', 0),
	(2, 3, '23', '70', 0),
	(3, 3, '23', '69', 0),
	(4, 2, '23', '76', 0),
	(7, 5, '23', '69', 0),
	(9, 6, '23', '69', 0),
	(11, 7, '23', '69', 0),
	(12, 7, '23', '70', 0),
	(13, 6, '23', '70', 0),
	(14, 8, '23', '76', 0),
	(17, 10, '23', '69', 0),
	(22, 11, '23', '69', 0);
/*!40000 ALTER TABLE `timesheet_row` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.timesheet_row_actual
CREATE TABLE IF NOT EXISTS `timesheet_row_actual` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `timesheet_actual_id` bigint(20) DEFAULT NULL,
  `project_code` varchar(255) DEFAULT NULL,
  `task_code` varchar(255) DEFAULT NULL,
  `over_time` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `ix_timesheet_row_actual_times_49` (`timesheet_actual_id`),
  CONSTRAINT `fk_timesheet_row_actual_times_49` FOREIGN KEY (`timesheet_actual_id`) REFERENCES `timesheet_actual` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.timesheet_row_actual: ~5 rows (approximately)
/*!40000 ALTER TABLE `timesheet_row_actual` DISABLE KEYS */;
INSERT INTO `timesheet_row_actual` (`id`, `timesheet_actual_id`, `project_code`, `task_code`, `over_time`) VALUES
	(1, 1, '23', '76', 0),
	(2, 2, '23', '69', 0),
	(4, 3, '23', '69', 0),
	(8, 3, '23', '70', 0),
	(9, 4, '23', '70', 0);
/*!40000 ALTER TABLE `timesheet_row_actual` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.user
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
  `department` varchar(255) DEFAULT NULL,
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
  `usertype` varchar(50) DEFAULT NULL,
  `organization_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_user_email` (`email`),
  KEY `ix_user_companyobject_50` (`companyobject_id`),
  KEY `ix_user_role_51` (`role_id`),
  KEY `ix_user_level_52` (`level_id`),
  KEY `ix_user_manager_53` (`manager_id`),
  KEY `ix_user_hrmanager_54` (`hrmanager_id`),
  KEY `FK_user_organization` (`organization_id`),
  CONSTRAINT `FK_user_organization` FOREIGN KEY (`organization_id`) REFERENCES `organization` (`id`),
  CONSTRAINT `fk_user_companyobject_50` FOREIGN KEY (`companyobject_id`) REFERENCES `company` (`id`),
  CONSTRAINT `fk_user_hrmanager_54` FOREIGN KEY (`hrmanager_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_user_level_52` FOREIGN KEY (`level_id`) REFERENCES `leave_level` (`id`),
  CONSTRAINT `fk_user_manager_53` FOREIGN KEY (`manager_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_user_role_51` FOREIGN KEY (`role_id`) REFERENCES `role_level` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.user: ~9 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `salutation`, `employee_id`, `first_name`, `middle_name`, `last_name`, `email`, `gender`, `status`, `hire_date`, `annual_income`, `hourlyrate`, `companyobject_id`, `designation`, `department`, `role_id`, `level_id`, `manager_id`, `release_date`, `hrmanager_id`, `permissions`, `temp_password`, `password`, `reset_flag`, `failed_login_attempt`, `create_date`, `modified_date`, `password_reset`, `user_status`, `process_instance_id`, `last_update`, `usertype`, `organization_id`) VALUES
	(1, 'Mr', '1', 'jagbir', 'singh', 'paul', 'mindnervestech@gmail.com', NULL, 'OnRolls', NULL, NULL, NULL, 1, 'SuperAdmin', NULL, NULL, NULL, NULL, NULL, NULL, 'SearchTimesheet|CreateTimesheet|Delegate|Leaves|ApplyLeave|FeedBackCreate|FeedBackView|UserPermissions|LeaveBucket|MyBucket|TeamRate|ProjectReport|ManageUser|ManageProject|ManageTask|ManageClient|Notification|DefineRoles|OrgHierarchy|Mail|RolePermissions', 0, 'sa', NULL, NULL, NULL, NULL, 0, 'Approved', NULL, '2015-02-19 15:32:01', NULL, NULL),
	(2, NULL, NULL, 'mindnerves.com Admin', NULL, NULL, 'amit.goyal@mindnerves.com', NULL, NULL, NULL, NULL, NULL, 1, 'Admin', NULL, NULL, NULL, NULL, NULL, NULL, 'Delegate|FeedBackCreate|FeedBackView|TeamRate|ProjectReport|Notification|ApplyLeave|Leaves|Timesheet|CreateTimesheet|SearchTimesheet|Mail|UserRequest|DefineRoles|OrgHierarchy|DefineProjects|UserPermissions|ManageUser|ManageProject|ManageTask|ManageClient|LeaveSettings|RolePermissions|DefineLeaves|DefineDepartments|ManageSupplier|DefineFlexiAttribute|', 0, 'sa', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2015-04-22 12:29:42', NULL, NULL),
	(3, NULL, NULL, 'Sumit', 'M', 'Pathak', 'sumitp@mindnerves.com', NULL, 'OnRolls', NULL, NULL, NULL, 1, 'VP (Managment)', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 'qwer123', NULL, NULL, NULL, NULL, NULL, 'PendingApproval', NULL, '2015-04-22 12:21:19', NULL, NULL),
	(4, NULL, '4', 'gmail.com Admin', NULL, NULL, 'jagbir.paul@gmail.com', NULL, NULL, NULL, NULL, NULL, 2, 'Admin', NULL, NULL, NULL, NULL, NULL, NULL, 'ManageUser|ManageClient|ManageProject|ManageTask|ApplyLeave|CreateTimesheet|SearchTimesheet|CreateTimesheets|Today|Week|Month|Holiday|TodayAll|WeekReport|Delegate|RolePermissions|UserPermissions|MyBucket|LeaveBucket|TeamRate|ProjectReport|Mail|Notification|DefineRoles|DefineLeaves|OrgHierarchy|ManageSupplier|ManageProjects|DefineDepartments|DefineFlexiAttribute|Case|LeaveSettings|CaseTo|', 0, 'qwe123', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2015-04-24 12:25:16', NULL, NULL),
	(5, NULL, NULL, 'Akash', 'Sarpanch', 'Shinde', 'akash@mindnerves.com', NULL, 'OnRolls', NULL, NULL, NULL, 1, 'VP (Managment)', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 'qwer123', NULL, NULL, NULL, NULL, NULL, 'PendingApproval', NULL, '2015-04-22 12:28:12', NULL, NULL),
	(6, 'Mr', '1', 'Jagbir', 'Paul', 'Singh', 'jagbirs@mindnerves.com', 'Male', 'OnRolls', '2012-01-04 00:00:00', 123456, 59.35, 1, 'CTO', 'Development', 3, NULL, 4, NULL, NULL, 'ManageUser|ManageClient|ManageProject|ManageTask|ApplyLeave|CreateTimesheet|SearchTimesheet|CreateTimesheets|Today|Week|Month|Holiday|TodayAll|WeekReport|Delegate|RolePermissions|UserPermissions|MyBucket|LeaveBucket|TeamRate|ProjectReport|Mail|Notification|DefineRoles|DefineLeaves|OrgHierarchy|ManageSupplier|ManageProjects|DefineDepartments|DefineFlexiAttribute|Case|LeaveSettings|CaseTo|', 0, 'qwe123', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2015-04-24 13:09:35', NULL, NULL),
	(7, 'Mr', '2', 'Amit', '', 'Goyal', 'amitg@mindnerves.com', 'Male', 'OnRolls', '2012-01-04 00:00:00', 123456, 59.35, 1, 'CEO', 'Marketing', 2, NULL, 4, NULL, NULL, 'ManageUser|ManageClient|ManageProject|ManageTask|ApplyLeave|CreateTimesheet|SearchTimesheet|CreateTimesheets|Today|Week|Month|Holiday|TodayAll|WeekReport|Delegate|RolePermissions|UserPermissions|MyBucket|LeaveBucket|TeamRate|ProjectReport|Mail|Notification|DefineRoles|DefineLeaves|OrgHierarchy|ManageSupplier|ManageProjects|DefineDepartments|DefineFlexiAttribute|Case|LeaveSettings|CaseTo|', 0, 'qwe123', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2015-04-25 19:47:18', NULL, NULL),
	(8, NULL, NULL, 'software', 'abc', 'engineer', 'abc@gmail.com', NULL, 'OnRolls', NULL, NULL, NULL, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 'qwe123', NULL, NULL, NULL, NULL, NULL, 'PendingApproval', NULL, '2015-04-24 21:41:53', NULL, NULL),
	(9, 'Mr', '123', 'Yogesh', 'pqr', 'Patil', 'yogesh@gmail.com', 'Male', 'OnRolls', '2015-04-15 00:00:00', 1234, 23, 2, 'Development Expert', 'Development', 13, NULL, 4, '2015-04-29 00:00:00', NULL, NULL, 0, 'qwe123', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2015-04-24 21:58:37', NULL, NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.user_flexi
CREATE TABLE IF NOT EXISTS `user_flexi` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `flexi_id` bigint(20) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_user_flexi_user_55` (`user_id`),
  CONSTRAINT `fk_user_flexi_user_55` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.user_flexi: ~0 rows (approximately)
/*!40000 ALTER TABLE `user_flexi` DISABLE KEYS */;
INSERT INTO `user_flexi` (`id`, `user_id`, `flexi_id`, `value`) VALUES
	(1, 7, 1, '[{"n":"human.jpg","s":7932,"ct":"image/jpeg"}]'),
	(2, 9, 1, '');
/*!40000 ALTER TABLE `user_flexi` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.user_leave
CREATE TABLE IF NOT EXISTS `user_leave` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `leave_type` int(11) DEFAULT NULL,
  `from_date` datetime DEFAULT NULL,
  `to_date` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_user_leave_user_56` (`user_id`),
  CONSTRAINT `fk_user_leave_user_56` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.user_leave: ~6 rows (approximately)
/*!40000 ALTER TABLE `user_leave` DISABLE KEYS */;
INSERT INTO `user_leave` (`id`, `user_id`, `reason`, `leave_type`, `from_date`, `to_date`, `status`) VALUES
	(1, 4, 'Weekly Leaves', 0, NULL, NULL, NULL),
	(3, 4, 'leave', 7, '2015-05-06 00:00:00', NULL, 'un-assigned'),
	(4, 4, 'holiday', 7, '2015-06-10 00:00:00', NULL, 'un-assigned'),
	(5, 4, 'holiday', 7, '2015-06-17 00:00:00', NULL, 'un-assigned'),
	(7, 4, 'bday', 7, '2015-07-08 00:00:00', '2015-07-08 00:00:00', 'un-assigned'),
	(8, 4, 'hhh', 7, '2015-04-25 00:00:00', NULL, 'un-assigned');
/*!40000 ALTER TABLE `user_leave` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.user_project
CREATE TABLE IF NOT EXISTS `user_project` (
  `user_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`project_id`),
  KEY `fk_user_project_project_02` (`project_id`),
  CONSTRAINT `fk_user_project_project_02` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  CONSTRAINT `fk_user_project_user_01` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.user_project: ~0 rows (approximately)
/*!40000 ALTER TABLE `user_project` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_project` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
