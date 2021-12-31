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
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `svnr` bigint NOT NULL,
  `hours` int DEFAULT NULL,
  `wage` int DEFAULT NULL,
  `qualification` varchar(30) DEFAULT NULL,
  `street` varchar(30) DEFAULT NULL,
  `city` varchar(30) DEFAULT NULL,
  `zip` char(4) DEFAULT NULL,
  PRIMARY KEY (`svnr`),
  KEY `street` (`street`,`city`,`zip`),
  CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`svnr`) REFERENCES `person` (`svnr`) ON DELETE CASCADE,
  CONSTRAINT `employee_ibfk_2` FOREIGN KEY (`street`, `city`, `zip`) REFERENCES `branch` (`street`, `city`, `zip`) ON DELETE CASCADE,
  CONSTRAINT `employee_chk_1` CHECK ((`wage` > 900))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1139426602,13,3909,'Functional Strength Trainer','Manufacturers','Beicang','4721'),(1176078954,28,3539,'Functional Strength Trainer','Forest Run','Pangawaren','8832'),(1211887829,32,4021,'Functional Strength Trainer','Lakewood','Nagbukel','9359'),(1447156115,17,3907,'Fitness Trainer','Autumn Leaf','Itacarambi','1178'),(1716131560,36,4766,'Fitness Trainer','Forest Run','Pangawaren','8832'),(1727502081,34,1856,'Personal Trainer','Autumn Leaf','Itacarambi','1178'),(1753688129,37,4600,'Pilates Trainer','Forest Run','Pangawaren','8832'),(1802979969,30,3630,'Pilates Trainer','Ruskin','Shuidong','3219'),(1815152848,34,3070,'Yoga Trainer','Ludington','Kisesa','6251'),(2005205952,33,2318,'Yoga Trainer','Forest Dale','Las Lajas','9820'),(2034321985,28,2086,'Functional Strength Trainer','Sauthoff','La Esperanza','9281'),(2287669384,27,4049,'Yoga Trainer','Autumn Leaf','Itacarambi','1178'),(2299437815,27,4169,'Functional Strength Trainer','Harper','Santo Rosario','4412'),(2426744001,39,3049,'Functional Strength Trainer','Manufacturers','Beicang','4721'),(2427737371,27,3492,'Functional Strength Trainer','Forest Dale','Las Lajas','9820'),(2910096698,31,1531,'Fitness Trainer','Ruskin','Shuidong','3219'),(2932460517,28,2994,'Functional Strength Trainer','Autumn Leaf','Itacarambi','1178'),(2962815794,14,3755,'Yoga Trainer','Ruskin','Shuidong','3219'),(3083859828,22,2239,'Functional Strength Trainer','Ruskin','Shuidong','3219'),(3177637248,32,2284,'Yoga Trainer','Forest Run','Pangawaren','8832'),(3286463383,32,2302,'Personal Trainer','Harper','Santo Rosario','4412'),(3413565708,11,1290,'Functional Strength Trainer','Manufacturers','Beicang','4721'),(3487736466,12,3859,'Fitness Trainer','Autumn Leaf','Itacarambi','1178'),(3490130704,22,4515,'Personal Trainer','Forest Dale','Las Lajas','9820'),(3493877954,26,1176,'Fitness Trainer','Autumn Leaf','Itacarambi','1178'),(3565511081,36,2871,'Fitness Trainer','Manufacturers','Beicang','4721'),(3619158679,37,2733,'Fitness Trainer','Sauthoff','La Esperanza','9281'),(3744369570,40,3393,'Fitness Trainer','Forest Dale','Las Lajas','9820'),(3800373047,19,2552,'Yoga Trainer','Forest Run','Pangawaren','8832'),(3802423755,36,4540,'Functional Strength Trainer','Lakewood','Nagbukel','9359'),(3834848959,28,1478,'Functional Strength Trainer','Autumn Leaf','Itacarambi','1178'),(3894188425,39,3867,'Personal Trainer','Sauthoff','La Esperanza','9281'),(3949567860,39,2397,'Fitness Trainer','Ludington','Kisesa','6251'),(3988556157,37,4153,'Yoga Trainer','Manufacturers','Beicang','4721'),(3994323116,35,1839,'Fitness Trainer','Ludington','Kisesa','6251'),(4010968005,15,2924,'Functional Strength Trainer','Lakewood','Nagbukel','9359'),(4076638987,20,4218,'Yoga Trainer','Ludington','Kisesa','6251'),(4114881912,33,2122,'Functional Strength Trainer','Mockingbird','Klwów','7579'),(4150771534,11,1735,'Personal Trainer','Mockingbird','Klwów','7579'),(4366297969,34,3227,'Personal Trainer','Manufacturers','Beicang','4721'),(4382224323,11,2826,'Pilates Trainer','Lakewood','Nagbukel','9359'),(4453730812,18,3467,'Yoga Trainer','Manufacturers','Beicang','4721'),(4541294778,24,2901,'Yoga Trainer','Harper','Santo Rosario','4412'),(4710858725,11,1576,'Yoga Trainer','Harper','Santo Rosario','4412'),(4823169456,19,4869,'Yoga Trainer','Mockingbird','Klwów','7579'),(4825408378,18,2014,'Yoga Trainer','Ruskin','Shuidong','3219'),(4879762047,38,4637,'Pilates Trainer','Ludington','Kisesa','6251'),(5133939561,14,1912,'Functional Strength Trainer','Forest Dale','Las Lajas','9820'),(5143189231,15,4738,'Functional Strength Trainer','Ruskin','Shuidong','3219'),(5191656285,35,2395,'Fitness Trainer','Harper','Santo Rosario','4412'),(5212047736,20,3379,'Yoga Trainer','Sauthoff','La Esperanza','9281'),(5231698646,17,2474,'Functional Strength Trainer','Sauthoff','La Esperanza','9281'),(5236522852,18,2161,'Fitness Trainer','Sauthoff','La Esperanza','9281'),(5252590761,10,2372,'Functional Strength Trainer','Mockingbird','Klwów','7579'),(5324474200,37,2389,'Personal Trainer','Forest Run','Pangawaren','8832'),(5482951499,16,1940,'Functional Strength Trainer','Autumn Leaf','Itacarambi','1178'),(5507055836,19,1142,'Personal Trainer','Ludington','Kisesa','6251'),(5545058583,23,1522,'Personal Trainer','Manufacturers','Beicang','4721'),(5680893638,34,4735,'Personal Trainer','Sauthoff','La Esperanza','9281'),(5877425294,14,2421,'Fitness Trainer','Manufacturers','Beicang','4721'),(6014722777,36,2110,'Fitness Trainer','Mockingbird','Klwów','7579'),(6120888541,14,1267,'Yoga Trainer','Ludington','Kisesa','6251'),(6302744462,25,1398,'Personal Trainer','Forest Dale','Las Lajas','9820'),(6453605402,25,3955,'Personal Trainer','Harper','Santo Rosario','4412'),(6637962958,39,3447,'Pilates Trainer','Ruskin','Shuidong','3219'),(6666566772,39,1773,'Pilates Trainer','Harper','Santo Rosario','4412'),(6668106042,15,3177,'Yoga Trainer','Harper','Santo Rosario','4412'),(6765973957,33,4833,'Personal Trainer','Forest Run','Pangawaren','8832'),(6801703860,32,4319,'Fitness Trainer','Lakewood','Nagbukel','9359'),(7085666736,24,1342,'Personal Trainer','Lakewood','Nagbukel','9359'),(7298423589,38,4968,'Personal Trainer','Autumn Leaf','Itacarambi','1178'),(7315195893,33,2585,'Personal Trainer','Harper','Santo Rosario','4412'),(7365702236,11,3556,'Functional Strength Trainer','Sauthoff','La Esperanza','9281'),(7518142082,30,3651,'Personal Trainer','Forest Dale','Las Lajas','9820'),(7802272564,32,2473,'Personal Trainer','Mockingbird','Klwów','7579'),(7805590668,16,1104,'Pilates Trainer','Lakewood','Nagbukel','9359'),(7897669934,24,1539,'Functional Strength Trainer','Forest Run','Pangawaren','8832'),(7964075649,12,4249,'Pilates Trainer','Ludington','Kisesa','6251'),(7968046452,27,1074,'Personal Trainer','Harper','Santo Rosario','4412'),(8008242188,26,3406,'Pilates Trainer','Ruskin','Shuidong','3219'),(8019383505,26,4237,'Pilates Trainer','Sauthoff','La Esperanza','9281'),(8128505099,18,1974,'Personal Trainer','Ludington','Kisesa','6251'),(8134772758,31,1095,'Pilates Trainer','Forest Dale','Las Lajas','9820'),(8168092942,34,3409,'Functional Strength Trainer','Forest Run','Pangawaren','8832'),(8334736400,22,3933,'Yoga Trainer','Mockingbird','Klwów','7579'),(8609838243,38,2124,'Pilates Trainer','Ruskin','Shuidong','3219'),(8838407234,37,4364,'Personal Trainer','Autumn Leaf','Itacarambi','1178'),(8934741937,16,4807,'Yoga Trainer','Lakewood','Nagbukel','9359'),(9109817093,10,4266,'Functional Strength Trainer','Sauthoff','La Esperanza','9281'),(9110547589,34,2951,'Fitness Trainer','Mockingbird','Klwów','7579'),(9126861784,31,3374,'Pilates Trainer','Mockingbird','Klwów','7579'),(9167291328,19,1950,'Fitness Trainer','Lakewood','Nagbukel','9359'),(9189816144,33,2251,'Pilates Trainer','Mockingbird','Klwów','7579'),(9370753915,33,1465,'Pilates Trainer','Manufacturers','Beicang','4721'),(9428950525,28,4946,'Personal Trainer','Forest Run','Pangawaren','8832'),(9435553114,32,4729,'Functional Strength Trainer','Ludington','Kisesa','6251'),(9584678775,34,2789,'Yoga Trainer','Lakewood','Nagbukel','9359'),(9927009021,17,1761,'Personal Trainer','Ruskin','Shuidong','3219'),(9967217826,14,1777,'Pilates Trainer','Forest Dale','Las Lajas','9820'),(9982563116,32,1013,'Functional Strength Trainer','Forest Dale','Las Lajas','9820');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
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
