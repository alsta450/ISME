-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: fitness_center
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `room_number` int NOT NULL,
  `street` varchar(30) NOT NULL,
  `city` varchar(30) NOT NULL,
  `zip` char(4) NOT NULL,
  `name` varchar(15) DEFAULT NULL,
  `interior` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`room_number`,`street`,`city`,`zip`),
  KEY `street` (`street`,`city`,`zip`),
  CONSTRAINT `room_ibfk_1` FOREIGN KEY (`street`, `city`, `zip`) REFERENCES `branch` (`street`, `city`, `zip`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (1,'Autumn Leaf','Itacarambi','1178','Spinning Room','Spinning bikes'),(1,'Forest Dale','Las Lajas','9820','Spinning Room','Spinning bikes'),(1,'Forest Run','Pangawaren','8832','Spinning Room','Spinning bikes'),(1,'Harper','Santo Rosario','4412','Spinning Room','Spinning bikes'),(1,'Lakewood','Nagbukel','9359','Spinning Room','Spinning bikes'),(1,'Ludington','Kisesa','6251','Spinning Room','Spinning bikes'),(1,'Manufacturers','Beicang','4721','Spinning Room','Spinning bikes'),(1,'Mockingbird','Klwów','7579','Spinning Room','Spinning bikes'),(1,'Ruskin','Shuidong','3219','Spinning Room','Spinning bikes'),(1,'Sauthoff','La Esperanza','9281','Spinning Room','Spinning bikes');
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-31 13:33:50
