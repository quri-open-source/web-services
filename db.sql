-- MySQL dump 10.13  Distrib 8.0.42, for Linux (x86_64)
--
-- Host: localhost    Database: quri-teelab
-- ------------------------------------------------------
-- Server version	8.0.42-0ubuntu0.24.04.1

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
-- Table structure for table `customer_analytics`
--

DROP TABLE IF EXISTS `customer_analytics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_analytics` (
  `id` varchar(191) NOT NULL,
  `blueprints` int DEFAULT NULL,
  `completed` int DEFAULT NULL,
  `designed_garments` int DEFAULT NULL,
  `total_projects` int DEFAULT NULL,
  `user_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--

DROP TABLE IF EXISTS `fulfillment_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fulfillment_items` (
  `value` binary(16) NOT NULL,
  `fulfillment_id` binary(16) NOT NULL,
  `product_id` binary(16) NOT NULL,
  `quantity` int NOT NULL,
  `status` enum('CANCELLED','PENDING','RECEIVED','SHIPPED') NOT NULL,
  PRIMARY KEY (`value`),
  KEY `idx_fulfillment_items_product_id` (`product_id`),
  KEY `FKahu1didgipge2mgkivry1mywv` (`fulfillment_id`),
  CONSTRAINT `FKahu1didgipge2mgkivry1mywv` FOREIGN KEY (`fulfillment_id`) REFERENCES `fulfillments` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `fulfillments`
--

DROP TABLE IF EXISTS `fulfillments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fulfillments` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `order_id` binary(16) NOT NULL,
  `received_date` datetime DEFAULT NULL,
  `shipped_date` datetime DEFAULT NULL,
  `status` enum('CANCELLED','DELIVERED','PENDING','PROCESSING','SHIPPED') NOT NULL,
  `manufacturer_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_fulfillments_order_id` (`order_id`),
  KEY `idx_fulfillments_manufacturer_id` (`manufacturer_id`),
  CONSTRAINT `FKt8p29nch2cgjibwa0c8hmnxnd` FOREIGN KEY (`manufacturer_id`) REFERENCES `manufacturers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `items`
--

DROP TABLE IF EXISTS `items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `items` (
  `id` binary(16) NOT NULL,
  `product_id` binary(16) DEFAULT NULL,
  `quantity` int NOT NULL,
  `order_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5pqstbxcemvs4suso34smhr1n` (`order_id`),
  CONSTRAINT `FK5pqstbxcemvs4suso34smhr1n` FOREIGN KEY (`order_id`) REFERENCES `order_processings` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `layers`
--

DROP TABLE IF EXISTS `layers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `layers` (
  `layer_type` varchar(31) NOT NULL,
  `layer_id` binary(16) NOT NULL,
  `default_opacity` float NOT NULL,
  `default_x_position` int NOT NULL,
  `default_y_position` int NOT NULL,
  `default_z_index` int NOT NULL,
  `created_at` datetime NOT NULL,
  `is_visible` bit(1) NOT NULL,
  `opacity` float NOT NULL,
  `type` enum('IMAGE','TEXT') NOT NULL,
  `updated_at` datetime NOT NULL,
  `x` int NOT NULL,
  `y` int NOT NULL,
  `z` int NOT NULL,
  `height` float DEFAULT NULL,
  `image_url` varchar(191) DEFAULT NULL,
  `width` float DEFAULT NULL,
  `font_color` varchar(191) DEFAULT NULL,
  `font_family` varchar(191) DEFAULT NULL,
  `font_size` int DEFAULT NULL,
  `is_bold` bit(1) DEFAULT NULL,
  `is_italic` bit(1) DEFAULT NULL,
  `is_underlined` bit(1) DEFAULT NULL,
  `text` varchar(191) DEFAULT NULL,
  `project_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`layer_id`),
  KEY `FKcqb7tlhbwy2abk71j80y20wq0` (`project_id`),
  CONSTRAINT `FKcqb7tlhbwy2abk71j80y20wq0` FOREIGN KEY (`project_id`) REFERENCES `projects` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manufacturer_analytics`
--

DROP TABLE IF EXISTS `manufacturer_analytics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `manufacturer_analytics` (
  `id` varchar(191) NOT NULL,
  `avg_fulfillment_time_days` double NOT NULL,
  `pending_fulfillments` int NOT NULL,
  `produced_projects` int NOT NULL,
  `total_orders_received` int NOT NULL,
  `user_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manufacturers`
--

DROP TABLE IF EXISTS `manufacturers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `manufacturers` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `address_line` varchar(200) NOT NULL,
  `city` varchar(100) NOT NULL,
  `country` varchar(100) NOT NULL,
  `state` varchar(100) NOT NULL,
  `zip_code` varchar(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK4awvjyycsdogd3xop7jt1iprf` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order_processings`
--

DROP TABLE IF EXISTS `order_processings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_processings` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `status` enum('COMPLETED','PROCESSING') DEFAULT NULL,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_likes`
--

DROP TABLE IF EXISTS `product_likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_likes` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `product_id` binary(16) NOT NULL,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_product_user_like` (`product_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `garment_color` varchar(191) NOT NULL,
  `garment_gender` varchar(191) NOT NULL,
  `garment_size` varchar(191) NOT NULL,
  `like_count` bigint NOT NULL,
  `price_amount` decimal(38,2) NOT NULL,
  `price_currency` varchar(3) NOT NULL,
  `project_id` varchar(191) NOT NULL,
  `project_preview_url` varchar(191) DEFAULT NULL,
  `project_title` varchar(191) NOT NULL,
  `project_user_id` binary(16) NOT NULL,
  `status` enum('AVAILABLE','DISCONTINUED','OUT_OF_STOCK','UNAVAILABLE') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `projects`
--

DROP TABLE IF EXISTS `projects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `projects` (
  `project_id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `garment_color` enum('BLACK','BLUE','CYAN','DARK_YELLOW','GRAY','GREEN','LIGHT_BLUE','LIGHT_GRAY','LIGHT_GREEN','LIGHT_PURPLE','PINK','PURPLE','RED','SKY_BLUE','WHITE','YELLOW') DEFAULT NULL,
  `garment_gender` enum('KIDS','MEN','UNISEX','WOMEN') DEFAULT NULL,
  `garment_size` enum('L','M','S','XL','XS','XXL') DEFAULT NULL,
  `preview_url` varchar(191) DEFAULT NULL,
  `status` enum('BLUEPRINT','GARMENT','TEMPLATE') DEFAULT NULL,
  `title` varchar(191) DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `user_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` enum('ROLE_ADMIN','ROLE_INSTRUCTOR','ROLE_MANUFACTURER','ROLE_USER') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` binary(16) NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`),
  CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` binary(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `password` varchar(120) NOT NULL,
  `username` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'quri-teelab'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-06 10:51:37