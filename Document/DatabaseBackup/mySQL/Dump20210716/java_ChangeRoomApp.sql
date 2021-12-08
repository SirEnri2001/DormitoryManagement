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
-- Table structure for table `ChangeRoomApp`
--

DROP TABLE IF EXISTS `ChangeRoomApp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ChangeRoomApp` (
  `crID` char(16) NOT NULL COMMENT '换寝申请单号\n            change room (application) ID',
  `sID` char(16) NOT NULL COMMENT '学生号\n            每个学生的唯一标识，也作登陆账号',
  `bIDout` char(4) NOT NULL COMMENT '宿舍楼号',
  `rIDout` char(4) NOT NULL COMMENT '房间号',
  `bIDtar` char(4) NOT NULL COMMENT '宿舍楼号',
  `rIDtar` char(4) NOT NULL COMMENT '房间号',
  `appDate` date NOT NULL COMMENT '申请建立时间',
  `reason` char(64) DEFAULT NULL COMMENT '申请理由',
  `mID` char(16) NOT NULL COMMENT '管理员ID \n            也作管理员登录账号',
  `pass` tinyint(1) DEFAULT NULL COMMENT '是否通过标识符\n            当刚建立申请，还未审批时，该标识符留空\n            审批不通过，标识置flase\n            审批通过，标识置true\n            不论通过与否，申请记录不删除',
  `passDate` date DEFAULT NULL COMMENT '审批决定的日期\n            即决定通过/不通过的日期，刚建立申请时留空，决定审批结果时更新',
  PRIMARY KEY (`crID`),
  KEY `FK_ApplyChangeRoom` (`sID`),
  KEY `FK_ChangeFrom` (`bIDtar`,`rIDtar`),
  KEY `FK_ChangeTo` (`bIDout`,`rIDout`),
  KEY `FK_PassChangeRoomApp` (`mID`),
  CONSTRAINT `FK_ApplyChangeRoom` FOREIGN KEY (`sID`) REFERENCES `Student` (`sID`),
  CONSTRAINT `FK_ChangeFrom` FOREIGN KEY (`bIDtar`, `rIDtar`) REFERENCES `Room` (`bID`, `rID`),
  CONSTRAINT `FK_ChangeTo` FOREIGN KEY (`bIDout`, `rIDout`) REFERENCES `Room` (`bID`, `rID`),
  CONSTRAINT `FK_PassChangeRoomApp` FOREIGN KEY (`mID`) REFERENCES `Manager` (`mID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='换寝申请表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ChangeRoomApp`
--

LOCK TABLES `ChangeRoomApp` WRITE;
/*!40000 ALTER TABLE `ChangeRoomApp` DISABLE KEYS */;
/*!40000 ALTER TABLE `ChangeRoomApp` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-16 15:34:49
