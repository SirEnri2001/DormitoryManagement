-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: 155.138.157.102    Database: java
-- ------------------------------------------------------
-- Server version	5.7.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Room`
--

DROP TABLE IF EXISTS `Room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Room` (
  `bID` char(4) NOT NULL COMMENT '宿舍楼号',
  `rID` char(4) NOT NULL COMMENT '房间号',
  `elecUsage` float DEFAULT NULL COMMENT '月用电量\n            月初清零，无论是否缴费。',
  `elecBalance` float DEFAULT NULL COMMENT '电费余额\n            月末结算电费时更新，月初缴费时更新，用于以月份为单位记录所欠电费或者缴费盈余',
  `elecAvailable` tinyint(1) DEFAULT '1' COMMENT '断电状态标识\n            true不断电，false断电，如果月末结算电费欠费，月初若干日后仍未缴费，则更新此项为false，阻止该宿舍用电相关函数继续提高用电量elecUsage，即给该宿舍断电',
  `waterUsage` float DEFAULT NULL COMMENT '月用水量\n            月初清零，无论是否缴费。',
  `waterBalance` float DEFAULT NULL COMMENT '水费余额\n            月末结算水费时更新，月初缴费时更新，用于以月份为单位记录所欠水费或者缴费盈余',
  `waterAvailable` tinyint(1) DEFAULT '1' COMMENT '停水状态标识\n            true不停水，false停水，如果月末结算水费欠费，月初若干日后仍未缴费，则更新此项为false，阻止该宿舍用水相关函数继续提高用水量waterUsage，即给该宿舍停水',
  `rCapacity` int(11) NOT NULL COMMENT '寝室规格\n            记录该寝室是几人寝',
  `rAvailable` int(11) DEFAULT NULL,
  `description` varchar(128) DEFAULT NULL COMMENT '寝室描述\n            方便床位未满的寝室找新室友',
  PRIMARY KEY (`bID`,`rID`),
  KEY `FK_Cost` (`rCapacity`),
  CONSTRAINT `FK_Cost` FOREIGN KEY (`rCapacity`) REFERENCES `RoomPrice` (`rCapacity`),
  CONSTRAINT `FK_Inside` FOREIGN KEY (`bID`) REFERENCES `Building` (`bID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='寝室房间表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Room`
--

LOCK TABLES `Room` WRITE;
/*!40000 ALTER TABLE `Room` DISABLE KEYS */;
INSERT INTO `Room` VALUES ('A1','101',9.14,395.43,1,9.77,351.15,1,4,0,NULL),('A1','102',2.34,398.83,1,7.69,361.55,1,4,0,NULL),('A1','103',0.82,399.59,1,7.73,361.35,1,4,0,NULL),('A1','104',4.07,397.97,1,3.56,382.2,1,4,0,NULL),('A1','105',3.03,398.49,1,1.22,393.9,1,4,0,NULL),('A1','106',1.71,399.15,1,2.35,388.25,1,4,0,NULL),('A1','107',7.12,396.44,1,2.4,388,1,4,0,NULL),('A1','108',3.66,398.17,1,2.97,385.15,1,4,0,NULL),('A1','109',3.87,398.07,1,8.84,355.8,1,4,0,NULL),('A1','110',9.3,395.35,1,0.73,396.35,1,4,0,NULL),('A1','111',1.2,399.4,1,1.38,393.1,1,4,0,NULL),('A1','112',1.48,399.26,1,3.24,383.8,1,4,0,NULL),('A1','113',4.59,397.71,1,6.98,365.1,1,4,0,NULL),('A1','114',7.05,396.48,1,2.49,387.55,1,4,0,NULL),('A1','115',0.09,399.96,1,2.72,386.4,1,4,0,NULL),('A1','116',5.43,397.29,1,3.73,381.35,1,4,0,NULL),('A1','117',6.21,396.9,1,2.51,387.45,1,4,0,NULL),('A1','118',6.07,396.97,1,7.7,361.5,1,4,0,NULL),('A1','119',6.06,396.97,1,1.76,391.2,1,4,0,NULL),('A1','120',1.74,399.13,1,4.19,379.05,1,4,0,NULL),('A1','201',7.1,396.45,1,3.69,381.55,1,4,0,NULL),('A1','202',3.96,398.02,1,1.66,391.7,1,4,0,NULL),('A1','203',2.7,398.65,1,1.7,391.5,1,4,0,NULL),('A1','204',1.01,399.5,1,3.68,381.6,1,4,0,NULL),('A1','205',1.15,399.43,1,4.7,376.5,1,4,0,NULL),('A1','206',7.3,396.35,1,6.17,369.15,1,4,0,NULL),('A1','207',3.83,398.09,1,9.01,354.95,1,4,0,NULL),('A1','208',6.51,396.75,1,9.18,354.1,1,4,0,NULL),('A1','209',3.01,398.5,1,2.37,388.15,1,4,0,NULL),('A1','210',0.83,399.59,1,4.2,379,1,4,0,NULL),('A1','211',6.7,396.65,1,0.47,397.65,1,4,0,NULL),('A1','212',0.87,399.57,1,8.5,357.5,1,4,0,NULL),('A1','213',0.26,399.87,1,6.48,367.6,1,4,0,NULL),('A1','214',3.92,398.04,1,7.01,364.95,1,4,0,NULL),('A1','215',5.43,397.29,1,9.68,351.6,1,4,0,NULL),('A1','216',1.89,399.06,1,8.32,358.4,1,4,0,NULL),('A1','217',6.95,396.53,1,1.47,392.65,1,4,0,NULL),('A1','218',9.19,395.41,1,9.65,351.75,1,4,0,NULL),('A1','219',5.55,397.23,1,6.28,368.6,1,4,0,NULL),('A1','220',0.5,399.75,1,6.53,367.35,1,4,0,NULL),('A1','301',5.68,397.16,1,6.56,367.2,1,4,0,NULL),('A1','302',8.13,395.94,1,6.98,365.1,1,4,0,NULL),('A1','303',6.87,396.57,1,0.69,396.55,1,4,0,NULL),('A1','304',4.85,397.58,1,3.88,380.6,1,4,0,NULL),('A1','305',9.81,395.1,1,9.07,354.65,1,4,0,NULL),('A1','306',7.59,396.21,1,7.39,363.05,1,4,0,NULL),('A1','307',0.51,399.75,1,2.41,387.95,1,4,0,NULL),('A1','308',0.66,399.67,1,4.22,378.9,1,4,0,NULL),('A1','309',6.49,396.76,1,1.17,394.15,1,4,0,NULL),('A1','310',1.62,399.19,1,0.58,397.1,1,4,0,NULL),('A1','311',4.81,397.6,1,8.35,358.25,1,4,0,NULL),('A1','312',0.08,399.96,1,8.04,359.8,1,4,0,NULL),('A1','313',3.78,398.11,1,6.74,366.3,1,4,0,NULL),('A1','314',5.14,297.43,1,0.14,299.3,1,4,1,NULL),('A1','315',0,0,1,0,0,1,4,4,NULL),('A1','316',0,0,1,0,0,1,4,4,NULL),('A1','317',0,0,1,0,0,1,4,4,NULL),('A1','318',0,0,1,0,0,1,4,4,NULL),('A1','319',0,0,1,0,0,1,4,4,NULL),('A1','320',0,0,1,0,0,1,4,4,NULL),('A2','101',6.57,396.72,1,8.1,359.5,1,4,0,NULL),('A2','102',3.36,398.32,1,8.37,358.15,1,4,0,NULL),('A2','103',1.64,399.18,1,3.47,382.65,1,4,0,NULL),('A2','104',6.37,396.82,1,1.37,393.15,1,4,0,NULL),('A2','105',6.1,396.95,1,5.95,370.25,1,4,0,NULL),('A2','106',2.34,398.83,1,2.86,385.7,1,4,0,NULL),('A2','107',4.97,397.52,1,5.36,373.2,1,4,0,NULL),('A2','108',8.34,395.83,1,7.79,361.05,1,4,0,NULL),('A2','109',5,397.5,1,2.24,388.8,1,4,0,NULL),('A2','110',6.8,396.6,1,9.57,352.15,1,4,0,NULL),('A2','111',1.12,399.44,1,0.9,395.5,1,4,0,NULL),('A2','112',3.07,398.47,1,6.42,367.9,1,4,0,NULL),('A2','113',1.88,399.06,1,3.41,382.95,1,4,0,NULL),('A2','114',3.11,398.45,1,5.01,374.95,1,4,0,NULL),('A2','115',4.69,397.66,1,5.44,372.8,1,4,0,NULL),('A2','116',9.09,395.46,1,1.13,394.35,1,4,0,NULL),('A2','117',5.96,397.02,1,5.67,371.65,1,4,0,NULL),('A2','118',5.87,397.07,1,8.45,357.75,1,4,0,NULL),('A2','119',6.31,396.85,1,6.52,367.4,1,4,0,NULL),('A2','120',0.25,399.88,1,7.53,362.35,1,4,0,NULL),('A2','201',8.39,395.81,1,3.45,382.75,1,4,0,NULL),('A2','202',5.56,397.22,1,4.21,378.95,1,4,0,NULL),('A2','203',6.69,396.66,1,0.89,395.55,1,4,0,NULL),('A2','204',1.65,399.18,1,0.08,399.6,1,4,0,NULL),('A2','205',0,0,1,0,0,1,4,4,NULL),('A2','206',0,0,1,0,0,1,4,4,NULL),('A2','207',0,0,1,0,0,1,4,4,NULL),('A2','208',0,0,1,0,0,1,4,4,NULL),('A2','209',0,0,1,0,0,1,4,4,NULL),('A2','210',0,0,1,0,0,1,4,4,NULL),('A3','101',7.15,396.43,1,8.41,357.95,1,4,0,NULL),('A3','102',7.31,396.35,1,8.98,355.1,1,4,0,NULL),('A3','103',6.89,396.56,1,3.71,381.45,1,4,0,NULL),('A3','104',1.61,399.2,1,5.46,372.7,1,4,0,NULL),('A3','105',0.72,399.64,1,9.97,350.15,1,4,0,NULL),('A3','106',5.07,397.47,1,5.37,373.15,1,4,0,NULL),('A3','107',2.37,398.82,1,1.4,393,1,4,0,NULL),('A3','108',0.85,399.58,1,2.21,388.95,1,4,0,NULL),('A3','109',0.63,399.69,1,9.43,352.85,1,4,0,NULL),('A3','110',1.41,399.3,1,0.29,398.55,1,4,0,NULL),('A3','111',9.92,395.04,1,4.24,378.8,1,4,0,NULL),('A3','112',0.92,399.54,1,7.63,361.85,1,4,0,NULL),('A3','113',0.87,399.57,1,5.07,374.65,1,4,0,NULL),('A3','114',8.47,395.77,1,7.44,362.8,1,4,0,NULL),('A3','115',2.76,398.62,1,7.07,364.65,1,4,0,NULL),('A3','116',8.99,395.51,1,8.92,355.4,1,4,0,NULL),('A3','117',0.51,399.75,1,0.75,396.25,1,4,0,NULL),('A3','118',4.66,397.67,1,7.1,364.5,1,4,0,NULL),('A3','119',7.54,396.23,1,4.97,375.15,1,4,0,NULL),('A3','120',6.66,396.67,1,3.01,384.95,1,4,0,NULL),('A3','201',7.8,396.1,1,7.49,362.55,1,4,0,NULL),('A3','202',1.49,399.26,1,7.14,364.3,1,4,0,NULL),('A3','203',0,0,1,0,0,1,4,4,NULL),('A3','204',0,0,1,0,0,1,4,4,NULL),('A3','205',0,0,1,0,0,1,4,4,NULL),('A3','206',0,0,1,0,0,1,4,4,NULL),('A3','207',0,0,1,0,0,1,4,4,NULL),('A3','208',0,0,1,0,0,1,4,4,NULL),('A3','209',0,0,1,0,0,1,4,4,NULL),('A3','210',0,0,1,0,0,1,4,4,NULL),('B1','101',2.92,398.54,1,1.68,391.6,1,4,0,NULL),('B1','102',3.26,398.37,1,8.89,355.55,1,4,0,NULL),('B1','103',3.95,398.03,1,0.7,396.5,1,4,0,NULL),('B1','104',7.85,396.08,1,4.96,375.2,1,4,0,NULL),('B1','105',1.6,399.2,1,8.95,355.25,1,4,0,NULL),('B1','106',0.42,399.79,1,1.26,393.7,1,4,0,NULL),('B1','107',1.26,399.37,1,3.56,382.2,1,4,0,NULL),('B1','108',6.21,396.9,1,6.98,365.1,1,4,0,NULL),('B1','109',3.94,398.03,1,3.97,380.15,1,4,0,NULL),('B1','110',4.45,397.78,1,9.82,350.9,1,4,0,NULL),('B1','111',7.3,396.35,1,5.46,372.7,1,4,0,NULL),('B1','112',9.69,395.16,1,2.04,389.8,1,4,0,NULL),('B1','113',5.73,397.14,1,8.2,359,1,4,0,NULL),('B1','114',4.59,397.71,1,9.97,350.15,1,4,0,NULL),('B1','115',3.96,398.02,1,7.48,362.6,1,4,0,NULL),('B1','116',1.77,399.12,1,0.63,396.85,1,4,0,NULL),('B1','117',7.29,396.36,1,2.53,387.35,1,4,0,NULL),('B1','118',8.62,395.69,1,2.25,388.75,1,4,0,NULL),('B1','119',7.17,396.42,1,1.89,390.55,1,4,0,NULL),('B1','120',9.97,395.02,1,3.2,384,1,4,0,NULL),('B1','201',5.49,397.26,1,1.3,393.5,1,4,0,NULL),('B1','202',1.76,399.12,1,1.92,390.4,1,4,0,NULL),('B1','203',2.43,398.79,1,4.48,377.6,1,4,0,NULL),('B1','204',3.04,398.48,1,3.36,383.2,1,4,0,NULL),('B1','205',7.83,396.09,1,9.71,351.45,1,4,0,NULL),('B1','206',7.21,396.4,1,5.18,374.1,1,4,0,NULL),('B1','207',1.46,299.27,1,6.49,267.55,1,4,1,NULL),('B1','208',0,0,1,0,0,1,4,4,NULL),('B1','209',0,0,1,0,0,1,4,4,NULL),('B1','210',0,0,1,0,0,1,4,4,NULL);
/*!40000 ALTER TABLE `Room` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-16 15:34:55
