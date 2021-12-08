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
-- Table structure for table `ElecPayment`
--

DROP TABLE IF EXISTS `ElecPayment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ElecPayment` (
  `epayID` char(16) NOT NULL COMMENT '电费单号',
  `sID` char(16) NOT NULL COMMENT '学生号\n            每个学生的唯一标识，也作登陆账号',
  `bID` char(4) NOT NULL COMMENT '宿舍楼号',
  `rID` char(4) NOT NULL COMMENT '房间号',
  `elecUsage` float NOT NULL COMMENT '当月用电量',
  `grade` int(11) NOT NULL COMMENT '电费价位等级',
  `cost` float NOT NULL COMMENT '当月用电量对应电费消费',
  `balance` float NOT NULL COMMENT '当月月初余额\n            ',
  `setDate` date NOT NULL COMMENT '欠费记录结算日期',
  `payment` float DEFAULT NULL COMMENT '实付电费金额\n            付款前留空',
  `payDate` date DEFAULT NULL COMMENT '实际付款日期\n            付款前留空',
  PRIMARY KEY (`epayID`),
  KEY `FK_ElecPayApplyTo` (`bID`,`rID`),
  KEY `FK_ElecPayReference` (`grade`),
  KEY `FK_PayElec` (`sID`),
  CONSTRAINT `FK_ElecPayApplyTo` FOREIGN KEY (`bID`, `rID`) REFERENCES `Room` (`bID`, `rID`),
  CONSTRAINT `FK_ElecPayReference` FOREIGN KEY (`grade`) REFERENCES `ElecPrice` (`grade`),
  CONSTRAINT `FK_PayElec` FOREIGN KEY (`sID`) REFERENCES `Student` (`sID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='电费欠费缴费记录表\n每月按照用量计算消费，缴费可溢出，如果余额不足则在此表中产生欠费记录，并通知对应寝室同学';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ElecPayment`
--

LOCK TABLES `ElecPayment` WRITE;
/*!40000 ALTER TABLE `ElecPayment` DISABLE KEYS */;
/*!40000 ALTER TABLE `ElecPayment` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-16 15:35:02
