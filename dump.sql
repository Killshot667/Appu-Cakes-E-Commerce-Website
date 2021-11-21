-- MySQL dump 10.13  Distrib 8.0.27, for Linux (x86_64)
--
-- Host: localhost    Database: appu1
-- ------------------------------------------------------
-- Server version	8.0.27-0ubuntu0.20.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Article`
--

DROP TABLE IF EXISTS `Article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Article` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `date` date NOT NULL,
  `content` varchar(10000) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Article`
--

LOCK TABLES `Article` WRITE;
/*!40000 ALTER TABLE `Article` DISABLE KEYS */;
INSERT INTO `Article` VALUES (15,'How to Start a Cake Business','2021-11-15','<div><br></div><div>You often puzzled by these above factors from when where you can buy the best types of equipment for bakery business, how much cost involved to start this business? how to make a business plan? How to give training to staff? how to do marketing?</div><div>Chef Bakers, one of the Best Cake Shops in Bangalore having a solution to all the above questions. Chef bakers having the best and successful franchisee models for the cake business and currently operating 47 franchisees in Bangalore and Hyderabad, and also planning to expand more franchisee units primarily in Bangalore, Hyderabad, and Chennai.</div><div>Chef bakers were founded in the year 2007 by 3 Young Professionals from the Hotel Industry-a Pastry Chef and 2 F&amp;B Managers, with a passion to open a world-class bakery at affordable pricing. Chef bakers ensure and giving utmost importance to the quality of the Product and Customer Service. Their detailed attention and the right balance between traditional and innovative baking methods and intuitive customer service gives an edge over other Bakeries. They give attention to each franchisee to serve fresh and prepackaged Bread, Butter Croissants, Fresh-Fruit pies, Signature Cakes, Cupcakes, Cheese Cakes Shape Cakes, Birthday Cakes, Wedding Cakes, Coffees, and other drinks.</div><div></div><div><img src=\"https://www.chefbakers.com/article_images/ice-cream-dripping-cake66562.png\" class=\"img-responsive\" style=\"border: 0px; display: block; max-width: 100%; height: auto;\"></div><div></div><div>The following is the overview of the Chef Bakers Franchisee Model:</div><div><br></div>'),(19,'new article','2021-11-15','<div><div>&nbsp; &nbsp;&nbsp;<img src=\"https://www.chefbakers.com/userfiles/image/Online%20Cake%20Shop%20in%20Bangalore.jpg\" width=\"350\" height=\"350\" alt=\"Online Cake Shop in Bangalore\" title=\"Online Cake Shop in Bangalore\" style=\"border: 0px;\"></div><div><em><span style=\"font-size: large;\"><span style=\"font-family: &quot;Times New Roman&quot;;\">Busy with your daily routine and wish to order a quality cake online? Come to Chef Bakers website and get the best cake delivered at your door step. At Chef Bakers we have covered a countless variety of cakes for various occasions. We have birthday cakes, anniversary cakes, festival cakes, 3D cakes, digital cakes and many more to choose from. Your choice might end by our variety will not. Surf through the different pages of the website to witness the different variety of cake at each page you go through.&nbsp;</span></span></em></div><div><em><span style=\"font-size: large;\"><span style=\"font-family: &quot;Times New Roman&quot;;\">We are one of its kinds of an online cake shop in Bangalore that deals with a large variety of cakes. We not only boast of having a number of cakes but we provide hassle free&nbsp;<span style=\"font-weight: 700;\"><a href=\"https://www.chefbakers.com/\" style=\"color: rgb(51, 122, 183);\">online cake delivery in Bangalore</a></span>. We have countless happy customers and we would like to add your name to it too. Chef Bakers Bangalore cake shop is quite popular and we have gained this popularity by producing the finest quality cakes for our customers. We take pride to be a part of your happiness by putting each and every ingredient in the cake carefully and with great love. Your occasion is our honor!!</span></span></em></div></div>'),(20,'another one','2021-11-15','<div><div>&nbsp; &nbsp;&nbsp;<img src=\"https://www.chefbakers.com/userfiles/image/Online%20Cake%20Shop%20in%20Bangalore.jpg\" width=\"350\" height=\"350\" alt=\"Online Cake Shop in Bangalore\" title=\"Online Cake Shop in Bangalore\" style=\"border: 0px;\"></div><div><em><span style=\"font-size: large;\"><span style=\"font-family: &quot;Times New Roman&quot;;\">Busy with your daily routine and wish to order a quality cake online? Come to Chef Bakers website and get the best cake delivered at your door step. At Chef Bakers we have covered a countless variety of cakes for various occasions. We have birthday cakes, anniversary cakes, festival cakes, 3D cakes, digital cakes and many more to choose from. Your choice might end by our variety will not. Surf through the different pages of the website to witness the different variety of cake at each page you go through.&nbsp;</span></span></em></div><div><em><span style=\"font-size: large;\"><span style=\"font-family: &quot;Times New Roman&quot;;\">We are one of its kinds of an online cake shop in Bangalore that deals with a large variety of cakes. We not only boast of having a number of cakes but we provide hassle free&nbsp;<span style=\"font-weight: 700;\"><a href=\"https://www.chefbakers.com/\" style=\"color: rgb(51, 122, 183);\">online cake delivery in Bangalore</a></span>. We have countless happy customers and we would like to add your name to it too. Chef Bakers Bangalore cake shop is quite popular and we have gained this popularity by producing the finest quality cakes for our customers. We take pride to be a part of your happiness by putting each and every ingredient in the cake carefully and with great love. Your occasion is our honor!!</span></span></em></div></div>');
/*!40000 ALTER TABLE `Article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Cart`
--

DROP TABLE IF EXISTS `Cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Cart` (
  `customerid` int NOT NULL,
  `productid` int NOT NULL,
  `quantity` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`customerid`,`productid`),
  KEY `fk_Cart_1_idx` (`productid`),
  CONSTRAINT `fk_Cart_1` FOREIGN KEY (`productid`) REFERENCES `Product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Cart_2` FOREIGN KEY (`customerid`) REFERENCES `User` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cart`
--

LOCK TABLES `Cart` WRITE;
/*!40000 ALTER TABLE `Cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `Cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Category`
--

DROP TABLE IF EXISTS `Category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `discount` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Category`
--

LOCK TABLES `Category` WRITE;
/*!40000 ALTER TABLE `Category` DISABLE KEYS */;
INSERT INTO `Category` VALUES (2,'Birthday Cake',40),(3,'Exhibition Cake',0),(7,'Cupcake',20),(8,'Sponge Cake',15),(11,'beautiful cakes',0);
/*!40000 ALTER TABLE `Category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Order`
--

DROP TABLE IF EXISTS `Order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customerid` int NOT NULL,
  `order_date` date NOT NULL,
  `payment_date` date DEFAULT NULL,
  `delivery_date` date DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `cost` int NOT NULL,
  `mode` varchar(45) NOT NULL DEFAULT 'Cash',
  PRIMARY KEY (`id`),
  KEY `fk_Order_1_idx` (`customerid`),
  CONSTRAINT `fk_Order_1` FOREIGN KEY (`customerid`) REFERENCES `User` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Order`
--

LOCK TABLES `Order` WRITE;
/*!40000 ALTER TABLE `Order` DISABLE KEYS */;
INSERT INTO `Order` VALUES (12,12,'2021-11-15','2021-11-15',NULL,1,657,'Net Banking'),(13,12,'2021-11-15','2021-11-15',NULL,1,2140,'Cash'),(14,12,'2021-11-15','2021-11-15','2021-11-15',2,1087,'Net Banking'),(15,12,'2021-11-15','2021-11-15','2021-11-15',2,855,'Gpay');
/*!40000 ALTER TABLE `Order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OrderItem`
--

DROP TABLE IF EXISTS `OrderItem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `OrderItem` (
  `orderid` int NOT NULL,
  `productid` int NOT NULL,
  `quantity` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`orderid`,`productid`),
  KEY `fk_OrderItem_2_idx` (`productid`),
  CONSTRAINT `fk_OrderItem_1` FOREIGN KEY (`orderid`) REFERENCES `Order` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_OrderItem_2` FOREIGN KEY (`productid`) REFERENCES `Product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrderItem`
--

LOCK TABLES `OrderItem` WRITE;
/*!40000 ALTER TABLE `OrderItem` DISABLE KEYS */;
INSERT INTO `OrderItem` VALUES (12,6,1),(13,9,1),(14,6,1),(14,8,2),(15,8,2);
/*!40000 ALTER TABLE `OrderItem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Product`
--

DROP TABLE IF EXISTS `Product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `categoryid` int NOT NULL,
  `discount` int NOT NULL DEFAULT '0',
  `name` varchar(45) NOT NULL,
  `description` varchar(1000) NOT NULL,
  `price` int NOT NULL,
  `availability` int NOT NULL DEFAULT '1',
  `prodImage` varchar(45) NOT NULL DEFAULT 'chocolate.jpg',
  PRIMARY KEY (`id`),
  KEY `fk_Product_1_idx` (`categoryid`),
  CONSTRAINT `fk_Product_1` FOREIGN KEY (`categoryid`) REFERENCES `Category` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Product`
--

LOCK TABLES `Product` WRITE;
/*!40000 ALTER TABLE `Product` DISABLE KEYS */;
INSERT INTO `Product` VALUES (6,2,10,'Beauty Chocolate','This is my favorite homemade chocolate cake recipe. With a super moist crumb and fudgy, yet light texture, this chocolate cake recipe will soon be your favorite too. Top with chocolate buttercream and chocolate chips for 3x the chocolate flavor.',400,2,'chocolate.jpg'),(8,3,5,'Lion Face','An exhibition type cake famous among children. The lion face is tasty visually as well in the mouth.',900,3,'lion.jpg'),(9,3,0,'Water Rusher','A watermelon shaped cake.',750,8,'watermelon.jpeg'),(11,3,10,'Oceanicity','Beautiful yet tasty. This exhibition is one in a million.',1000,2,'ocean.jpg'),(14,8,10,'Spongy Sponge','A new and improved sponge cake',550,20,'sponge.jpg'),(15,2,12,'New Cake Strawberry','very good cake',900,7,'strawberry.jpg');
/*!40000 ALTER TABLE `Product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `User` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(200) NOT NULL,
  `role` varchar(45) NOT NULL,
  `contactNo` varchar(45) NOT NULL,
  `address` varchar(200) NOT NULL,
  `city` varchar(45) NOT NULL,
  `houseno` varchar(45) NOT NULL,
  `pincode` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (11,'vicky','shabari.snair.cd.cse19@itbhu.ac.in','$2a$10$GsENmdEyYztK3vFkpm.cBuMRJn2HmMlVCns47Q7C03kj2D2dT0jny','ROLE_ADMIN','9449080504','asaf afaf\r\na','varanasi','315','560047'),(12,'shabari','shabarisnair123@gmail.com','$2a$10$nkaRWTNUNrqGw5EZmBuAj./zySP7r4SeeyQawV.0fbP.x0SDtcI4W','ROLE_USER','1234567890','Mukund Apts, Palm Grove Rd, Victoria Layout, Bangalore','bangalore','Flat no. 315','560042');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-11-21 16:48:43
