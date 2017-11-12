CREATE TABLE `project` (
  `project_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_name` varchar(30) NOT NULL,
  PRIMARY KEY (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `key_words` (
  `keyword_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `keyword_name` varchar(50) NOT NULL,
  `operation` varchar(50) DEFAULT NULL,
  `param_layout` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`keyword_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `target_env` (
  `env_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `env_name` varchar(50) NOT NULL,
  `env_description` varchar(100) DEFAULT NULL,
  `platform` varchar(10) DEFAULT NULL,
  `url_forcall` varchar(255) DEFAULT NULL,
  `equipment_id` varchar(100) DEFAULT NULL,
  `equipment_name` varchar(100) DEFAULT NULL,
  `os_version` varchar(50) DEFAULT NULL,
  `capability` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`env_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `test_case` (
  `case_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `case_version` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  `case_track_id` bigint(20) DEFAULT NULL,
  `referenced` int(1) DEFAULT '0',
  PRIMARY KEY (`case_id`),
  KEY `FK_TC_2_idx` (`project_id`),
  CONSTRAINT `FK_TC_2` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `test_case_track` (
  `case_track_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `case_id` bigint(20) NOT NULL,
  `case_version` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  `case_name` varchar(16) NOT NULL,
  `case_description` varchar(50) NOT NULL,
  `target_platform` varchar(10) DEFAULT NULL,
  `referenced` int(1) DEFAULT '0',
  `created_by` varchar(30) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(30) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`case_track_id`),
  KEY `idx_testcase_track` (`case_id`,`case_version`) USING BTREE,
  KEY `FK_TCT_1_idx` (`project_id`),
  CONSTRAINT `FK_TCT_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `test_steps` (
  `step_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `case_track_id` bigint(20) NOT NULL,
  `step_seq` bigint(20) NOT NULL,
  `keyword_id` bigint(20) NOT NULL,
  `target_type` varchar(255) NOT NULL,
  `referenced_case_track_id` bigint(20) DEFAULT NULL,
  `target_locator_by` varchar(20) DEFAULT NULL,
  `targetLocatorValue` varchar(100) DEFAULT NULL,
  `target_value` varchar(255) DEFAULT NULL,
  `default_data` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`step_id`),
  KEY `FK_TS_1_idx` (`case_track_id`),
  KEY `FK_TS_2_idx` (`keyword_id`),
  CONSTRAINT `FK_TS_1` FOREIGN KEY (`case_track_id`) REFERENCES `test_case_track` (`case_track_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_TS_2` FOREIGN KEY (`keyword_id`) REFERENCES `key_words` (`keyword_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `test_round_data` (
  `round_data_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `case_track_id` bigint(20) NOT NULL,
  `round_num` bigint(20) NOT NULL,
  `step_id` bigint(20) NOT NULL,
  `round_data` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`round_data_id`),
  KEY `FK_TRD_1_idx` (`case_track_id`),
  KEY `FK_TRD_2_idx` (`step_id`),
  CONSTRAINT `FK_TRD_1` FOREIGN KEY (`case_track_id`) REFERENCES `test_case_track` (`case_track_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_TRD_2` FOREIGN KEY (`step_id`) REFERENCES `test_steps` (`step_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `test_plan` (
  `plan_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `plan_name` varchar(50) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  `platform` varchar(10) DEFAULT NULL,
  `env_id` bigint(20) NOT NULL,
  `schedule_config` varchar(255) DEFAULT NULL,
  `created_by` varchar(30) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(30) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`plan_id`),
  KEY `FK_TP_1_idx` (`project_id`),
  CONSTRAINT `FK_TP_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `test_plan_suite` (
  `plan_id` bigint(20) NOT NULL,
  `case_id` bigint(20) NOT NULL,
  PRIMARY KEY (`plan_id`,`case_id`),
  KEY `FK_TPS_2_idx` (`case_id`),
  CONSTRAINT `FK_TPS_1` FOREIGN KEY (`plan_id`) REFERENCES `test_plan` (`plan_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_TPS_2` FOREIGN KEY (`case_id`) REFERENCES `test_case` (`case_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `test_execute_plan_log` (
  `execute_plan_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `plan_id` bigint(20) NOT NULL,
  `plan_name_stamp` varchar(60) NOT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `execute_summary_log` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`execute_plan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `test_execute_log` (
  `execute_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `execute_plan_id` bigint(20) NOT NULL,
  `case_track_id` bigint(20) NOT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `execute_log` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`execute_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `test_execute_step_log` (
  `execute_step_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `execute_id` bigint(20) DEFAULT NULL,
  `round_data_id` bigint(20) DEFAULT NULL,
  `snapshot_location` varchar(255) DEFAULT NULL,
  `step_log` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`execute_step_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
