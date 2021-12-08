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
-- Table structure for table `FieldNameTransformer`
--

DROP TABLE IF EXISTS `FieldNameTransformer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `FieldNameTransformer` (
  `ClassName` varchar(30) DEFAULT NULL,
  `FieldName` varchar(30) DEFAULT NULL,
  `ColumnName` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FieldNameTransformer`
--

LOCK TABLES `FieldNameTransformer` WRITE;
/*!40000 ALTER TABLE `FieldNameTransformer` DISABLE KEYS */;
INSERT INTO `FieldNameTransformer` VALUES ('BuildingInfo','BuildingID','bID'),('BuildingInfo','Capacity','bCapacity'),('BuildingInfo','Available','bAvailable'),('ChangeRoomApp','StudentID','sID'),('ChangeRoomApp','ApplicationID','crID'),('ChangeRoomApp','Building','bIDout'),('ChangeRoomApp','Room','rIDout'),('ChangeRoomApp','BuildingTar','bIDtar'),('ChangeRoomApp','RoomTar','rIDtar'),('ChangeRoomApp','appDate','appDate'),('ChangeRoomApp','Reason','reason'),('ChangeRoomApp','ManagerID','mID'),('ChangeRoomApp','isPass','pass'),('ChangeRoomApp','passDate','passDate'),('LeaveApp','StudentID','sID'),('LeaveApp','ApplicationID','leaID'),('LeaveApp','appDate','appDate'),('LeaveApp','leaDate','leaDate'),('LeaveApp','expRetDate','expRetDate'),('LeaveApp','ActRetDate','actRetDate'),('LeaveApp','Reason','reason'),('LeaveApp','ManagerID','mID'),('LeaveApp','isPass','pass'),('LeaveApp','passDate','passDate'),('MoveInApp','StudentID','sID'),('MoveInApp','ApplicationID','miID'),('MoveInApp','Building','bID'),('MoveInApp','Room','rID'),('MoveInApp','appDate','appDate'),('MoveInApp','Reason','reason'),('MoveInApp','ManagerID','mID'),('MoveInApp','isPass','pass'),('MoveInApp','passDate','passDate'),('MoveOutApp','StudentID','sID'),('MoveOutApp','ApplicationID','moID'),('MoveOutApp','Building','bID'),('MoveOutApp','Room','rID'),('MoveOutApp','appDate','appDate'),('MoveOutApp','Reason','reason'),('MoveOutApp','ManagerID','mID'),('MoveOutApp','isPass','pass'),('MoveOutApp','passDate','passDate'),('ClassInfo','Degree','dID'),('ClassInfo','Grade','grade'),('ClassInfo','classID','class'),('ClassInfo','Major','majID'),('ClassInfo','Manager','mID'),('ElecPaymentInfo','PayDate','payDate'),('ElecPaymentInfo','SetDate','setDate'),('ElecPaymentInfo','RoomId','rID'),('ElecPaymentInfo','BuildingId','bID'),('ElecPaymentInfo','StudentId','sID'),('ElecPaymentInfo','PaymentID','epayID'),('ElecPaymentInfo','Grade','grade'),('ElecPaymentInfo','Payment','payment'),('ElecPaymentInfo','Cost','cost'),('ElecPaymentInfo','ElecUsage','elecUsage'),('ElecPaymentInfo','ElecBalance','balance'),('ManagerInfo','ManagerID','mID'),('ManagerInfo','Name','mName'),('ManagerInfo','Phone','mPhone'),('ManagerInfo','password','password'),('RoomInfo','RoomID','rID'),('RoomInfo','BuildingID','bID'),('RoomInfo','Capacity','rCapacity'),('RoomInfo','elecUsage','elecUsage'),('RoomInfo','elecBalance','elecBalance'),('RoomInfo','elecAvailable','elecAvailable'),('RoomInfo','waterUsage','waterUsage'),('RoomInfo','waterBalance','waterBalance'),('RoomInfo','waterAvailable','waterAvailable'),('RoomInfo','Available','rAvailable'),('RoomInfo','Description','description'),('RoomPaymentInfo','PayDate','payDate'),('RoomPaymentInfo','SetDate','setDate'),('RoomPaymentInfo','RoomId','rID'),('RoomPaymentInfo','BuildingId','bID'),('RoomPaymentInfo','StudentId','sID'),('RoomPaymentInfo','PaymentID','rpID'),('RoomPaymentInfo','Amount','amount'),('RoomPaymentInfo','RoomCapacity','rCapacity'),('StudentInfo','StudentID','sID'),('StudentInfo','Name','sName'),('StudentInfo','Phone','sPhone'),('StudentInfo','Degree','dID'),('StudentInfo','Major','majID'),('StudentInfo','Grade','grade'),('StudentInfo','classID','class'),('StudentInfo','Sex','sex'),('StudentInfo','password','password'),('StudentInfo','RoomID','rID'),('StudentInfo','BuildingID','bID'),('WaterPaymentInfo','PayDate','payDate'),('WaterPaymentInfo','SetDate','setDate'),('WaterPaymentInfo','RoomId','rID'),('WaterPaymentInfo','BuildingId','bID'),('WaterPaymentInfo','StudentId','sID'),('WaterPaymentInfo','PaymentID','wpID'),('WaterPaymentInfo','Grade','grade'),('WaterPaymentInfo','Payment','payment'),('WaterPaymentInfo','Cost','cost'),('WaterPaymentInfo','WaterUsage','waterUsage'),('WaterPaymentInfo','WaterBalance','balance');
/*!40000 ALTER TABLE `FieldNameTransformer` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-16 15:35:16
