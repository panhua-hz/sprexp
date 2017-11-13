--
-- Temporary table structure for view `v_user_superuser`
--
DROP TABLE IF EXISTS `v_user_superuser`;
/*!50001 DROP VIEW IF EXISTS `v_user_superuser`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `v_user_superuser` (
  `id` int(11),
  `user_name` varchar(100),
  `job_num` varchar(40),
  `display_name` varchar(50),
  `user_type` varchar(30),
  `password` varchar(45),
  `email` varchar(100),
  `is_locked` bit(1),
  `is_domain` bit(1),
  `is_effectual` bit(1),
  `statusCode` char(1),
  `default_project` int(11),
  `is_dashboard_on` bit(1),
  `is_super_user` decimal(10,0)
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;


--
-- Final view structure for view `v_user_superuser`
--
/*!50001 DROP TABLE IF EXISTS `v_user_superuser`*/;
/*!50001 DROP VIEW IF EXISTS `v_user_superuser`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `v_user_superuser` AS select `u`.`id` AS `id`,`u`.`user_name` AS `user_name`,`u`.`job_num` AS `job_num`,`u`.`display_name` AS `display_name`,`u`.`user_type` AS `user_type`,`u`.`password` AS `password`,`u`.`email` AS `email`,`u`.`is_locked` AS `is_locked`,`u`.`is_domain` AS `is_domain`,`u`.`is_effectual` AS `is_effectual`,`u`.`status_code` AS `statusCode`,`u`.`default_project` AS `default_project`,`u`.`is_dashboard_on` AS `is_dashboard_on`,cast((ifnull(`su`.`user_id`,0) / ifnull(`su`.`user_id`,1)) as decimal(10,0)) AS `is_super_user` from (`user` `u` left join `super_user` `su` on((`u`.`id` = `su`.`user_id`))) order by cast((ifnull(`su`.`user_id`,0) / ifnull(`su`.`user_id`,1)) as decimal(10,0)) desc */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-13 16:22:53