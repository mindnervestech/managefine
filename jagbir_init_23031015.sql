-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.5.37 - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             9.1.0.4916
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

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
DELETE FROM `apply_leave`;
/*!40000 ALTER TABLE `apply_leave` DISABLE KEYS */;
/*!40000 ALTER TABLE `apply_leave` ENABLE KEYS */;


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
  KEY `ix_client_company_3` (`company_id`),
  CONSTRAINT `fk_client_company_3` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.client: ~0 rows (approximately)
DELETE FROM `client`;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
/*!40000 ALTER TABLE `client` ENABLE KEYS */;


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
DELETE FROM `company`;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` (`id`, `company_name`, `company_code`, `company_email`, `company_phone`, `address`, `company_status`) VALUES
	(1, 'mindnerves', 'gmail.com', 'mindnervestech@gmail.com', '223445', 'hadapser,pune-411001', 'Approved'),
	(2, 'google', 'ggg.com', 'google@ggg.com', '2342534', 'asdasd,asdfsfds,sdfs', 'Approved');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.delegate
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

-- Dumping data for table timemgmt1.delegate: ~0 rows (approximately)
DELETE FROM `delegate`;
/*!40000 ALTER TABLE `delegate` DISABLE KEYS */;
/*!40000 ALTER TABLE `delegate` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.feedback
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

-- Dumping data for table timemgmt1.feedback: ~0 rows (approximately)
DELETE FROM `feedback`;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.flexi_attribute
CREATE TABLE IF NOT EXISTS `flexi_attribute` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `model` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.flexi_attribute: ~5 rows (approximately)
DELETE FROM `flexi_attribute`;
/*!40000 ALTER TABLE `flexi_attribute` DISABLE KEYS */;
INSERT INTO `flexi_attribute` (`id`, `model`, `type`, `name`) VALUES
	(1, 'models.UserFlexi', 'string', 'Name'),
	(2, 'models.UserFlexi', 'string', 'Address'),
	(3, 'models.UserFlexi', 'textarea', 'Address 1'),
	(4, 'models.UserFlexi', 'date', 'Address 2'),
	(5, 'models.UserFlexi', 'FILE', 'Doc');
/*!40000 ALTER TABLE `flexi_attribute` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.leave_balance
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.leave_balance: ~0 rows (approximately)
DELETE FROM `leave_balance`;
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
  KEY `ix_leave_level_leaveX_10` (`leave_x_id`),
  KEY `ix_leave_level_roleLeave_11` (`role_leave_id`),
  CONSTRAINT `fk_leave_level_leaveX_10` FOREIGN KEY (`leave_x_id`) REFERENCES `leave_x` (`id`),
  CONSTRAINT `fk_leave_level_roleLeave_11` FOREIGN KEY (`role_leave_id`) REFERENCES `role_leave` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.leave_level: ~0 rows (approximately)
DELETE FROM `leave_level`;
/*!40000 ALTER TABLE `leave_level` DISABLE KEYS */;
/*!40000 ALTER TABLE `leave_level` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.leave_x
CREATE TABLE IF NOT EXISTS `leave_x` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_leave_x_company_12` (`company_id`),
  CONSTRAINT `fk_leave_x_company_12` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.leave_x: ~0 rows (approximately)
DELETE FROM `leave_x`;
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
  KEY `ix_mail_setting_companyObject_13` (`company_object_id`),
  CONSTRAINT `fk_mail_setting_companyObject_13` FOREIGN KEY (`company_object_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.mail_setting: ~2 rows (approximately)
DELETE FROM `mail_setting`;
/*!40000 ALTER TABLE `mail_setting` DISABLE KEYS */;
INSERT INTO `mail_setting` (`id`, `host_name`, `port_number`, `ssl_port`, `tls_port`, `user_name`, `password`, `company_object_id`, `last_update`) VALUES
	(4, 'harsh', '11', NULL, NULL, 'dhairyashil.bankar@gmail.com', 'abcd123', 1, '2014-02-25 10:23:21'),
	(5, NULL, NULL, NULL, NULL, 'google@ggg.com', NULL, 2, '2015-02-23 11:18:10');
/*!40000 ALTER TABLE `mail_setting` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.notification
CREATE TABLE IF NOT EXISTS `notification` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `setting_as_json` longtext,
  `company_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_notification_company_14` (`company_id`),
  CONSTRAINT `fk_notification_company_14` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.notification: ~2 rows (approximately)
DELETE FROM `notification`;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` (`id`, `setting_as_json`, `company_id`) VALUES
	(4, NULL, 1),
	(5, NULL, 2);
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
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.organization: ~2 rows (approximately)
DELETE FROM `organization`;
/*!40000 ALTER TABLE `organization` DISABLE KEYS */;
INSERT INTO `organization` (`id`, `organization_name`, `organization_type`, `organization_location`, `organization_profile_url`, `company_id`, `parent`) VALUES
	(13, 'aaa', 'aaa', 'aaa', 'org13.jpg', 2, NULL),
	(28, 'adf', 'werf', 'ujyt', 'org2_28.jpg', 2, 13);
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
  KEY `ix_project_clientName_15` (`client_name_id`),
  KEY `ix_project_projectManager_16` (`project_manager_id`),
  KEY `ix_project_companyObj_17` (`company_obj_id`),
  CONSTRAINT `fk_project_clientName_15` FOREIGN KEY (`client_name_id`) REFERENCES `client` (`id`),
  CONSTRAINT `fk_project_companyObj_17` FOREIGN KEY (`company_obj_id`) REFERENCES `company` (`id`),
  CONSTRAINT `fk_project_projectManager_16` FOREIGN KEY (`project_manager_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.project: ~2 rows (approximately)
DELETE FROM `project`;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` (`id`, `client_name_id`, `project_name`, `project_code`, `project_description`, `start_date`, `end_date`, `budget`, `currency`, `efforts`, `project_manager_id`, `company_obj_id`) VALUES
	(1, 1, 'Aptitude Test 2', 'AT-2', 'exam 2', '2014-01-20 00:00:00', '2014-02-10 00:00:00', 123456, 'INR', 2, 5, 1),
	(2, 1, 'hkj', 'fghk', '', '2014-02-04 00:00:00', '2014-02-27 00:00:00', 568, 'INR', 769, 7, 1);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.project_task
CREATE TABLE IF NOT EXISTS `project_task` (
  `project_id` bigint(20) NOT NULL,
  `task_id` bigint(20) NOT NULL,
  PRIMARY KEY (`project_id`,`task_id`),
  KEY `fk_project_task_task_02` (`task_id`),
  CONSTRAINT `fk_project_task_project_01` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  CONSTRAINT `fk_project_task_task_02` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.project_task: ~1 rows (approximately)
DELETE FROM `project_task`;
/*!40000 ALTER TABLE `project_task` DISABLE KEYS */;
INSERT INTO `project_task` (`project_id`, `task_id`) VALUES
	(1, 1);
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

-- Dumping data for table timemgmt1.project_user: ~4 rows (approximately)
DELETE FROM `project_user`;
/*!40000 ALTER TABLE `project_user` DISABLE KEYS */;
INSERT INTO `project_user` (`project_id`, `user_id`) VALUES
	(1, 5),
	(1, 6),
	(2, 7),
	(1, 9);
/*!40000 ALTER TABLE `project_user` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.role_leave
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.role_leave: ~0 rows (approximately)
DELETE FROM `role_leave`;
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
  `last_update` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_role_level_roleX_21` (`role_x_id`),
  CONSTRAINT `fk_role_level_roleX_21` FOREIGN KEY (`role_x_id`) REFERENCES `role_x` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.role_level: ~20 rows (approximately)
DELETE FROM `role_level`;
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
	(19, 2, 2, 'Group Lead', 'Project Manager', 'Project Manager', '', '2015-02-23 11:22:36'),
	(20, 2, 3, 'Project Manager', 'Project Manager', 'Project Manager', 'ManageUser|ManageClient|ManageProject|ManageTask|ApplyLeave|CreateTimesheet|SearchTimesheet|FeedBackCreate|FeedBackView|UserRequest|MyBucket|LeaveBucket|TeamRate|ProjectReport|', '2015-02-23 11:29:12');
/*!40000 ALTER TABLE `role_level` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.role_x
CREATE TABLE IF NOT EXISTS `role_x` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_role_x_company_22` (`company_id`),
  CONSTRAINT `fk_role_x_company_22` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.role_x: ~2 rows (approximately)
DELETE FROM `role_x`;
/*!40000 ALTER TABLE `role_x` DISABLE KEYS */;
INSERT INTO `role_x` (`id`, `company_id`) VALUES
	(1, 1),
	(2, 2);
/*!40000 ALTER TABLE `role_x` ENABLE KEYS */;


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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.task: ~2 rows (approximately)
DELETE FROM `task`;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` (`id`, `task_name`, `task_code`, `start_date`, `end_date`, `effort`, `is_billable`) VALUES
	(1, 'CSS', 't-2', '2014-01-29 00:00:00', '2014-01-30 00:00:00', 1, 'Yes'),
	(2, 'Billing 1', 'PS 1', '2014-01-30 00:00:00', '2014-02-28 00:00:00', 1, 'Yes');
/*!40000 ALTER TABLE `task` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.task_project
CREATE TABLE IF NOT EXISTS `task_project` (
  `task_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  PRIMARY KEY (`task_id`,`project_id`),
  KEY `fk_task_project_project_02` (`project_id`),
  CONSTRAINT `fk_task_project_project_02` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  CONSTRAINT `fk_task_project_task_01` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.task_project: ~1 rows (approximately)
DELETE FROM `task_project`;
/*!40000 ALTER TABLE `task_project` DISABLE KEYS */;
INSERT INTO `task_project` (`task_id`, `project_id`) VALUES
	(1, 1);
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
  KEY `ix_timesheet_user_23` (`user_id`),
  KEY `ix_timesheet_timesheetWith_24` (`timesheet_with_id`),
  CONSTRAINT `fk_timesheet_timesheetWith_24` FOREIGN KEY (`timesheet_with_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_timesheet_user_23` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.timesheet: ~4 rows (approximately)
DELETE FROM `timesheet`;
/*!40000 ALTER TABLE `timesheet` DISABLE KEYS */;
INSERT INTO `timesheet` (`id`, `user_id`, `status`, `week_of_year`, `year`, `timesheet_with_id`, `level`, `process_instance_id`, `tid`, `last_update_date`) VALUES
	(1, 1, 1, 5, 2014, 6, 2, '101', 'ae8e02b3-6577-4665-bb32-ba4ad109bc4b', '2014-01-29 00:00:00'),
	(2, 1, 3, 6, 2014, 5, 1, '201', 'b3be1ef1-934e-4369-ba56-852bdb2e2d61', '2014-02-03 00:00:00'),
	(3, 1, 0, 9, 2014, 9, 0, '416', 'b837f4dc-7d15-4705-ae34-f0c7c232ff88', '2014-03-01 00:00:00'),
	(4, 1, 0, 12, 2014, 9, 0, NULL, 'bf422fed-9c37-41d3-8980-1d2bca1af5b4', '2014-03-17 00:00:00');
/*!40000 ALTER TABLE `timesheet` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.timesheet_row
CREATE TABLE IF NOT EXISTS `timesheet_row` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `timesheet_id` bigint(20) DEFAULT NULL,
  `project_code` varchar(255) DEFAULT NULL,
  `task_code` varchar(255) DEFAULT NULL,
  `total_hrs` int(11) DEFAULT NULL,
  `sun` int(11) DEFAULT NULL,
  `mon` int(11) DEFAULT NULL,
  `tue` int(11) DEFAULT NULL,
  `wed` int(11) DEFAULT NULL,
  `thu` int(11) DEFAULT NULL,
  `fri` int(11) DEFAULT NULL,
  `sat` int(11) DEFAULT NULL,
  `over_time` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `ix_timesheet_row_timesheet_25` (`timesheet_id`),
  CONSTRAINT `fk_timesheet_row_timesheet_25` FOREIGN KEY (`timesheet_id`) REFERENCES `timesheet` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.timesheet_row: ~4 rows (approximately)
DELETE FROM `timesheet_row`;
/*!40000 ALTER TABLE `timesheet_row` DISABLE KEYS */;
INSERT INTO `timesheet_row` (`id`, `timesheet_id`, `project_code`, `task_code`, `total_hrs`, `sun`, `mon`, `tue`, `wed`, `thu`, `fri`, `sat`, `over_time`) VALUES
	(1, 1, 'AT-1', 't-1', 14, 2, 2, 2, 2, 2, 2, 2, NULL),
	(2, 2, 'AT-1', 't-1', 18, 1, 1, 12, 1, 1, 1, 1, NULL),
	(8, 4, 'AT-2', 't-2', 116, 1, 2, 3, 44, 54, 6, 6, NULL),
	(9, 3, 'AT-2', 't-2', 7, 1, 1, 1, 1, 1, 1, 1, 1);
/*!40000 ALTER TABLE `timesheet_row` ENABLE KEYS */;


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
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.user: ~11 rows (approximately)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `salutation`, `employee_id`, `first_name`, `middle_name`, `last_name`, `email`, `gender`, `status`, `hire_date`, `annual_income`, `hourlyrate`, `companyobject_id`, `designation`, `role_id`, `level_id`, `manager_id`, `release_date`, `hrmanager_id`, `permissions`, `temp_password`, `password`, `reset_flag`, `failed_login_attempt`, `create_date`, `modified_date`, `password_reset`, `user_status`, `process_instance_id`, `last_update`) VALUES
	(1, 'Mr', '1', 'jagbir', 'singh', 'paul', 'mindnervestech@gmail.com', NULL, 'OnRolls', NULL, NULL, NULL, 1, 'SuperAdmin', NULL, NULL, NULL, NULL, NULL, 'SearchTimesheet|CreateTimesheet|Delegate|Leaves|ApplyLeave|FeedBackCreate|FeedBackView|UserPermissions|LeaveBucket|MyBucket|TeamRate|ProjectReport|ManageUser|ManageProject|ManageTask|ManageClient|Notification|DefineRoles|OrgHierarchy|Mail|RolePermissions', 0, 'Qwe123', NULL, NULL, NULL, NULL, 0, 'Approved', NULL, '2015-02-19 15:32:01'),
	(2, NULL, NULL, 'Google', NULL, 'Admin', 'sa@ggg.com', NULL, NULL, NULL, NULL, NULL, 2, 'Admin', NULL, NULL, NULL, NULL, NULL, 'Delegate|FeedBackCreate|FeedBackView|TeamRate|ProjectReport|Notification|ApplyLeave|Leaves|Timesheet|CreateTimesheet|SearchTimesheet|Mail|UserRequest|DefineRoles|DefineDepartments|OrgHierarchy|UserPermissions|ManageUser|ManageProject|ManageTask|ManageClient|LeaveSettings|RolePermissions|DefineLeaves', 0, 'sa', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2015-02-23 11:19:49'),
	(3, 'Mr', '1', 'Project', '', 'Manager', 'pm@ggg.com', 'Male', 'OnRolls', '2015-02-23 00:00:00', 1000000, 480.77, 2, 'Project Manager', 20, NULL, 2, NULL, NULL, '', 0, 'qwe123', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2015-02-23 11:30:21'),
	(4, 'Mr', '2', 'Group', '', 'Lead', 'gl@ggg.com', 'Male', 'OnRolls', '2015-02-23 00:00:00', 500000, 240.38, 2, 'Group Lead', 19, NULL, 3, NULL, NULL, '', 0, 'ys6jah', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2015-03-07 12:55:59'),
	(5, 'Mr', '3', 'Senior', '', 'Software', 'sse@ggg.com', 'Male', 'OnRolls', '2015-02-23 00:00:00', 250000, 120.19, 2, 'Senior Software Engineer', 18, NULL, 4, NULL, NULL, '', 1, 'm8ct27', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2015-02-23 11:31:46'),
	(6, 'Mr', '11', 'Jagbir', '', 'Singh', 'jagbir.paul@gmail.com@ggg.com', 'Male', 'OnRolls', '2015-03-10 00:00:00', 2121, 1.02, 2, 'Software Engineer', 17, NULL, 2, '2015-03-04 00:00:00', NULL, '', 1, 'htopv2', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2015-03-18 13:34:44'),
	(7, 'Mr', '11', 'Jagbir', '11', 'Singh', 'jagbir.paul@gmail.com1@ggg.com', 'Male', 'OnRolls', '2015-03-02 00:00:00', 33, 0.02, 2, 'Software Engineer', 17, NULL, 2, '2015-03-14 00:00:00', NULL, '', 1, 'taap9f', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2015-03-18 13:41:39'),
	(10, 'Mr', '99', 'Jagbir', '', 'Singh', 'jagbir.paul@gma9l.com@ggg.com', 'Male', 'OnRolls', '2015-03-03 00:00:00', 9, 0, 2, 'Software Engineer', 17, NULL, 2, NULL, NULL, '', 1, 'd1s8vs', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2015-03-18 14:19:25'),
	(18, 'Mr', '99', 'Jagbir', '', 'Singh', 'jagbir1.paul@gma9l.com@ggg.com', 'Male', 'OnRolls', '2015-03-03 00:00:00', 9, 0, 2, 'Software Engineer', 17, NULL, 2, NULL, NULL, '', 1, 'v355s3', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2015-03-18 14:44:42'),
	(24, 'Mr', '11', 'Jagbir', '', 'Singh', 'jag33bir.paul@gmail.com@ggg.com', 'Male', 'OnRolls', '2015-03-03 00:00:00', 12, 0.01, 2, 'Software Engineer', 17, NULL, 2, '2015-03-06 00:00:00', NULL, '', 1, 'lkzbtb', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2015-03-20 20:12:37'),
	(29, 'Mr', '44', 'Jagbir', '', 'Singh', 'jagbir44.paul@gmail.com@ggg.com', 'Male', 'OnRolls', '2015-03-11 00:00:00', 44, 0.02, 2, 'Software Engineer', 17, NULL, 2, NULL, NULL, '', 1, 'm1s4mr', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2015-03-22 10:45:07');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.user_flexi
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
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.user_flexi: ~23 rows (approximately)
DELETE FROM `user_flexi`;
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
	(29, 5, '[{"n":"giraffe-306478_640.png","s":86773,"ct":"image/png"}]', 41);
/*!40000 ALTER TABLE `user_flexi` ENABLE KEYS */;


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
DELETE FROM `user_project`;
/*!40000 ALTER TABLE `user_project` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_project` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
