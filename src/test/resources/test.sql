
-- Dumping structure for table ebean-test.apply_leave
CREATE TABLE IF NOT EXISTS `apply_leave` (
  `id` bigint(20) NOT NULL auto_increment,
  `start_date` datetime default NULL,
  `no_of_days` varchar(255) default NULL,
  `type_of_leave` varchar(14) default NULL,
  `remarks` varchar(255) default NULL,
  `status` varchar(9) default NULL,
  `user_id` bigint(20) default NULL,
  `pending_with_id` bigint(20) default NULL,
  `level` int(11) default NULL,
  `process_instance_id` varchar(255) default NULL,
  `leave_guid` varchar(255) default NULL,
  PRIMARY KEY  (`id`),
  KEY `ix_apply_leave_user_1` (`user_id`),
  KEY `ix_apply_leave_pendingWith_2` (`pending_with_id`),
  CONSTRAINT `fk_apply_leave_pendingWith_2` FOREIGN KEY (`pending_with_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_apply_leave_user_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table ebean-test.apply_leave: ~1 rows (approximately)
/*!40000 ALTER TABLE `apply_leave` DISABLE KEYS */;
INSERT INTO `apply_leave` (`id`, `start_date`, `no_of_days`, `type_of_leave`, `remarks`, `status`, `user_id`, `pending_with_id`, `level`, `process_instance_id`, `leave_guid`) VALUES
	(1, '2014-01-29 00:00:00', '2', 'AnnualLeave', 'asda', 'Approved', 6, 5, 1, '145', '7d4eea88-7085-4a2d-96d2-8af51acce0cc');
/*!40000 ALTER TABLE `apply_leave` ENABLE KEYS */;


-- Dumping structure for table ebean-test.client
CREATE TABLE IF NOT EXISTS `client` (
  `id` bigint(20) NOT NULL auto_increment,
  `client_name` varchar(255) default NULL,
  `phone_no` varchar(255) default NULL,
  `email` varchar(255) default NULL,
  `fax` int(11) default NULL,
  `address` varchar(255) default NULL,
  `street` varchar(255) default NULL,
  `city` varchar(255) default NULL,
  `country` varchar(255) default NULL,
  `pin` varchar(255) default NULL,
  `contact_name` varchar(255) default NULL,
  `contact_phone` varchar(255) default NULL,
  `contact_email` varchar(255) default NULL,
  `company_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `ix_client_company_3` (`company_id`),
  CONSTRAINT `fk_client_company_3` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table ebean-test.client: ~1 rows (approximately)
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` (`id`, `client_name`, `phone_no`, `email`, `fax`, `address`, `street`, `city`, `country`, `pin`, `contact_name`, `contact_phone`, `contact_email`, `company_id`) VALUES
	(1, 'Akash', '123456789', 'akash@gmail.com', 12345678, 'kharadi', '', 'pune', 'india', '411014', 'Akash', '123456789', 'akash@gmail.com', 1);
/*!40000 ALTER TABLE `client` ENABLE KEYS */;


-- Dumping structure for table ebean-test.company
CREATE TABLE IF NOT EXISTS `company` (
  `id` bigint(20) NOT NULL auto_increment,
  `company_name` varchar(255) default NULL,
  `company_code` varchar(255) default NULL,
  `company_email` varchar(255) default NULL,
  `company_phone` varchar(255) default NULL,
  `address` varchar(255) default NULL,
  `company_status` varchar(15) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table ebean-test.company: ~1 rows (approximately)
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` (`id`, `company_name`, `company_code`, `company_email`, `company_phone`, `address`, `company_status`) VALUES
	(1, 'google', 'gmail.com', 'akg8990@gmail.com', '123456789', 'asasasasasasasasa', 'Approved');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;


-- Dumping structure for table ebean-test.delegate
CREATE TABLE IF NOT EXISTS `delegate` (
  `id` bigint(20) NOT NULL auto_increment,
  `from_date` datetime default NULL,
  `to_date` datetime default NULL,
  `delegator_id` bigint(20) default NULL,
  `delegated_to_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `ix_delegate_delegator_4` (`delegator_id`),
  KEY `ix_delegate_delegatedTo_5` (`delegated_to_id`),
  CONSTRAINT `fk_delegate_delegatedTo_5` FOREIGN KEY (`delegated_to_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_delegate_delegator_4` FOREIGN KEY (`delegator_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table ebean-test.delegate: ~0 rows (approximately)
/*!40000 ALTER TABLE `delegate` DISABLE KEYS */;
/*!40000 ALTER TABLE `delegate` ENABLE KEYS */;


-- Dumping structure for table ebean-test.feedback
CREATE TABLE IF NOT EXISTS `feedback` (
  `id` bigint(20) NOT NULL auto_increment,
  `feedback_date` datetime default NULL,
  `feedback` longtext,
  `rating` int(11) default NULL,
  `user_id` bigint(20) default NULL,
  `manager_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `ix_feedback_user_6` (`user_id`),
  KEY `ix_feedback_manager_7` (`manager_id`),
  CONSTRAINT `fk_feedback_manager_7` FOREIGN KEY (`manager_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_feedback_user_6` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table ebean-test.feedback: ~0 rows (approximately)
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;


-- Dumping structure for table ebean-test.mail_setting
CREATE TABLE IF NOT EXISTS `mail_setting` (
  `id` bigint(20) NOT NULL auto_increment,
  `host_name` varchar(255) default NULL,
  `port_number` varchar(255) default NULL,
  `ssl_port` tinyint(1) default '0',
  `tls_port` tinyint(1) default '0',
  `user_name` varchar(255) default NULL,
  `password` varchar(255) default NULL,
  `company_object_id` bigint(20) default NULL,
  `last_update` datetime NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `ix_mail_setting_companyObject_8` (`company_object_id`),
  CONSTRAINT `fk_mail_setting_companyObject_8` FOREIGN KEY (`company_object_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumping data for table ebean-test.mail_setting: ~1 rows (approximately)
/*!40000 ALTER TABLE `mail_setting` DISABLE KEYS */;
INSERT INTO `mail_setting` (`id`, `host_name`, `port_number`, `ssl_port`, `tls_port`, `user_name`, `password`, `company_object_id`, `last_update`) VALUES
	(3, NULL, NULL, NULL, NULL, 'akg8990@gmail.com', NULL, 1, '2014-01-29 12:01:47');
/*!40000 ALTER TABLE `mail_setting` ENABLE KEYS */;


-- Dumping structure for table ebean-test.notification
CREATE TABLE IF NOT EXISTS `notification` (
  `id` bigint(20) NOT NULL auto_increment,
  `setting_as_json` varchar(255) default NULL,
  `company_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `ix_notification_company_9` (`company_id`),
  CONSTRAINT `fk_notification_company_9` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumping data for table ebean-test.notification: ~1 rows (approximately)
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` (`id`, `setting_as_json`, `company_id`) VALUES
	(3, NULL, 1);
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;


-- Dumping structure for table ebean-test.project
CREATE TABLE IF NOT EXISTS `project` (
  `id` bigint(20) NOT NULL auto_increment,
  `client_name_id` bigint(20) default NULL,
  `project_name` varchar(255) default NULL,
  `project_code` varchar(255) default NULL,
  `project_description` varchar(255) default NULL,
  `start_date` datetime default NULL,
  `end_date` datetime default NULL,
  `budget` double default NULL,
  `currency` varchar(6) default NULL,
  `efforts` int(11) default NULL,
  `project_manager_id` bigint(20) default NULL,
  `company_obj_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `ix_project_clientName_10` (`client_name_id`),
  KEY `ix_project_projectManager_11` (`project_manager_id`),
  KEY `ix_project_companyObj_12` (`company_obj_id`),
  CONSTRAINT `fk_project_clientName_10` FOREIGN KEY (`client_name_id`) REFERENCES `client` (`id`),
  CONSTRAINT `fk_project_companyObj_12` FOREIGN KEY (`company_obj_id`) REFERENCES `company` (`id`),
  CONSTRAINT `fk_project_projectManager_11` FOREIGN KEY (`project_manager_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table ebean-test.project: ~1 rows (approximately)
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` (`id`, `client_name_id`, `project_name`, `project_code`, `project_description`, `start_date`, `end_date`, `budget`, `currency`, `efforts`, `project_manager_id`, `company_obj_id`) VALUES
	(1, 1, 'Aptitude Test 2', 'AT-2', 'exam 2', '2014-01-20 00:00:00', '2014-02-10 00:00:00', 123456, 'INR', 2, 5, 1);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;


-- Dumping structure for table ebean-test.project_task
CREATE TABLE IF NOT EXISTS `project_task` (
  `project_id` bigint(20) NOT NULL,
  `task_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`project_id`,`task_id`),
  KEY `fk_project_task_task_02` (`task_id`),
  CONSTRAINT `fk_project_task_project_01` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  CONSTRAINT `fk_project_task_task_02` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table ebean-test.project_task: ~0 rows (approximately)
/*!40000 ALTER TABLE `project_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_task` ENABLE KEYS */;


-- Dumping structure for table ebean-test.project_user
CREATE TABLE IF NOT EXISTS `project_user` (
  `project_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`project_id`,`user_id`),
  KEY `fk_project_user_user_02` (`user_id`),
  CONSTRAINT `fk_project_user_project_01` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  CONSTRAINT `fk_project_user_user_02` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table ebean-test.project_user: ~2 rows (approximately)
/*!40000 ALTER TABLE `project_user` DISABLE KEYS */;
INSERT INTO `project_user` (`project_id`, `user_id`) VALUES
	(1, 5),
	(1, 6);
/*!40000 ALTER TABLE `project_user` ENABLE KEYS */;


-- Dumping structure for table ebean-test.role_level
CREATE TABLE IF NOT EXISTS `role_level` (
  `id` bigint(20) NOT NULL auto_increment,
  `role_x_id` bigint(20) default NULL,
  `role_level` int(11) default NULL,
  `role_name` varchar(255) default NULL,
  `reporting_to` varchar(255) default NULL,
  `final_approval` varchar(255) default NULL,
  `permissions` varchar(700) default NULL,
  `last_update` datetime NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `ix_role_level_roleX_13` (`role_x_id`),
  CONSTRAINT `fk_role_level_roleX_13` FOREIGN KEY (`role_x_id`) REFERENCES `role_x` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- Dumping data for table ebean-test.role_level: ~9 rows (approximately)
/*!40000 ALTER TABLE `role_level` DISABLE KEYS */;
INSERT INTO `role_level` (`id`, `role_x_id`, `role_level`, `role_name`, `reporting_to`, `final_approval`, `permissions`, `last_update`) VALUES
	(1, 1, 0, 'Subject Mater Expert', 'Senior Subject Mater Expert', 'Team Lead', 'Home|ManageUser|ManageClient|ManageProject|ManageTask|Delegate|FeedBackCreate|RolePermissions|UserPermissions|UserRequest|MyBucket|LeaveBucket|TeamRate|ProjectReport|Mail|Notification|DefineRoles|OrgHierarchy|', '2014-01-30 02:14:46'),
	(2, 1, 1, 'Senior Subject Mater Expert', 'Team Lead', 'Project Manager', 'ManageUser|ManageClient|ManageProject|ManageTask|Delegate|FeedBackCreate|RolePermissions|UserPermissions|UserRequest|MyBucket|LeaveBucket|TeamRate|ProjectReport|Mail|Notification|DefineRoles|OrgHierarchy|', '2014-01-30 02:14:46'),
	(3, 1, 2, 'Team Lead', 'Expert', 'Project Manager', 'ManageUser|ManageClient|ManageProject|ManageTask|Delegate|RolePermissions|UserPermissions|UserRequest|TeamRate|ProjectReport|Mail|Notification|DefineRoles|OrgHierarchy|', '2014-01-30 02:14:46'),
	(4, 1, 3, 'Expert', 'Senior Expert', 'Director', 'Home|', '2014-02-10 06:41:13'),
	(5, 1, 4, 'Project Manager', 'Director', 'CEO', 'ManageUser|ManageClient|ManageProject|ManageTask|Delegate|RolePermissions|UserPermissions|UserRequest|Mail|Notification|DefineRoles|OrgHierarchy|', '2014-01-30 02:14:46'),
	(6, 1, 5, 'Senior Expert', 'Director', 'CEO', 'FeedBackCreate|FeedBackView|', '2014-02-10 06:40:47'),
	(7, 1, 6, 'Director', 'CEO', 'CEO', 'ManageUser|ManageClient|ManageProject|ManageTask|UserRequest|Mail|Notification|DefineRoles|OrgHierarchy|', '2014-01-30 02:14:46'),
	(8, 1, 7, 'CEO', 'CEO', 'CEO', 'FeedBackCreate|FeedBackView|', '2014-02-10 06:40:36'),
	(9, 1, 8, 'Owner', 'Subject Mater Expert', 'Subject Mater Expert', 'ManageUser|ManageClient|ManageProject|ManageTask|ApplyLeave|CreateTimesheet|SearchTimesheet|FeedBackCreate|FeedBackView|RolePermissions|UserPermissions|UserRequest|MyBucket|LeaveBucket|TeamRate|ProjectReport|Mail|Notification|DefineRoles|OrgHierarchy|', '2014-02-10 06:43:40');
/*!40000 ALTER TABLE `role_level` ENABLE KEYS */;


-- Dumping structure for table ebean-test.role_x
CREATE TABLE IF NOT EXISTS `role_x` (
  `id` bigint(20) NOT NULL auto_increment,
  `company_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `ix_role_x_company_14` (`company_id`),
  CONSTRAINT `fk_role_x_company_14` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table ebean-test.role_x: ~1 rows (approximately)
/*!40000 ALTER TABLE `role_x` DISABLE KEYS */;
INSERT INTO `role_x` (`id`, `company_id`) VALUES
	(1, 1);
/*!40000 ALTER TABLE `role_x` ENABLE KEYS */;


-- Dumping structure for table ebean-test.task
CREATE TABLE IF NOT EXISTS `task` (
  `id` bigint(20) NOT NULL auto_increment,
  `task_name` varchar(255) default NULL,
  `task_code` varchar(255) default NULL,
  `start_date` datetime default NULL,
  `end_date` datetime default NULL,
  `effort` int(11) default NULL,
  `is_billable` varchar(3) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table ebean-test.task: ~2 rows (approximately)
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` (`id`, `task_name`, `task_code`, `start_date`, `end_date`, `effort`, `is_billable`) VALUES
	(1, 'CSS', 't-2', '2014-01-29 00:00:00', '2014-01-30 00:00:00', 1, 'Yes'),
	(2, 'Billing 1', 'PS 1', '2014-01-30 00:00:00', '2014-02-28 00:00:00', 1, 'Yes');
/*!40000 ALTER TABLE `task` ENABLE KEYS */;


-- Dumping structure for table ebean-test.task_project
CREATE TABLE IF NOT EXISTS `task_project` (
  `task_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`task_id`,`project_id`),
  KEY `fk_task_project_project_02` (`project_id`),
  CONSTRAINT `fk_task_project_project_02` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  CONSTRAINT `fk_task_project_task_01` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table ebean-test.task_project: ~1 rows (approximately)
/*!40000 ALTER TABLE `task_project` DISABLE KEYS */;
INSERT INTO `task_project` (`task_id`, `project_id`) VALUES
	(1, 1);
/*!40000 ALTER TABLE `task_project` ENABLE KEYS */;


-- Dumping structure for table ebean-test.timesheet
CREATE TABLE IF NOT EXISTS `timesheet` (
  `id` bigint(20) NOT NULL auto_increment,
  `user_id` bigint(20) default NULL,
  `status` int(11) default NULL,
  `last_update_date` datetime default NULL,
  `week_of_year` int(11) default NULL,
  `year` int(11) default NULL,
  `timesheet_with_id` bigint(20) default NULL,
  `level` int(11) default NULL,
  `process_instance_id` varchar(255) default NULL,
  `tid` varchar(255) default NULL,
  PRIMARY KEY  (`id`),
  KEY `ix_timesheet_user_15` (`user_id`),
  KEY `ix_timesheet_timesheetWith_16` (`timesheet_with_id`),
  CONSTRAINT `fk_timesheet_timesheetWith_16` FOREIGN KEY (`timesheet_with_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_timesheet_user_15` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table ebean-test.timesheet: ~2 rows (approximately)
/*!40000 ALTER TABLE `timesheet` DISABLE KEYS */;
INSERT INTO `timesheet` (`id`, `user_id`, `status`, `last_update_date`, `week_of_year`, `year`, `timesheet_with_id`, `level`, `process_instance_id`, `tid`) VALUES
	(1, 6, 1, '2014-01-29 00:00:00', 5, 2014, 6, 2, '101', 'ae8e02b3-6577-4665-bb32-ba4ad109bc4b'),
	(2, 6, 3, '2014-02-03 00:00:00', 6, 2014, 5, 1, '201', 'b3be1ef1-934e-4369-ba56-852bdb2e2d61');
/*!40000 ALTER TABLE `timesheet` ENABLE KEYS */;


-- Dumping structure for table ebean-test.timesheet_row
CREATE TABLE IF NOT EXISTS `timesheet_row` (
  `id` bigint(20) NOT NULL auto_increment,
  `timesheet_id` bigint(20) default NULL,
  `project_code` varchar(255) default NULL,
  `task_code` varchar(255) default NULL,
  `total_hrs` int(11) default NULL,
  `sun` int(11) default NULL,
  `mon` int(11) default NULL,
  `tue` int(11) default NULL,
  `wed` int(11) default NULL,
  `thu` int(11) default NULL,
  `fri` int(11) default NULL,
  `sat` int(11) default NULL,
  `over_time` tinyint(1) default '0',
  PRIMARY KEY  (`id`),
  KEY `ix_timesheet_row_timesheet_17` (`timesheet_id`),
  CONSTRAINT `fk_timesheet_row_timesheet_17` FOREIGN KEY (`timesheet_id`) REFERENCES `timesheet` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table ebean-test.timesheet_row: ~2 rows (approximately)
/*!40000 ALTER TABLE `timesheet_row` DISABLE KEYS */;
INSERT INTO `timesheet_row` (`id`, `timesheet_id`, `project_code`, `task_code`, `total_hrs`, `sun`, `mon`, `tue`, `wed`, `thu`, `fri`, `sat`, `over_time`) VALUES
	(1, 1, 'AT-1', 't-1', 14, 2, 2, 2, 2, 2, 2, 2, NULL),
	(2, 2, 'AT-1', 't-1', 18, 1, 1, 12, 1, 1, 1, 1, NULL);
/*!40000 ALTER TABLE `timesheet_row` ENABLE KEYS */;


-- Dumping structure for table ebean-test.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL auto_increment,
  `salutation` varchar(4) default NULL,
  `employee_id` varchar(255) default NULL,
  `first_name` varchar(255) default NULL,
  `middle_name` varchar(255) default NULL,
  `last_name` varchar(255) default NULL,
  `email` varchar(255) default NULL,
  `gender` varchar(6) default NULL,
  `status` varchar(8) default NULL,
  `hire_date` datetime default NULL,
  `annual_income` double default NULL,
  `hourlyrate` double default NULL,
  `companyobject_id` bigint(20) default NULL,
  `designation` varchar(255) default NULL,
  `role_id` bigint(20) default NULL,
  `manager_id` bigint(20) default NULL,
  `release_date` datetime default NULL,
  `hrmanager_id` bigint(20) default NULL,
  `permissions` varchar(700) default NULL,
  `temp_password` int(11) default NULL,
  `password` varchar(255) default NULL,
  `reset_flag` tinyint(1) default '0',
  `failed_login_attempt` int(11) default NULL,
  `create_date` datetime default NULL,
  `modified_date` datetime default NULL,
  `password_reset` tinyint(1) default '0',
  `user_status` varchar(15) default NULL,
  `process_instance_id` varchar(255) default NULL,
  `last_update` datetime NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `uq_user_email` (`email`),
  KEY `ix_user_companyobject_18` (`companyobject_id`),
  KEY `ix_user_role_19` (`role_id`),
  KEY `ix_user_manager_20` (`manager_id`),
  KEY `ix_user_hrmanager_21` (`hrmanager_id`),
  CONSTRAINT `fk_user_companyobject_18` FOREIGN KEY (`companyobject_id`) REFERENCES `company` (`id`),
  CONSTRAINT `fk_user_hrmanager_21` FOREIGN KEY (`hrmanager_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_user_manager_20` FOREIGN KEY (`manager_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_user_role_19` FOREIGN KEY (`role_id`) REFERENCES `role_level` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- Dumping data for table ebean-test.user: ~7 rows (approximately)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `salutation`, `employee_id`, `first_name`, `middle_name`, `last_name`, `email`, `gender`, `status`, `hire_date`, `annual_income`, `hourlyrate`, `companyobject_id`, `designation`, `role_id`, `manager_id`, `release_date`, `hrmanager_id`, `permissions`, `temp_password`, `password`, `reset_flag`, `failed_login_attempt`, `create_date`, `modified_date`, `password_reset`, `user_status`, `process_instance_id`, `last_update`) VALUES
	(1, 'Mr', '1', 'Jagbir', 'Singh', 'Paul', 'mindnervestech@gmail.com', NULL, NULL, NULL, NULL, NULL, NULL, 'SuperAdmin', NULL, NULL, NULL, NULL, 'SearchTimesheet|CreateTimesheet|Delegate|Leaves|ApplyLeave|FeedBackCreate|FeedBackView|UserPermissions|LeaveBucket|MyBucket|TeamRate|ProjectReport|ManageUser|ManageProject|ManageTask|ManageClient|Notification|DefineRoles|OrgHierarchy|Mail|RolePermissions', 0, 'a', 0, NULL, NULL, NULL, 0, 'Approved', NULL, '2013-07-08 13:46:15'),
	(4, NULL, NULL, 'gmail.com Admin', NULL, NULL, 'akg8990@gmail.com', NULL, NULL, NULL, NULL, NULL, 1, 'Admin', NULL, NULL, NULL, NULL, 'Delegate|FeedBackCreate|FeedBackView|TeamRate|ProjectReport|Notification|ApplyLeave|Leaves|Timesheet|CreateTimesheet|SearchTimesheet|CompanyRequest', 0, 'qwe123', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2014-01-29 12:21:55'),
	(5, 'Mr', 'E-1', 'Dhiraj', '', 'Bankar', 'dhairyashil.bankar@gmail.com', 'Male', 'OnRolls', '2014-01-29 00:00:00', 1234567, 593.54, 1, 'Team Lead', 3, 4, '2014-01-31 00:00:00', NULL, 'ManageUser|ManageClient|ManageProject|ManageTask|RolePermissions|UserPermissions|UserRequest|TeamRate|ProjectReport|Mail|Notification|DefineRoles|OrgHierarchy|', 0, 'abcd123', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2014-01-29 16:16:44'),
	(6, 'Mr', 'E-2', 'Pankaj', '', 'Muneshwar', 'pankajashokmuneshwar@gmail.com', 'Male', 'OnRolls', '2014-01-30 00:00:00', 123456, 59.35, 1, 'Senior Subject Mater Expert', 2, 5, NULL, NULL, 'ManageUser|ManageClient|ManageProject|ManageTask|Delegate|FeedBackCreate|RolePermissions|UserPermissions|UserRequest|TeamRate|ProjectReport|Mail|Notification|DefineRoles|OrgHierarchy|', 0, 'abcd123', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2014-01-29 13:26:38'),
	(7, 'Mr', 'E-3', 'Nagesh', '', 'Dalave', 'nageshdalave98@gmail.com', 'Male', 'OnRolls', '2014-01-29 00:00:00', 12334556, 5930.07, 1, 'Subject Mater Expert', 1, 6, NULL, NULL, 'Home|ManageUser|ManageClient|ManageProject|ManageTask|Delegate|FeedBackCreate|RolePermissions|UserPermissions|UserRequest|MyBucket|LeaveBucket|TeamRate|ProjectReport|Mail|Notification|DefineRoles|OrgHierarchy|', 0, 'abcd123', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2014-01-29 12:50:14'),
	(8, 'Mr', 'E-4', 'Shashank', '', 'Pohare', 'shwashank12@gmail.com', 'Male', 'OnRolls', '2014-01-29 00:00:00', 1234567, 593.54, 1, 'Subject Mater Expert', 1, 6, NULL, NULL, 'Home|ManageUser|ManageClient|ManageProject|ManageTask|Delegate|FeedBackCreate|RolePermissions|UserPermissions|UserRequest|MyBucket|LeaveBucket|TeamRate|ProjectReport|Mail|Notification|DefineRoles|OrgHierarchy|', 0, 'abcd123', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2014-01-29 12:51:13'),
	(9, 'Mr', 'E-5', 'Pravin', '', 'Erande', 'pberande1@gmail.com', 'Male', 'OnRolls', '2014-01-29 00:00:00', 12345, 5.94, 1, 'Team Lead', 3, 4, NULL, NULL, 'UserRequest|ManageClient|ManageProject|OrgHierarchy|UserPermissions|Notification|Delegate|Mail|ManageUser|ManageTask|DefineRoles|RolePermissions|TeamRate|ProjectReport||', 0, 'abcd123', NULL, NULL, NULL, NULL, NULL, 'Approved', NULL, '2014-02-06 03:16:06');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;


-- Dumping structure for table ebean-test.user_project
CREATE TABLE IF NOT EXISTS `user_project` (
  `user_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`user_id`,`project_id`),
  KEY `fk_user_project_project_02` (`project_id`),
  CONSTRAINT `fk_user_project_project_02` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  CONSTRAINT `fk_user_project_user_01` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

