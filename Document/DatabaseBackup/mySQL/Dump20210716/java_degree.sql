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
-- Table structure for table `degree`
--

DROP TABLE IF EXISTS `degree`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `degree` (
  `dID` int(11) NOT NULL COMMENT '学位序号',
  `dName` char(32) NOT NULL COMMENT '学位名',
  `roomType1` int(11) DEFAULT NULL COMMENT '寝室规格\\n            记录该寝室是几人寝',
  `roomType2` int(11) DEFAULT NULL COMMENT '寝室规格\\n            记录该寝室是几人寝',
  `roomType3` int(11) DEFAULT NULL COMMENT '寝室规格\\n            记录该寝室是几人寝',
  PRIMARY KEY (`dID`),
  KEY `FK_AvailableRoomType1` (`roomType1`),
  KEY `FK_AvailableRoomType2` (`roomType3`),
  KEY `FK_AvailableRoomType3` (`roomType2`),
  CONSTRAINT `FK_AvailableRoomType1` FOREIGN KEY (`roomType1`) REFERENCES `RoomPrice` (`rCapacity`),
  CONSTRAINT `FK_AvailableRoomType2` FOREIGN KEY (`roomType3`) REFERENCES `RoomPrice` (`rCapacity`),
  CONSTRAINT `FK_AvailableRoomType3` FOREIGN KEY (`roomType2`) REFERENCES `RoomPrice` (`rCapacity`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学位表\n存储学名名称和对应序号，例如\n1  本科生\n2  研究生\n3  ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `degree`
--

LOCK TABLES `degree` WRITE;
/*!40000 ALTER TABLE `degree` DISABLE KEYS */;
INSERT INTO `degree` VALUES (1,'本科生',4,NULL,NULL),(2,'研究生',4,NULL,NULL);
/*!40000 ALTER TABLE `degree` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-16 15:36:04
