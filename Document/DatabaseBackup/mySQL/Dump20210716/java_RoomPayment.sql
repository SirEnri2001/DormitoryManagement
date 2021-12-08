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
-- Table structure for table `RoomPayment`
--

DROP TABLE IF EXISTS `RoomPayment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `RoomPayment` (
  `rpID` char(16) NOT NULL COMMENT '住宿费缴费单号',
  `sID` char(16) NOT NULL COMMENT '学生号\n            每个学生的唯一标识，也作登陆账号',
  `bID` char(4) NOT NULL COMMENT '宿舍楼号',
  `rID` char(4) NOT NULL COMMENT '房间号',
  `setDate` date NOT NULL COMMENT '欠费记录产生日期',
  `rCapacity` int(11) NOT NULL COMMENT '寝室规格\n            记录该寝室是几人寝',
  `amount` float NOT NULL COMMENT '需付金额',
  `payDate` date DEFAULT NULL COMMENT '实际缴费日期\n            完成缴费前留空，缴费时更新',
  PRIMARY KEY (`rpID`),
  KEY `FK_PayRoom` (`sID`),
  KEY `FK_RoomPayApplyTo` (`bID`,`rID`),
  KEY `FK_RoomPayReference` (`rCapacity`),
  CONSTRAINT `FK_PayRoom` FOREIGN KEY (`sID`) REFERENCES `Student` (`sID`),
  CONSTRAINT `FK_RoomPayApplyTo` FOREIGN KEY (`bID`, `rID`) REFERENCES `Room` (`bID`, `rID`),
  CONSTRAINT `FK_RoomPayReference` FOREIGN KEY (`rCapacity`) REFERENCES `RoomPrice` (`rCapacity`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='住宿费欠费缴费表\n住宿费缴费不可溢出，只能需付多少付多少，故记录中只记录一个需付金额而没有记录实付金额';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RoomPayment`
--

LOCK TABLES `RoomPayment` WRITE;
/*!40000 ALTER TABLE `RoomPayment` DISABLE KEYS */;
/*!40000 ALTER TABLE `RoomPayment` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-16 15:36:11
