-- --------------------------------------------------------
-- Host:                         ec2-50-19-213-178.compute-1.amazonaws.com
-- Server version:               5.5.32-log - Source distribution
-- Server OS:                    Linux
-- HeidiSQL version:             6.0.0.4004
-- Date/time:                    2014-04-11 20:07:36
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;

-- Dumping structure for table time-mgmt.apply_leave
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table time-mgmt.apply_leave: ~0 rows (approximately)
/*!40000 ALTER TABLE `apply_leave` DISABLE KEYS */;
/*!40000 ALTER TABLE `apply_leave` ENABLE KEYS */;


-- Dumping structure for table time-mgmt.client
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table time-mgmt.client: ~1 rows (approximately)
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` (`id`, `client_name`, `phone_no`, `email`, `fax`, `address`, `street`, `city`, `country`, `pin`, `contact_name`, `contact_phone`, `contact_email`, `company_id`) VALUES
	(1, 'James', '2121122121', 'jagbir.paul@gmil.com', 21212, 'St Patrick', 'BT', 'Pune', 'India', '411001', 'Jagbir', '1212121212', 'jagbir.paul@gmail.com', 1);
/*!40000 ALTER TABLE `client` ENABLE KEYS */;


-- Dumping structure for table time-mgmt.company
CREATE TABLE IF NOT EXISTS `company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(255) DEFAULT NULL,
  `company_code` varchar(255) DEFAULT NULL,
  `company_email` varchar(255) DEFAULT NULL,
  `company_phone` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `company_status` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table time-mgmt.company: ~2 rows (approximately)
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` (`id`, `company_name`, `company_code`, `company_email`, `company_phone`, `address`, `company_status`) VALUES
	(1, 'Google', 'gmail.com', 'paul.jagbir@gmail.com', '1234512345', 'Some Nice Address', 'Approved'),
	(2, 'yahoo', 'yahoo.com', 'jagbir_paul@yahoo.com', '1234512345', 'some nice company', 'Approved');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;


-- Dumping structure for table time-mgmt.delegate
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table time-mgmt.delegate: ~1 rows (approximately)
/*!40000 ALTER TABLE `delegate` DISABLE KEYS */;
INSERT INTO `delegate` (`id`, `from_date`, `to_date`, `delegator_id`, `delegated_to_id`) VALUES
	(1, '2014-04-11 00:00:00', '2014-04-18 00:00:00', 7, 8);
/*!40000 ALTER TABLE `delegate` ENABLE KEYS */;


-- Dumping structure for table time-mgmt.feedback
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table time-mgmt.feedback: ~0 rows (approximately)
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;


-- Dumping structure for table time-mgmt.leave_balance
CREATE TABLE IF NOT EXISTS `leave_balance` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `employee_id` bigint(20) DEFAULT NULL,
  `leave_level_id` bigint(20) DEFAULT NULL,
  `balance` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_leave_balance_employee_8` (`employee_id`),
  KEY `ix_leave_balance_leaveLevel_9` (`leave_level_id`),
  CONSTRAINT `fk_leave_balance_leaveLevel_9` FOREIGN KEY (`leave_level_id`) REFERENCES `leave_level` (`id`),
  CONSTRAINT `fk_leave_balance_employee_8` FOREIGN KEY (`employee_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- Dumping data for table time-mgmt.leave_balance: ~0 rows (approximately)
/*!40000 ALTER TABLE `leave_balance` DISABLE KEYS */;
INSERT INTO `leave_balance` (`id`, `employee_id`, `leave_level_id`, `balance`) VALUES
	(1, 2, 4, NULL),
	(2, 3, 4, NULL),
	(3, 5, 4, NULL),
	(4, 7, 4, NULL),
	(5, 8, 4, NULL),
	(6, 2, 5, NULL),
	(7, 3, 5, NULL),
	(8, 5, 5, NULL),
	(9, 7, 5, NULL),
	(10, 8, 5, NULL),
	(11, 2, 6, NULL),
	(12, 3, 6, NULL),
	(13, 5, 6, NULL),
	(14, 7, 6, NULL),
	(15, 8, 6, NULL),
	(16, 2, 7, NULL),
	(17, 3, 7, NULL),
	(18, 5, 7, NULL),
	(19, 7, 7, NULL),
	(20, 8, 7, NULL),
	(21, 10, 4, NULL),
	(22, 10, 5, NULL),
	(23, 10, 6, NULL),
	(24, 10, 7, NULL);
/*!40000 ALTER TABLE `leave_balance` ENABLE KEYS */;


-- Dumping structure for table time-mgmt.leave_level
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
  CONSTRAINT `fk_leave_level_roleLeave_11` FOREIGN KEY (`role_leave_id`) REFERENCES `role_leave` (`id`),
  CONSTRAINT `fk_leave_level_leaveX_10` FOREIGN KEY (`leave_x_id`) REFERENCES `leave_x` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- Dumping data for table time-mgmt.leave_level: ~0 rows (approximately)
/*!40000 ALTER TABLE `leave_level` DISABLE KEYS */;
INSERT INTO `leave_level` (`id`, `leave_type`, `carry_forward`, `leave_x_id`, `role_leave_id`, `last_update`) VALUES
	(4, 'Sick Leave', 'NO', 2, NULL, '2014-04-11 19:18:59'),
	(5, 'Casual Leave', 'YES', 2, NULL, '2014-04-11 19:19:00'),
	(6, 'Maternity Leave', 'NO', 2, NULL, '2014-04-11 19:19:00'),
	(7, 'Condolence Leave', 'NO', 2, NULL, '2014-04-11 19:19:01');
/*!40000 ALTER TABLE `leave_level` ENABLE KEYS */;


-- Dumping structure for table time-mgmt.leave_x
CREATE TABLE IF NOT EXISTS `leave_x` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_leave_x_company_12` (`company_id`),
  CONSTRAINT `fk_leave_x_company_12` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table time-mgmt.leave_x: ~0 rows (approximately)
/*!40000 ALTER TABLE `leave_x` DISABLE KEYS */;
INSERT INTO `leave_x` (`id`, `company_id`) VALUES
	(2, 1);
/*!40000 ALTER TABLE `leave_x` ENABLE KEYS */;


-- Dumping structure for table time-mgmt.mail_setting
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumping data for table time-mgmt.mail_setting: ~3 rows (approximately)
/*!40000 ALTER TABLE `mail_setting` DISABLE KEYS */;
INSERT INTO `mail_setting` (`id`, `host_name`, `port_number`, `ssl_port`, `tls_port`, `user_name`, `password`, `company_object_id`, `last_update`) VALUES
	(1, 'smtp.gmail.com', '587', 0, 0, 'mntlogin@gmail.com', 'mntabcd123', NULL, '2014-04-09 10:49:31'),
	(2, 'smtp.gmail.com', '587', 0, 0, 'mntlogin@gmail.com', 'mntabcd123', 1, '2014-04-09 16:22:01'),
	(3, 'smtp.gmail.com', '587', 0, 0, 'mntlogin@gmail.com', 'mntabcd123', 2, '2014-04-09 16:56:36');
/*!40000 ALTER TABLE `mail_setting` ENABLE KEYS */;


-- Dumping structure for table time-mgmt.notification
CREATE TABLE IF NOT EXISTS `notification` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `setting_as_json` longtext,
  `company_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_notification_company_14` (`company_id`),
  CONSTRAINT `fk_notification_company_14` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table time-mgmt.notification: ~2 rows (approximately)
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` (`id`, `setting_as_json`, `company_id`) VALUES
	(1, NULL, 1),
	(2, NULL, 2);
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;


-- Dumping structure for table time-mgmt.project
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
  CONSTRAINT `fk_project_companyObj_17` FOREIGN KEY (`company_obj_id`) REFERENCES `company` (`id`),
  CONSTRAINT `fk_project_clientName_15` FOREIGN KEY (`client_name_id`) REFERENCES `client` (`id`),
  CONSTRAINT `fk_project_projectManager_16` FOREIGN KEY (`project_manager_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table time-mgmt.project: ~1 rows (approximately)
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` (`id`, `client_name_id`, `project_name`, `project_code`, `project_description`, `start_date`, `end_date`, `budget`, `currency`, `efforts`, `project_manager_id`, `company_obj_id`) VALUES
	(1, 1, 'Time01', 'time01', 'Time Minder', '2014-04-01 00:00:00', '2014-04-29 00:00:00', 1212, 'INR', 12, 5, 1);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;


-- Dumping structure for table time-mgmt.project_task
CREATE TABLE IF NOT EXISTS `project_task` (
  `project_id` bigint(20) NOT NULL,
  `task_id` bigint(20) NOT NULL,
  PRIMARY KEY (`project_id`,`task_id`),
  KEY `fk_project_task_task_02` (`task_id`),
  CONSTRAINT `fk_project_task_task_02` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`),
  CONSTRAINT `fk_project_task_project_01` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table time-mgmt.project_task: ~0 rows (approximately)
/*!40000 ALTER TABLE `project_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_task` ENABLE KEYS */;


-- Dumping structure for table time-mgmt.project_user
CREATE TABLE IF NOT EXISTS `project_user` (
  `project_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`project_id`,`user_id`),
  KEY `fk_project_user_user_02` (`user_id`),
  CONSTRAINT `fk_project_user_user_02` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_project_user_project_01` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table time-mgmt.project_user: ~2 rows (approximately)
/*!40000 ALTER TABLE `project_user` DISABLE KEYS */;
INSERT INTO `project_user` (`project_id`, `user_id`) VALUES
	(1, 3),
	(1, 5);
/*!40000 ALTER TABLE `project_user` ENABLE KEYS */;


-- Dumping structure for table time-mgmt.role_leave
CREATE TABLE IF NOT EXISTS `role_leave` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) DEFAULT NULL,
  `role_level_id` bigint(20) DEFAULT NULL,
  `leave_level_id` bigint(20) DEFAULT NULL,
  `total_leave` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_role_leave_company_18` (`company_id`),
  KEY `ix_role_leave_roleLevel_19` (`role_level_id`),
  KEY `ix_role_leave_leaveLevel_20` (`leave_level_id`),
  CONSTRAINT `fk_role_leave_leaveLevel_20` FOREIGN KEY (`leave_level_id`) REFERENCES `leave_level` (`id`),
  CONSTRAINT `fk_role_leave_company_18` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `fk_role_leave_roleLevel_19` FOREIGN KEY (`role_level_id`) REFERENCES `role_level` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

-- Dumping data for table time-mgmt.role_leave: ~0 rows (approximately)
/*!40000 ALTER TABLE `role_leave` DISABLE KEYS */;
INSERT INTO `role_leave` (`id`, `company_id`, `role_level_id`, `leave_level_id`, `total_leave`) VALUES
	(24, 1, 1, 4, 10),
	(25, 1, 1, 5, 10),
	(26, 1, 2, 4, 10),
	(27, 1, 2, 5, 10),
	(28, 1, 3, 4, 10),
	(29, 1, 3, 5, 11),
	(30, 1, 4, 4, 10),
	(31, 1, 4, 5, 10),
	(32, 1, 7, 4, 10),
	(33, 1, 7, 5, 15),
	(34, 1, 1, 6, 5),
	(35, 1, 2, 6, 5),
	(36, 1, 3, 6, 5),
	(37, 1, 4, 6, 5),
	(38, 1, 7, 6, 5),
	(39, 1, 8, 4, 10),
	(40, 1, 8, 5, 18),
	(41, 1, 8, 6, 5),
	(45, 1, 10, 4, 13),
	(46, 1, 10, 5, 3),
	(47, 1, 10, 6, 5),
	(48, 1, 1, 7, 3),
	(49, 1, 2, 7, 3),
	(50, 1, 3, 7, 3),
	(51, 1, 4, 7, 3),
	(52, 1, 7, 7, 3),
	(53, 1, 8, 7, 3),
	(54, 1, 10, 7, 3);
/*!40000 ALTER TABLE `role_leave` ENABLE KEYS */;


-- Dumping structure for table time-mgmt.role_level
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- Dumping data for table time-mgmt.role_level: ~6 rows (approximately)
/*!40000 ALTER TABLE `role_level` DISABLE KEYS */;
INSERT INTO `role_level` (`id`, `role_x_id`, `role_level`, `role_name`, `reporting_to`, `final_approval`, `permissions`, `last_update`) VALUES
	(1, 1, 0, 'SME', 'GL', 'GL', '', '2014-04-11 14:25:01'),
	(2, 1, 1, 'SSME', 'GL', 'PM', '', '2014-04-11 14:25:01'),
	(3, 1, 2, 'GL', 'PM', 'PM', '', '2014-04-11 14:25:02'),
	(4, 1, 3, 'PM', 'PM', 'PM', '', '2014-04-11 14:25:02'),
	(5, 2, 0, 'SME', 'SME', 'SME', '', '2014-04-09 17:08:07'),
	(6, 2, 1, 'SSME', 'SSME', 'SSME', '', '2014-04-09 17:08:07'),
	(7, 1, 4, 'Director', 'SME', 'SME', '', '2014-04-11 14:25:03'),
	(8, 1, 5, 'VP', 'SME', 'SME', '', '2014-04-11 14:25:04'),
	(10, 1, 6, 'Sr.VP', 'SME', 'SME', '', '2014-04-11 14:25:04');
/*!40000 ALTER TABLE `role_level` ENABLE KEYS */;


-- Dumping structure for table time-mgmt.role_x
CREATE TABLE IF NOT EXISTS `role_x` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_role_x_company_22` (`company_id`),
  CONSTRAINT `fk_role_x_company_22` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table time-mgmt.role_x: ~2 rows (approximately)
/*!40000 ALTER TABLE `role_x` DISABLE KEYS */;
INSERT INTO `role_x` (`id`, `company_id`) VALUES
	(1, 1),
	(2, 2);
/*!40000 ALTER TABLE `role_x` ENABLE KEYS */;


-- Dumping structure for table time-mgmt.task
CREATE TABLE IF NOT EXISTS `task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(255) DEFAULT NULL,
  `task_code` varchar(255) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `effort` int(11) DEFAULT NULL,
  `is_billable` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table time-mgmt.task: ~2 rows (approximately)
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` (`id`, `task_name`, `task_code`, `start_date`, `end_date`, `effort`, `is_billable`) VALUES
	(1, 'TaskT01', 'TaskT01', '2014-04-01 00:00:00', '2014-04-09 00:00:00', 30, 'Yes'),
	(2, 'TaskT02', 'TaskT02', '2014-04-10 00:00:00', '2014-04-16 00:00:00', 10, 'Yes');
/*!40000 ALTER TABLE `task` ENABLE KEYS */;


-- Dumping structure for table time-mgmt.task_project
CREATE TABLE IF NOT EXISTS `task_project` (
  `task_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  PRIMARY KEY (`task_id`,`project_id`),
  KEY `fk_task_project_project_02` (`project_id`),
  CONSTRAINT `fk_task_project_project_02` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  CONSTRAINT `fk_task_project_task_01` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table time-mgmt.task_project: ~2 rows (approximately)
/*!40000 ALTER TABLE `task_project` DISABLE KEYS */;
INSERT INTO `task_project` (`task_id`, `project_id`) VALUES
	(1, 1),
	(2, 1);
/*!40000 ALTER TABLE `task_project` ENABLE KEYS */;


-- Dumping structure for table time-mgmt.timesheet
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table time-mgmt.timesheet: ~0 rows (approximately)
/*!40000 ALTER TABLE `timesheet` DISABLE KEYS */;
/*!40000 ALTER TABLE `timesheet` ENABLE KEYS */;


-- Dumping structure for table time-mgmt.timesheet_row
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table time-mgmt.timesheet_row: ~0 rows (approximately)
/*!40000 ALTER TABLE `timesheet_row` DISABLE KEYS */;
/*!40000 ALTER TABLE `timesheet_row` ENABLE KEYS */;


-- Dumping structure for table time-mgmt.user
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
  CONSTRAINT `fk_user_hrmanager_30` FOREIGN KEY (`hrmanager_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_user_companyobject_26` FOREIGN KEY (`companyobject_id`) REFERENCES `company` (`id`),
  CONSTRAINT `fk_user_level_28` FOREIGN KEY (`level_id`) REFERENCES `leave_level` (`id`),
  CONSTRAINT `fk_user_manager_29` FOREIGN KEY (`manager_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_user_role_27` FOREIGN KEY (`role_id`) REFERENCES `role_level` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- Dumping data for table time-mgmt.user: ~8 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `salutation`, `employee_id`, `first_name`, `middle_name`, `last_name`, `email`, `gender`, `status`, `hire_date`, `annual_income`, `hourlyrate`, `companyobject_id`, `designation`, `role_id`, `level_id`, `manager_id`, `release_date`, `hrmanager_id`, `permissions`, `temp_password`, `password`, `reset_flag`, `failed_login_attempt`, `create_date`, `modified_date`, `password_reset`, `user_status`, `process_instance_id`, `last_update`) VALUES
	(1, 'Mr', '1', 'Jagbir', 'Singh', 'Paul', 'mindnervestech@gmail.com', NULL, NULL, NULL, NULL, NULL, NULL, 'SuperAdmin', NULL, NULL, NULL, NULL, NULL, 'CompanyRequest|Mail', 0, 'a', 0, NULL, NULL, NULL, 0, 'Approved', NULL, '2013-07-08 13:46:15'),
	(2, NULL, NULL, 'gmail.com Admin', NULL, NULL, 'paul.jagbir@gmail.com', NULL, NULL, NULL, NULL, NULL, 1, 'Admin', NULL, NULL, NULL, NULL, NULL, 'Delegate|FeedBackCreate|FeedBackView|TeamRate|ProjectReport|Notification|ApplyLeave|Leaves|Timesheet|CreateTimesheet|SearchTimesheet|Mail|UserRequest|DefineRoles|OrgHierarchy|UserPermissions|ManageUser|ManageProject|ManageTask|ManageClient|LeaveSettings|RolePermissions|DefineLeaves', 0, 'abcd1234', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2014-04-10 18:36:39'),
	(3, 'Mr', '1', 'Jagbir', 'Singh', 'Inner', 'jagbir.paul@gmail.com', 'Male', 'OnRolls', '2014-04-09 00:00:00', 1234567, 593.54, 1, 'SSME', 2, NULL, 7, NULL, NULL, 'ApplyLeave|SearchTimesheet|CreateTimesheet|', 0, 'abcd1234', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2014-04-10 18:34:07'),
	(4, NULL, NULL, 'yahoo.com Admin', NULL, NULL, 'jagbir_paul@yahoo.com', NULL, NULL, NULL, NULL, NULL, 2, 'Admin', NULL, NULL, NULL, NULL, NULL, 'Delegate|FeedBackCreate|FeedBackView|TeamRate|ProjectReport|Notification|ApplyLeave|Leaves|Timesheet|CreateTimesheet|SearchTimesheet|Mail|UserRequest|DefineRoles|OrgHierarchy|UserPermissions|ManageUser|ManageProject|ManageTask|ManageClient|LeaveSettings|RolePermissions', 0, 'abcd123', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2014-04-09 16:57:37'),
	(5, 'Mr', '2', 'Amit', '', 'Goyal', 'creativeapps.guru@gmail.com', 'Male', 'OnRolls', '2014-04-09 00:00:00', 121212, 58.27, 1, 'SME', 1, NULL, 7, NULL, NULL, 'CreateTimesheet||', 0, 'abcd1234', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2014-04-10 17:44:35'),
	(6, 'Mr', '1', 'Jagbir', 'Yahoo', 'Singh', 'jagbir.singh@yahoo.com', 'Male', 'OnRolls', '2014-04-09 00:00:00', 121212, 58.27, 2, 'SME', 5, NULL, 4, NULL, NULL, '', 1, 'veo7th', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2014-04-09 17:09:32'),
	(7, 'Mr', '3', 'Group', '', 'Lead', 'grouplead@gmail.com', 'Male', 'OnRolls', '2014-04-01 00:00:00', 1000000, 480.77, 1, 'GL', 3, NULL, 8, NULL, NULL, 'ApplyLeave|SearchTimesheet|LeaveBucket|FeedBackCreate|Delegate|FeedBackView|MyBucket|CreateTimesheet||', 0, 'abcd1234', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2014-04-10 18:59:03'),
	(8, 'Mr', '4', 'project', '', 'Manager', 'projectmanager@gmail.com', 'Male', 'OnRolls', '2014-04-01 00:00:00', 7654321, 3679.96, 1, 'PM', 4, NULL, 2, NULL, NULL, 'ApplyLeave|CreateTimesheet|SearchTimesheet|Delegate|FeedBackCreate|FeedBackView|MyBucket|LeaveBucket|TeamRate|ProjectReport|', 0, 'abcd1234', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2014-04-10 18:38:29'),
	(10, 'Mr', '5', 'Dhrej', 'Bankar', 'Director', 'dhairyashil.bankar@gmail.com', 'Male', 'OnRolls', '2014-04-11 00:00:00', 12121212, 5827.51, 1, 'Director', 7, NULL, 2, NULL, NULL, '', 1, 'dmcjs1', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2014-04-11 19:45:28');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;


-- Dumping structure for table time-mgmt.user_project
CREATE TABLE IF NOT EXISTS `user_project` (
  `user_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`project_id`),
  KEY `fk_user_project_project_02` (`project_id`),
  CONSTRAINT `fk_user_project_project_02` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  CONSTRAINT `fk_user_project_user_01` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table time-mgmt.user_project: ~0 rows (approximately)
/*!40000 ALTER TABLE `user_project` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_project` ENABLE KEYS */;
/*!40014 SET FOREIGN_KEY_CHECKS=1 */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
