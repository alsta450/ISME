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
-- Table structure for table `fitness_equipment`
--

DROP TABLE IF EXISTS `fitness_equipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fitness_equipment` (
  `description` varchar(30) NOT NULL,
  `muscle_group` varchar(25) DEFAULT NULL,
  `fitness_type` varchar(10) DEFAULT NULL,
  `street` varchar(30) DEFAULT NULL,
  `city` varchar(30) DEFAULT NULL,
  `zip` char(4) DEFAULT NULL,
  PRIMARY KEY (`description`),
  KEY `street` (`street`,`city`,`zip`),
  CONSTRAINT `fitness_equipment_ibfk_1` FOREIGN KEY (`street`, `city`, `zip`) REFERENCES `branch` (`street`, `city`, `zip`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fitness_equipment`
--

LOCK TABLES `fitness_equipment` WRITE;
/*!40000 ALTER TABLE `fitness_equipment` DISABLE KEYS */;
INSERT INTO `fitness_equipment` VALUES ('Bench Press','Chest','Weights','Autumn Leaf','Itacarambi','1178'),('Deadlift','Back','Weights','Sauthoff','La Esperanza','9281'),('Hip Thrust','Glutes','Weights','Ruskin','Shuidong','3219'),('Leg Press','Legs','Weights','Ludington','Kisesa','6251'),('Shoulder Press','Shoulder','Weights','Forest Dale','Las Lajas','9820'),('Spinning Bike','Legs','Cardio','Lakewood','Nagbukel','9359'),('Squad Rack','Legs','Weights','Harper','Santo Rosario','4412'),('Stairmaster','Legs','Cardio','Forest Run','Pangawaren','8832'),('Tips Tower ','Triceps','Weights','Mockingbird','Klwów','7579'),('Treadmill','Legs','Cardio','Manufacturers','Beicang','4721');
/*!40000 ALTER TABLE `fitness_equipment` ENABLE KEYS */;
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
