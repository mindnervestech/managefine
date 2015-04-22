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
  CONSTRAINT `fk_case_data_company_5` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `fk_case_data_assignto_4` FOREIGN KEY (`assignto_id`) REFERENCES `user` (`id`),
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

-- Dumping data for table timemgmt1.company: ~0 rows (approximately)
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
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.department: ~0 rows (approximately)
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.flexi_attribute: ~0 rows (approximately)
/*!40000 ALTER TABLE `flexi_attribute` DISABLE KEYS */;
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
  CONSTRAINT `fk_leave_balance_leaveLevel_15` FOREIGN KEY (`leave_level_id`) REFERENCES `leave_level` (`id`),
  CONSTRAINT `fk_leave_balance_employee_14` FOREIGN KEY (`employee_id`) REFERENCES `user` (`id`)
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
  CONSTRAINT `fk_leave_level_roleLeave_17` FOREIGN KEY (`role_leave_id`) REFERENCES `role_leave` (`id`),
  CONSTRAINT `fk_leave_level_leaveX_16` FOREIGN KEY (`leave_x_id`) REFERENCES `leave_x` (`id`)
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

-- Dumping data for table timemgmt1.mail_setting: ~0 rows (approximately)
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

-- Dumping data for table timemgmt1.notification: ~0 rows (approximately)
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

-- Dumping data for table timemgmt1.organization: ~0 rows (approximately)
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
  CONSTRAINT `fk_project_companyObj_24` FOREIGN KEY (`company_obj_id`) REFERENCES `company` (`id`),
  CONSTRAINT `fk_project_clientName_22` FOREIGN KEY (`client_name_id`) REFERENCES `client` (`id`),
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
  `project_types` varchar(255) DEFAULT NULL,
  `project_description` varchar(255) DEFAULT NULL,
  `project_color` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `project_id_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_projectclassnode_projectId_27` (`project_id_id`),
  CONSTRAINT `fk_projectclassnode_projectId_27` FOREIGN KEY (`project_id_id`) REFERENCES `projectclass` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.projectclassnode: ~0 rows (approximately)
/*!40000 ALTER TABLE `projectclassnode` DISABLE KEYS */;
INSERT INTO `projectclassnode` (`id`, `project_types`, `project_description`, `project_color`, `parent_id`, `level`, `project_id_id`) VALUES
	(1, 'Software', 'This manages Software life cycle.', NULL, NULL, 0, 1),
	(2, 'Development', 'This manages Software life cycle.', '#7df17a', 1, 1, 1),
	(3, 'Testing', 'Define Testing .', '#7df17a', 1, 1, 1),
	(4, 'Requirement Gathering', 'This is requirement gathering Phase', '#7df17a', 1, 1, 1),
	(5, 'Coding', 'Coding Task', '#7df17a', 2, 2, 1),
	(6, 'Unit Testing', 'UT Task', '#7df17a', 2, 2, 1),
	(7, 'Code Review', 'This task is for experience coder', '#7df17a', 2, 2, 1),
	(8, 'ST', 'System testing', '#7df17a', 3, 2, 1),
	(9, 'UAT', 'User Acceptance  testing', '#7df17a', 3, 2, 1),
	(10, 'HLD', 'High Level Design', '#7df17a', 4, 2, 1),
	(11, 'Proof Of Concept', 'Proof Of Concept', '#7df17a', 4, 2, 1);
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

-- Dumping data for table timemgmt1.projectclassnodeattribut: ~0 rows (approximately)
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
  `project_name` varchar(255) DEFAULT NULL,
  `project_description` varchar(255) DEFAULT NULL,
  `client_name` varchar(255) DEFAULT NULL,
  `start_date` varchar(255) DEFAULT NULL,
  `end_date` varchar(255) DEFAULT NULL,
  `client_id` bigint(20) DEFAULT NULL,
  `projectid` bigint(20) DEFAULT NULL,
  `userid_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_projectinstance_userid_29` (`userid_id`),
  CONSTRAINT `fk_projectinstance_userid_29` FOREIGN KEY (`userid_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.projectinstance: ~0 rows (approximately)
/*!40000 ALTER TABLE `projectinstance` DISABLE KEYS */;
/*!40000 ALTER TABLE `projectinstance` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.projectinstancenode
CREATE TABLE IF NOT EXISTS `projectinstancenode` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `projectclassnode_id` bigint(20) DEFAULT NULL,
  `projecttypeid` bigint(20) DEFAULT NULL,
  `projectinstanceid` bigint(20) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `task_compilation` bigint(20) DEFAULT NULL,
  `weightage` int(11) DEFAULT NULL,
  `project_manager` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `supplier_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_projectinstancenode_Projec_30` (`projectclassnode_id`),
  KEY `ix_projectinstancenode_user_31` (`user_id`),
  KEY `ix_projectinstancenode_suppli_32` (`supplier_id`),
  CONSTRAINT `fk_projectinstancenode_suppli_32` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`),
  CONSTRAINT `fk_projectinstancenode_Projec_30` FOREIGN KEY (`projectclassnode_id`) REFERENCES `projectclassnode` (`id`),
  CONSTRAINT `fk_projectinstancenode_user_31` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.projectinstancenode: ~0 rows (approximately)
/*!40000 ALTER TABLE `projectinstancenode` DISABLE KEYS */;
/*!40000 ALTER TABLE `projectinstancenode` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.projectinstance_supplier
CREATE TABLE IF NOT EXISTS `projectinstance_supplier` (
  `projectinstance_id` bigint(20) NOT NULL,
  `supplier_id` bigint(20) NOT NULL,
  PRIMARY KEY (`projectinstance_id`,`supplier_id`),
  KEY `fk_projectinstance_supplier_s_02` (`supplier_id`),
  CONSTRAINT `fk_projectinstance_supplier_s_02` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`),
  CONSTRAINT `fk_projectinstance_supplier_p_01` FOREIGN KEY (`projectinstance_id`) REFERENCES `projectinstance` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.projectinstance_supplier: ~0 rows (approximately)
/*!40000 ALTER TABLE `projectinstance_supplier` DISABLE KEYS */;
/*!40000 ALTER TABLE `projectinstance_supplier` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.projectinstance_user
CREATE TABLE IF NOT EXISTS `projectinstance_user` (
  `projectinstance_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`projectinstance_id`,`user_id`),
  KEY `fk_projectinstance_user_user_02` (`user_id`),
  CONSTRAINT `fk_projectinstance_user_user_02` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_projectinstance_user_proje_01` FOREIGN KEY (`projectinstance_id`) REFERENCES `projectinstance` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.projectinstance_user: ~0 rows (approximately)
/*!40000 ALTER TABLE `projectinstance_user` DISABLE KEYS */;
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
  CONSTRAINT `fk_project_task_task_02` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`),
  CONSTRAINT `fk_project_task_project_01` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
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
  CONSTRAINT `fk_project_user_user_02` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_project_user_project_01` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.project_user: ~0 rows (approximately)
/*!40000 ALTER TABLE `project_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_user` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.role
CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) DEFAULT NULL,
  `role_description` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `role_x_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_role_roleX_33` (`role_x_id`),
  CONSTRAINT `fk_role_roleX_33` FOREIGN KEY (`role_x_id`) REFERENCES `role_x` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.role: ~0 rows (approximately)
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`id`, `role_name`, `role_description`, `parent_id`, `role_x_id`) VALUES
	(1, 'Head', 'Owner', NULL, NULL),
	(2, 'CEO', 'Chief Executive Officer', 1, NULL),
	(3, 'CTO', 'Chief technical Officer', 1, NULL),
	(4, 'CFO', 'Chief Financial Officer', 1, NULL),
	(5, 'VP (Managment)', 'Vice President - Management', 2, NULL),
	(6, 'VP (Development)', 'Vice President - Development', 3, NULL),
	(7, 'Director', 'Director', 6, NULL),
	(8, 'Project Manager', 'Project Manager', 7, NULL),
	(9, 'Sr Development Expert', 'Sr Development Expert', 7, NULL),
	(10, 'Group Lead', 'Group Lead Development', 8, NULL),
	(11, 'Sr Subject Mater Expert', 'Sr Subject Mater Expert', 10, NULL),
	(12, 'Subject Mater Expert', 'Subject Mater Expert', 10, NULL),
	(13, 'Development Expert', 'Development Expert', 8, NULL);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.role_leave
CREATE TABLE IF NOT EXISTS `role_leave` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) DEFAULT NULL,
  `role_level_id` bigint(20) DEFAULT NULL,
  `leave_level_id` bigint(20) DEFAULT NULL,
  `total_leave` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_role_leave_company_34` (`company_id`),
  KEY `ix_role_leave_roleLevel_35` (`role_level_id`),
  KEY `ix_role_leave_leaveLevel_36` (`leave_level_id`),
  CONSTRAINT `fk_role_leave_leaveLevel_36` FOREIGN KEY (`leave_level_id`) REFERENCES `leave_level` (`id`),
  CONSTRAINT `fk_role_leave_company_34` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `fk_role_leave_roleLevel_35` FOREIGN KEY (`role_level_id`) REFERENCES `role` (`id`)
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
  `last_update` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_role_level_roleX_37` (`role_x_id`),
  CONSTRAINT `fk_role_level_roleX_37` FOREIGN KEY (`role_x_id`) REFERENCES `role_x` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.role_level: ~0 rows (approximately)
/*!40000 ALTER TABLE `role_level` DISABLE KEYS */;
/*!40000 ALTER TABLE `role_level` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.role_x
CREATE TABLE IF NOT EXISTS `role_x` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_role_x_company_38` (`company_id`),
  CONSTRAINT `fk_role_x_company_38` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.role_x: ~0 rows (approximately)
/*!40000 ALTER TABLE `role_x` DISABLE KEYS */;
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

-- Dumping data for table timemgmt1.supplier: ~0 rows (approximately)
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.timesheet: ~0 rows (approximately)
/*!40000 ALTER TABLE `timesheet` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.timesheet_actual: ~0 rows (approximately)
/*!40000 ALTER TABLE `timesheet_actual` DISABLE KEYS */;
/*!40000 ALTER TABLE `timesheet_actual` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.timesheet_days
CREATE TABLE IF NOT EXISTS `timesheet_days` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `day` varchar(255) DEFAULT NULL,
  `timesheet_date` datetime DEFAULT NULL,
  `time_from` varchar(255) DEFAULT NULL,
  `time_to` varchar(255) DEFAULT NULL,
  `work_minutes` int(11) DEFAULT NULL,
  `timesheet_row_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_timesheet_days_timesheetRo_46` (`timesheet_row_id`),
  CONSTRAINT `fk_timesheet_days_timesheetRo_46` FOREIGN KEY (`timesheet_row_id`) REFERENCES `timesheet_row` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.timesheet_days: ~0 rows (approximately)
/*!40000 ALTER TABLE `timesheet_days` DISABLE KEYS */;
/*!40000 ALTER TABLE `timesheet_days` ENABLE KEYS */;


-- Dumping structure for table timemgmt1.timesheet_days_actual
CREATE TABLE IF NOT EXISTS `timesheet_days_actual` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `day` varchar(255) DEFAULT NULL,
  `timesheet_date` datetime DEFAULT NULL,
  `time_from` varchar(255) DEFAULT NULL,
  `time_to` varchar(255) DEFAULT NULL,
  `work_minutes` int(11) DEFAULT NULL,
  `timesheet_row_actual_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_timesheet_days_actual_time_47` (`timesheet_row_actual_id`),
  CONSTRAINT `fk_timesheet_days_actual_time_47` FOREIGN KEY (`timesheet_row_actual_id`) REFERENCES `timesheet_row_actual` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.timesheet_days_actual: ~0 rows (approximately)
/*!40000 ALTER TABLE `timesheet_days_actual` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.timesheet_row: ~0 rows (approximately)
/*!40000 ALTER TABLE `timesheet_row` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.timesheet_row_actual: ~0 rows (approximately)
/*!40000 ALTER TABLE `timesheet_row_actual` DISABLE KEYS */;
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_user_email` (`email`),
  KEY `ix_user_companyobject_50` (`companyobject_id`),
  KEY `ix_user_role_51` (`role_id`),
  KEY `ix_user_level_52` (`level_id`),
  KEY `ix_user_manager_53` (`manager_id`),
  KEY `ix_user_hrmanager_54` (`hrmanager_id`),
  CONSTRAINT `fk_user_hrmanager_54` FOREIGN KEY (`hrmanager_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_user_companyobject_50` FOREIGN KEY (`companyobject_id`) REFERENCES `company` (`id`),
  CONSTRAINT `fk_user_level_52` FOREIGN KEY (`level_id`) REFERENCES `leave_level` (`id`),
  CONSTRAINT `fk_user_manager_53` FOREIGN KEY (`manager_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_user_role_51` FOREIGN KEY (`role_id`) REFERENCES `role_level` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.user: ~0 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `salutation`, `employee_id`, `first_name`, `middle_name`, `last_name`, `email`, `gender`, `status`, `hire_date`, `annual_income`, `hourlyrate`, `companyobject_id`, `designation`, `department`, `role_id`, `level_id`, `manager_id`, `release_date`, `hrmanager_id`, `permissions`, `temp_password`, `password`, `reset_flag`, `failed_login_attempt`, `create_date`, `modified_date`, `password_reset`, `user_status`, `process_instance_id`, `last_update`) VALUES
	(1, 'Mr', '1', 'jagbir', 'singh', 'paul', 'mindnervestech@gmail.com', NULL, 'OnRolls', NULL, NULL, NULL, 1, 'SuperAdmin', NULL, NULL, NULL, NULL, NULL, NULL, 'SearchTimesheet|CreateTimesheet|Delegate|Leaves|ApplyLeave|FeedBackCreate|FeedBackView|UserPermissions|LeaveBucket|MyBucket|TeamRate|ProjectReport|ManageUser|ManageProject|ManageTask|ManageClient|Notification|DefineRoles|OrgHierarchy|Mail|RolePermissions', 0, 'sa', NULL, NULL, NULL, NULL, 0, 'Approved', NULL, '2015-02-19 15:32:01'),
	(2, NULL, NULL, 'mindnerves.com Admin', NULL, NULL, 'amit.goyal@mindnerves.com', NULL, NULL, NULL, NULL, NULL, 1, 'Admin', NULL, NULL, NULL, NULL, NULL, NULL, 'Delegate|FeedBackCreate|FeedBackView|TeamRate|ProjectReport|Notification|ApplyLeave|Leaves|Timesheet|CreateTimesheet|SearchTimesheet|Mail|UserRequest|DefineRoles|OrgHierarchy|DefineProjects|UserPermissions|ManageUser|ManageProject|ManageTask|ManageClient|LeaveSettings|RolePermissions|DefineLeaves|DefineDepartments|ManageSupplier', 0, 'qwer123', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2015-04-22 12:29:42'),
	(3, NULL, NULL, 'Sumit', 'M', 'Pathak', 'sumitp@mindnerves.com', NULL, 'OnRolls', NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 'qwer123', NULL, NULL, NULL, NULL, NULL, 'PendingApproval', NULL, '2015-04-22 12:21:19'),
	(4, NULL, NULL, 'gmail.com Admin', NULL, NULL, 'jagbir.paul@gmail.com', NULL, NULL, NULL, NULL, NULL, 2, 'Admin', NULL, NULL, NULL, NULL, NULL, NULL, 'Delegate|FeedBackCreate|FeedBackView|TeamRate|ProjectReport|Notification|ApplyLeave|Leaves|Timesheet|CreateTimesheet|SearchTimesheet|Mail|UserRequest|DefineRoles|OrgHierarchy|DefineProjects|UserPermissions|ManageUser|ManageProject|ManageTask|ManageClient|LeaveSettings|RolePermissions|DefineLeaves', 1, 'qwer123', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2015-04-22 12:25:22'),
	(5, NULL, NULL, 'Akash', 'Sarpanch', 'Shinde', 'akash@mindnerves.com', NULL, 'OnRolls', NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 'qwer123', NULL, NULL, NULL, NULL, NULL, 'PendingApproval', NULL, '2015-04-22 12:28:12');
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.user_flexi: ~0 rows (approximately)
/*!40000 ALTER TABLE `user_flexi` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table timemgmt1.user_leave: ~0 rows (approximately)
/*!40000 ALTER TABLE `user_leave` DISABLE KEYS */;
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
