/*
SQLyog Community v13.1.7 (64 bit)
MySQL - 8.0.23 : Database - acfcapstoneproject
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`acfcapstoneproject` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `acfcapstoneproject`;

/*Table structure for table `account` */

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` date DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `employee_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1kec5bwba2rl0j8garlarwe3d` (`employee_id`),
  CONSTRAINT `FK1kec5bwba2rl0j8garlarwe3d` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `account` */

insert  into `account`(`id`,`created_by`,`created_date`,`deleted`,`modified_by`,`modified_date`,`password`,`status`,`username`,`employee_id`) values 
(1,'truongtv','2021-12-10','\0','truongtv','2021-12-10','$2a$10$3KCn7pg6MWSMrWoN30qj1uENjGhHDLYQK1tmjDs1mEg2cV3VtTa.e','','truongtv',1),
(2,'truongtv','2021-12-10','\0','truongtv','2021-12-10','$2a$10$3KCn7pg6MWSMrWoN30qj1uENjGhHDLYQK1tmjDs1mEg2cV3VtTa.e','','ngoclt',2);

/*Table structure for table `account_roles` */

DROP TABLE IF EXISTS `account_roles`;

CREATE TABLE `account_roles` (
  `account_id` bigint NOT NULL,
  `roles_id` bigint NOT NULL,
  KEY `FK70s9enq5d1oywl7v8vis5ke5w` (`roles_id`),
  KEY `FKtp61eta5i06bug3w1qr6286uf` (`account_id`),
  CONSTRAINT `FK70s9enq5d1oywl7v8vis5ke5w` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKtp61eta5i06bug3w1qr6286uf` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `account_roles` */

insert  into `account_roles`(`account_id`,`roles_id`) values 
(1,1),
(1,2),
(1,3),
(2,2),
(2,1);

/*Table structure for table `advace_salary` */

DROP TABLE IF EXISTS `advace_salary`;

CREATE TABLE `advace_salary` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` date DEFAULT NULL,
  `accept` varchar(255) DEFAULT NULL,
  `advace_salary` varchar(255) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `content` text,
  `date_accept` date DEFAULT NULL,
  `id_employee_accept` bigint DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `employee_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKaei4ytudikyg3mer5oqew7aj6` (`employee_id`),
  CONSTRAINT `FKaei4ytudikyg3mer5oqew7aj6` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `advace_salary` */

/*Table structure for table `bonus_penalty` */

DROP TABLE IF EXISTS `bonus_penalty`;

CREATE TABLE `bonus_penalty` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` date DEFAULT NULL,
  `bonus` bit(1) DEFAULT NULL,
  `effective_date` date DEFAULT NULL,
  `money` varchar(255) DEFAULT NULL,
  `reason` text,
  `status` bit(1) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `bonus_penalty` */

/*Table structure for table `company` */

DROP TABLE IF EXISTS `company`;

CREATE TABLE `company` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` date DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `company` */

/*Table structure for table `contact` */

DROP TABLE IF EXISTS `contact`;

CREATE TABLE `contact` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` date DEFAULT NULL,
  `date_finish` date DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `note` text,
  `number_finish` varchar(255) DEFAULT NULL,
  `status_done` int DEFAULT NULL,
  `total_money` varchar(255) DEFAULT NULL,
  `company_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpgbqt6dnai52x55o1qvsx1dfn` (`company_id`),
  CONSTRAINT `FKpgbqt6dnai52x55o1qvsx1dfn` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `contact` */

/*Table structure for table `contact_money` */

DROP TABLE IF EXISTS `contact_money`;

CREATE TABLE `contact_money` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` date DEFAULT NULL,
  `date` date DEFAULT NULL,
  `money` varchar(255) DEFAULT NULL,
  `contact_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlv7epiw4ba38uk7qot4btk1i5` (`contact_id`),
  CONSTRAINT `FKlv7epiw4ba38uk7qot4btk1i5` FOREIGN KEY (`contact_id`) REFERENCES `contact` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `contact_money` */

/*Table structure for table `employee` */

DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` date DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `gender` bit(1) DEFAULT NULL,
  `nation` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `salary` bigint DEFAULT NULL,
  `file_id` varchar(255) DEFAULT NULL,
  `position_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2r9x573b79fx4d168flv7bquu` (`file_id`),
  KEY `FKbc8rdko9o9n1ri9bpdyxv3x7i` (`position_id`),
  CONSTRAINT `FK2r9x573b79fx4d168flv7bquu` FOREIGN KEY (`file_id`) REFERENCES `file` (`id`),
  CONSTRAINT `FKbc8rdko9o9n1ri9bpdyxv3x7i` FOREIGN KEY (`position_id`) REFERENCES `position` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `employee` */

insert  into `employee`(`id`,`created_by`,`created_date`,`deleted`,`modified_by`,`modified_date`,`address`,`dob`,`email`,`full_name`,`gender`,`nation`,`phone`,`salary`,`file_id`,`position_id`) values 
(1,'truongtv','2021-12-10','\0','truongtv','2021-12-10','Nam Định','2021-12-10','truongtvhe130302@fpt.edu.vn','Trần Vũ Trường','','Kinh','0382945665',1500000,'711f426f-37ac-493e-b79c-8f429288c8a4',1),
(2,'truongtv','2021-12-10','\0','truongtv','2021-12-10','Nam Định','2021-12-10','ngoclthe131028@fpt.edu.vn','Lê Thị Ngọc','\0','Kinh','0382945665',1500000,NULL,2),
(3,'truongtv','2021-12-10','\0','truongtv','2021-12-10','Nam Định','2021-12-10','anhlhhe130300@fpt.edu.vn','Lê Hoàng Anh','','Kinh','0382945665',1500000,NULL,2),
(4,'truongtv','2021-12-10','\0','truongtv','2021-12-10','Nam Định','2021-12-10','minhtbhe130308@fpt.edu.vn','Trần Bình Minh','','Kinh','0382945665',1500000,NULL,2),
(5,'truongtv','2021-12-10','\0','truongtv','2021-12-10','Nam Định','2021-12-10','phongnhlhe130650@fpt.edu.vn','Nguyễn Hữu Lâm Phong','','Kinh','0382945665',1500000,NULL,2);

/*Table structure for table `employee_bonus_penalties` */

DROP TABLE IF EXISTS `employee_bonus_penalties`;

CREATE TABLE `employee_bonus_penalties` (
  `bonus_penalty_id` bigint NOT NULL,
  `employee_id` bigint NOT NULL,
  KEY `FK8mq3xk535dfnxag0br0bi0rmh` (`employee_id`),
  KEY `FKs0hv8a17k556regux0nvttl2m` (`bonus_penalty_id`),
  CONSTRAINT `FK8mq3xk535dfnxag0br0bi0rmh` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `FKs0hv8a17k556regux0nvttl2m` FOREIGN KEY (`bonus_penalty_id`) REFERENCES `bonus_penalty` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `employee_bonus_penalties` */

/*Table structure for table `file` */

DROP TABLE IF EXISTS `file`;

CREATE TABLE `file` (
  `id` varchar(255) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` date DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `file` */

insert  into `file`(`id`,`created_by`,`created_date`,`deleted`,`modified_by`,`modified_date`,`name`,`type`,`url`) values 
('711f426f-37ac-493e-b79c-8f429288c8a4','truongtv','2021-12-10','\0','truongtv','2021-12-10','file_10-12-2021_09-31-49_z2738347792839_42241542750cdf637d4b82a9cf56b0be.jpg','image/jpeg','/var/app/current/uploads/');

/*Table structure for table `frame_material` */

DROP TABLE IF EXISTS `frame_material`;

CREATE TABLE `frame_material` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` date DEFAULT NULL,
  `frame_length` varchar(255) DEFAULT NULL,
  `frame_width` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `frame_material` */

/*Table structure for table `group_material` */

DROP TABLE IF EXISTS `group_material`;

CREATE TABLE `group_material` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` date DEFAULT NULL,
  `check_group_material` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `group_material` */

/*Table structure for table `height_material` */

DROP TABLE IF EXISTS `height_material`;

CREATE TABLE `height_material` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` date DEFAULT NULL,
  `frame_height` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `height_material` */

/*Table structure for table `history_salary` */

DROP TABLE IF EXISTS `history_salary`;

CREATE TABLE `history_salary` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` date DEFAULT NULL,
  `account_accept` varchar(255) DEFAULT NULL,
  `advance_salary` varchar(255) DEFAULT NULL,
  `bonus` varchar(255) DEFAULT NULL,
  `count_work` double DEFAULT NULL,
  `date_accept` date DEFAULT NULL,
  `penalty` varchar(255) DEFAULT NULL,
  `salary` varchar(255) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `total_money` varchar(255) DEFAULT NULL,
  `employee_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKl90fglpjpf6ku3keoivhved8h` (`employee_id`),
  CONSTRAINT `FKl90fglpjpf6ku3keoivhved8h` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `history_salary` */

insert  into `history_salary`(`id`,`created_by`,`created_date`,`deleted`,`modified_by`,`modified_date`,`account_accept`,`advance_salary`,`bonus`,`count_work`,`date_accept`,`penalty`,`salary`,`status`,`total_money`,`employee_id`) values 
(1,'JOB_AUTO','2021-11-10','\0','JOB_AUTO','2021-11-10',NULL,'0','0',0,NULL,'0','1500000','\0','0',1),
(2,'JOB_AUTO','2021-11-10','\0','JOB_AUTO','2021-11-10',NULL,'0','0',0,NULL,'0','1500000','\0','0',2),
(3,'JOB_AUTO','2021-11-10','\0','JOB_AUTO','2021-11-10',NULL,'0','0',0,NULL,'0','1500000','\0','0',3),
(4,'JOB_AUTO','2021-11-10','\0','JOB_AUTO','2021-11-10',NULL,'0','0',0,NULL,'0','1500000','\0','0',4),
(5,'JOB_AUTO','2021-11-10','\0','JOB_AUTO','2021-11-10',NULL,'0','0',0,NULL,'0','1500000','\0','0',5),
(6,'JOB_AUTO','2021-12-10','\0','JOB_AUTO','2021-12-10',NULL,'0','0',0,NULL,'0','1500000','\0','0',1),
(7,'JOB_AUTO','2021-12-10','\0','JOB_AUTO','2021-12-10',NULL,'0','0',0,NULL,'0','1500000','\0','0',2),
(8,'JOB_AUTO','2021-12-10','\0','JOB_AUTO','2021-12-10',NULL,'0','0',0,NULL,'0','1500000','\0','0',3),
(9,'JOB_AUTO','2021-12-10','\0','JOB_AUTO','2021-12-10',NULL,'0','0',0,NULL,'0','1500000','\0','0',1),
(10,'JOB_AUTO','2021-12-10','\0','JOB_AUTO','2021-12-10',NULL,'0','0',0,NULL,'0','1500000','\0','0',5);

/*Table structure for table `material` */

DROP TABLE IF EXISTS `material`;

CREATE TABLE `material` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` date DEFAULT NULL,
  `check_material` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `percent_choose_in_month` double DEFAULT NULL,
  `percent_choose_in_quarter_of_year` double DEFAULT NULL,
  `percent_choose_in_year` double DEFAULT NULL,
  `company_id` bigint DEFAULT NULL,
  `group_material_id` bigint DEFAULT NULL,
  `file_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2vx2oaljp97had67t7fbdnx0k` (`company_id`),
  KEY `FKkv81od2ab5oq5yncmunn6e98u` (`group_material_id`),
  KEY `FKsbdkgfcwrb54d8s4a3b7liyft` (`file_id`),
  CONSTRAINT `FK2vx2oaljp97had67t7fbdnx0k` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `FKkv81od2ab5oq5yncmunn6e98u` FOREIGN KEY (`group_material_id`) REFERENCES `group_material` (`id`),
  CONSTRAINT `FKsbdkgfcwrb54d8s4a3b7liyft` FOREIGN KEY (`file_id`) REFERENCES `file` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `material` */

/*Table structure for table `persona_leave_application` */

DROP TABLE IF EXISTS `persona_leave_application`;

CREATE TABLE `persona_leave_application` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` date DEFAULT NULL,
  `accept` varchar(255) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `content` text,
  `date_accept` date DEFAULT NULL,
  `date_end` date DEFAULT NULL,
  `date_start` date DEFAULT NULL,
  `file_attach` varchar(255) DEFAULT NULL,
  `id_employee_accept` bigint DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `employee_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe960gyn4br66hktqfakttfrqd` (`employee_id`),
  CONSTRAINT `FKe960gyn4br66hktqfakttfrqd` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `persona_leave_application` */

/*Table structure for table `position` */

DROP TABLE IF EXISTS `position`;

CREATE TABLE `position` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` date DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `position` */

insert  into `position`(`id`,`created_by`,`created_date`,`deleted`,`modified_by`,`modified_date`,`code`,`name`) values 
(1,'truongtv','2021-12-10','\0','truongtv','2021-12-10','GD','Giám Đốc'),
(2,'truongtv','2021-12-10','\0','truongtv','2021-12-10','QL','Quản Lý'),
(3,'truongtv','2021-12-10','\0','truongtv','2021-12-10','NV','Nhân Viên');

/*Table structure for table `price_material` */

DROP TABLE IF EXISTS `price_material`;

CREATE TABLE `price_material` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` date DEFAULT NULL,
  `price` varchar(255) DEFAULT NULL,
  `frame_material_id` bigint DEFAULT NULL,
  `frame_height_material_id` bigint DEFAULT NULL,
  `material_id` bigint DEFAULT NULL,
  `unit_measure_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp4mc4rrxljbjn145glw2cv61d` (`frame_material_id`),
  KEY `FK3bohm3hutke5ugbfy6y77g23k` (`frame_height_material_id`),
  KEY `FKhyrv95fjodyhclrx2bi0ow66` (`material_id`),
  KEY `FKkr37ga2f9ttvn92l20jld1rqf` (`unit_measure_id`),
  CONSTRAINT `FK3bohm3hutke5ugbfy6y77g23k` FOREIGN KEY (`frame_height_material_id`) REFERENCES `height_material` (`id`),
  CONSTRAINT `FKhyrv95fjodyhclrx2bi0ow66` FOREIGN KEY (`material_id`) REFERENCES `material` (`id`),
  CONSTRAINT `FKkr37ga2f9ttvn92l20jld1rqf` FOREIGN KEY (`unit_measure_id`) REFERENCES `unit_measure` (`id`),
  CONSTRAINT `FKp4mc4rrxljbjn145glw2cv61d` FOREIGN KEY (`frame_material_id`) REFERENCES `frame_material` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `price_material` */

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` date DEFAULT NULL,
  `count` int DEFAULT NULL,
  `height` varchar(255) DEFAULT NULL,
  `length` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `price` varchar(255) DEFAULT NULL,
  `price_in_contact` varchar(255) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `width` varchar(255) DEFAULT NULL,
  `contact_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7uagktiyc4frf09sgh4usw4ju` (`contact_id`),
  CONSTRAINT `FK7uagktiyc4frf09sgh4usw4ju` FOREIGN KEY (`contact_id`) REFERENCES `contact` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `product` */

/*Table structure for table `product_material` */

DROP TABLE IF EXISTS `product_material`;

CREATE TABLE `product_material` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` date DEFAULT NULL,
  `count` int DEFAULT NULL,
  `note` text,
  `price_at_create_contact` varchar(255) DEFAULT NULL,
  `price_material_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdgkxgky2ervnvp5ylaqhenas1` (`price_material_id`),
  KEY `FKk16tidor8p5yj7n3e9h8j784d` (`product_id`),
  CONSTRAINT `FKdgkxgky2ervnvp5ylaqhenas1` FOREIGN KEY (`price_material_id`) REFERENCES `price_material` (`id`),
  CONSTRAINT `FKk16tidor8p5yj7n3e9h8j784d` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `product_material` */

/*Table structure for table `production_oder_employee` */

DROP TABLE IF EXISTS `production_oder_employee`;

CREATE TABLE `production_oder_employee` (
  `production_oder_id` bigint NOT NULL,
  `employee_id` bigint NOT NULL,
  KEY `FK3gedqh617lv4hah258bhdpuyn` (`employee_id`),
  KEY `FK59ekgetciy77pcao9cechkt7v` (`production_oder_id`),
  CONSTRAINT `FK3gedqh617lv4hah258bhdpuyn` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `FK59ekgetciy77pcao9cechkt7v` FOREIGN KEY (`production_oder_id`) REFERENCES `production_order` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `production_oder_employee` */

/*Table structure for table `production_order` */

DROP TABLE IF EXISTS `production_order`;

CREATE TABLE `production_order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` date DEFAULT NULL,
  `date_end` date DEFAULT NULL,
  `date_start` date DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `number_finish` varchar(255) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkc38iyv9ioqhjwcdlrmfpecq3` (`product_id`),
  CONSTRAINT `FKkc38iyv9ioqhjwcdlrmfpecq3` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `production_order` */

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` date DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `role` */

insert  into `role`(`id`,`created_by`,`created_date`,`deleted`,`modified_by`,`modified_date`,`code`,`name`) values 
(1,'truongtv','2021-12-10','\0','truongtv','2021-12-10','SP_ADMIN','Quản Lý Hệ Thống'),
(2,'truongtv','2021-12-10','\0','truongtv','2021-12-10','ADMIN','Quản Lý'),
(3,'truongtv','2021-12-10','\0','truongtv','2021-12-10','EMPLOYEE','Nhân Viên');

/*Table structure for table `time_keep` */

DROP TABLE IF EXISTS `time_keep`;

CREATE TABLE `time_keep` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` date DEFAULT NULL,
  `date` date DEFAULT NULL,
  `note` text,
  `type` varchar(255) DEFAULT NULL,
  `employee_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa50filqbtluks1279bfks4r4h` (`employee_id`),
  CONSTRAINT `FKa50filqbtluks1279bfks4r4h` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=163 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `time_keep` */

insert  into `time_keep`(`id`,`created_by`,`created_date`,`deleted`,`modified_by`,`modified_date`,`date`,`note`,`type`,`employee_id`) values 
(1,'truongtv','2021-11-10','\0','truongtv','2021-11-10','2021-11-10','','0',1),
(2,'truongtv','2021-11-10','\0','truongtv','2021-11-10','2021-11-10','','0',2),
(3,'truongtv','2021-11-10','\0','truongtv','2021-11-10','2021-11-10','','0',3),
(4,'truongtv','2021-11-10','\0','truongtv','2021-11-10','2021-11-10','','0',4),
(5,'truongtv','2021-11-10','\0','truongtv','2021-11-10','2021-11-10','','0',5),
(6,'truongtv','2021-11-11','\0','truongtv','2021-11-11','2021-11-11','','0',1),
(7,'truongtv','2021-11-11','\0','truongtv','2021-11-11','2021-11-11','','0',2),
(8,'truongtv','2021-11-11','\0','truongtv','2021-11-11','2021-11-11','','0',3),
(9,'truongtv','2021-11-11','\0','truongtv','2021-11-11','2021-11-11','','0',4),
(10,'truongtv','2021-11-11','\0','truongtv','2021-11-11','2021-11-11','','0',5),
(11,'truongtv','2021-11-12','\0','truongtv','2021-11-12','2021-11-12','','0',1),
(12,'truongtv','2021-11-12','\0','truongtv','2021-11-12','2021-11-12','','0',2),
(13,'truongtv','2021-11-12','\0','truongtv','2021-11-12','2021-11-12','','0',3),
(14,'truongtv','2021-11-12','\0','truongtv','2021-11-12','2021-11-12','','0',4),
(15,'truongtv','2021-11-12','\0','truongtv','2021-11-12','2021-11-12','','0',5),
(16,'truongtv','2021-11-13','\0','truongtv','2021-11-13','2021-11-13','','0',1),
(17,'truongtv','2021-11-13','\0','truongtv','2021-11-13','2021-11-13','','0',2),
(18,'truongtv','2021-11-13','\0','truongtv','2021-11-13','2021-11-13','','0',3),
(19,'truongtv','2021-11-13','\0','truongtv','2021-11-13','2021-11-13','','0',4),
(20,'truongtv','2021-11-13','\0','truongtv','2021-11-13','2021-11-13','','0',5),
(21,'truongtv','2021-11-14','\0','truongtv','2021-11-14','2021-11-14','','0',1),
(22,'truongtv','2021-11-14','\0','truongtv','2021-11-14','2021-11-14','','0',2),
(23,'truongtv','2021-11-14','\0','truongtv','2021-11-14','2021-11-14','','0',3),
(24,'truongtv','2021-11-14','\0','truongtv','2021-11-14','2021-11-14','','0',4),
(25,'truongtv','2021-11-14','\0','truongtv','2021-11-14','2021-11-14','','0',5),
(26,'truongtv','2021-11-15','\0','truongtv','2021-11-15','2021-11-15','','0',1),
(27,'truongtv','2021-11-15','\0','truongtv','2021-11-15','2021-11-15','','0',2),
(28,'truongtv','2021-11-15','\0','truongtv','2021-11-15','2021-11-15','','0',3),
(29,'truongtv','2021-11-15','\0','truongtv','2021-11-15','2021-11-15','','0',4),
(30,'truongtv','2021-11-15','\0','truongtv','2021-11-15','2021-11-15','','0',5),
(31,'truongtv','2021-11-16','\0','truongtv','2021-11-16','2021-11-16','','0',1),
(32,'truongtv','2021-11-16','\0','truongtv','2021-11-16','2021-11-16','','0',2),
(34,'truongtv','2021-11-16','\0','truongtv','2021-11-16','2021-11-16','','0',4),
(35,'truongtv','2021-11-16','\0','truongtv','2021-11-16','2021-11-16','','0',5),
(36,'truongtv','2021-11-17','\0','truongtv','2021-11-17','2021-11-17','','0',1),
(37,'truongtv','2021-11-17','\0','truongtv','2021-11-17','2021-11-17','','0',2),
(38,'truongtv','2021-11-17','\0','truongtv','2021-11-17','2021-11-17','','0',3),
(39,'truongtv','2021-11-17','\0','truongtv','2021-11-17','2021-11-17','','0',4),
(40,'truongtv','2021-11-17','\0','truongtv','2021-11-17','2021-11-17','','0',5),
(41,'truongtv','2021-11-18','\0','truongtv','2021-11-18','2021-11-18','','0',1),
(42,'truongtv','2021-11-18','\0','truongtv','2021-11-18','2021-11-18','','0',2),
(43,'truongtv','2021-11-18','\0','truongtv','2021-11-18','2021-11-18','','0',3),
(44,'truongtv','2021-11-18','\0','truongtv','2021-11-18','2021-11-18','','0',4),
(45,'truongtv','2021-11-18','\0','truongtv','2021-11-18','2021-11-18','','0',5),
(46,'truongtv','2021-11-19','\0','truongtv','2021-11-19','2021-11-19','','0',1),
(47,'truongtv','2021-11-19','\0','truongtv','2021-11-19','2021-11-19','','0',2),
(48,'truongtv','2021-11-19','\0','truongtv','2021-11-19','2021-11-19','','0',3),
(49,'truongtv','2021-11-19','\0','truongtv','2021-11-19','2021-11-19','','0',4),
(50,'truongtv','2021-11-19','\0','truongtv','2021-11-19','2021-11-19','','0',5),
(51,'truongtv','2021-11-20','\0','truongtv','2021-11-20','2021-11-20','','0',1),
(52,'truongtv','2021-11-20','\0','truongtv','2021-11-20','2021-11-20','','0',2),
(53,'truongtv','2021-11-20','\0','truongtv','2021-11-20','2021-11-20','','0',3),
(54,'truongtv','2021-11-20','\0','truongtv','2021-11-20','2021-11-20','','0',4),
(55,'truongtv','2021-11-20','\0','truongtv','2021-11-20','2021-11-20','','0',5),
(56,'truongtv','2021-11-21','\0','truongtv','2021-11-21','2021-11-21','','0',1),
(57,'truongtv','2021-11-21','\0','truongtv','2021-11-21','2021-11-21','','0',2),
(58,'truongtv','2021-11-21','\0','truongtv','2021-11-21','2021-11-21','','0',3),
(59,'truongtv','2021-11-21','\0','truongtv','2021-11-21','2021-11-21','','0',4),
(60,'truongtv','2021-11-21','\0','truongtv','2021-11-21','2021-11-21','','0',5),
(61,'truongtv','2021-11-22','\0','truongtv','2021-11-22','2021-11-22','','0',1),
(62,'truongtv','2021-11-22','\0','truongtv','2021-11-22','2021-11-22','','0',2),
(63,'truongtv','2021-11-22','\0','truongtv','2021-11-22','2021-11-22','','0',3),
(64,'truongtv','2021-11-22','\0','truongtv','2021-11-22','2021-11-22','','0',4),
(65,'truongtv','2021-11-22','\0','truongtv','2021-11-22','2021-11-22','','0',5),
(66,'truongtv','2021-11-23','\0','truongtv','2021-11-23','2021-11-23','','0',1),
(67,'truongtv','2021-11-23','\0','truongtv','2021-11-23','2021-11-23','','0',2),
(68,'truongtv','2021-11-23','\0','truongtv','2021-11-23','2021-11-23','','0',3),
(69,'truongtv','2021-11-23','\0','truongtv','2021-11-23','2021-11-23','','0',4),
(70,'truongtv','2021-11-23','\0','truongtv','2021-11-23','2021-11-23','','0',5),
(71,'truongtv','2021-11-24','\0','truongtv','2021-11-24','2021-11-24','','0',1),
(72,'truongtv','2021-11-24','\0','truongtv','2021-11-24','2021-11-24','','0',2),
(73,'truongtv','2021-11-24','\0','truongtv','2021-11-24','2021-11-24','','0',3),
(74,'truongtv','2021-11-24','\0','truongtv','2021-11-24','2021-11-24','','0',4),
(75,'truongtv','2021-11-24','\0','truongtv','2021-11-24','2021-11-24','','0',5),
(76,'truongtv','2021-11-25','\0','truongtv','2021-11-25','2021-11-25','','0',1),
(77,'truongtv','2021-11-25','\0','truongtv','2021-11-25','2021-11-25','','0',2),
(78,'truongtv','2021-11-25','\0','truongtv','2021-11-25','2021-11-25','','0',3),
(79,'truongtv','2021-11-25','\0','truongtv','2021-11-25','2021-11-25','','0',4),
(80,'truongtv','2021-11-25','\0','truongtv','2021-11-25','2021-11-25','','0',5),
(81,'truongtv','2021-11-26','\0','truongtv','2021-11-26','2021-11-26','','0',1),
(82,'truongtv','2021-11-26','\0','truongtv','2021-11-26','2021-11-26','','0',2),
(83,'truongtv','2021-11-26','\0','truongtv','2021-11-26','2021-11-26','','0',3),
(84,'truongtv','2021-11-26','\0','truongtv','2021-11-26','2021-11-26','','0',4),
(85,'truongtv','2021-11-26','\0','truongtv','2021-11-26','2021-11-26','','0',5),
(86,'truongtv','2021-11-27','\0','truongtv','2021-11-27','2021-11-27','','0',1),
(87,'truongtv','2021-11-27','\0','truongtv','2021-11-27','2021-11-27','','0',2),
(88,'truongtv','2021-11-27','\0','truongtv','2021-11-27','2021-11-27','','0',3),
(89,'truongtv','2021-11-27','\0','truongtv','2021-11-27','2021-11-27','','0',4),
(90,'truongtv','2021-11-27','\0','truongtv','2021-11-27','2021-11-27','','0',5),
(91,'truongtv','2021-11-28','\0','truongtv','2021-11-28','2021-11-28','','0',1),
(92,'truongtv','2021-11-28','\0','truongtv','2021-11-28','2021-11-28','','0',2),
(93,'truongtv','2021-11-28','\0','truongtv','2021-11-28','2021-11-28','','0',3),
(94,'truongtv','2021-11-28','\0','truongtv','2021-11-28','2021-11-28','','0',4),
(95,'truongtv','2021-11-28','\0','truongtv','2021-11-28','2021-11-28','','0',5),
(96,'truongtv','2021-11-29','\0','truongtv','2021-11-29','2021-11-29','','0',1),
(97,'truongtv','2021-11-29','\0','truongtv','2021-11-29','2021-11-29','','0',2),
(98,'truongtv','2021-11-29','\0','truongtv','2021-11-29','2021-11-29','','0',3),
(99,'truongtv','2021-11-29','\0','truongtv','2021-11-29','2021-11-29','','0',4),
(100,'truongtv','2021-11-29','\0','truongtv','2021-11-29','2021-11-29','','0',5),
(101,'truongtv','2021-11-30','\0','truongtv','2021-11-30','2021-11-30','','0',1),
(102,'truongtv','2021-11-30','\0','truongtv','2021-11-30','2021-11-30','','0',2),
(103,'truongtv','2021-11-30','\0','truongtv','2021-11-30','2021-11-30','','0',3),
(104,'truongtv','2021-11-30','\0','truongtv','2021-11-30','2021-11-30','','0',4),
(105,'truongtv','2021-11-30','\0','truongtv','2021-11-30','2021-11-30','','0',5),
(106,'truongtv','2021-12-01','\0','truongtv','2021-12-01','2021-12-01','','0',1),
(107,'truongtv','2021-12-01','\0','truongtv','2021-12-01','2021-12-01','','0',2),
(108,'truongtv','2021-12-01','\0','truongtv','2021-12-01','2021-12-01','','0',3),
(109,'truongtv','2021-12-01','\0','truongtv','2021-12-01','2021-12-01','','0',4),
(110,'truongtv','2021-12-01','\0','truongtv','2021-12-01','2021-12-01','','0',5),
(111,'truongtv','2021-12-02','\0','truongtv','2021-12-02','2021-12-02','','0',1),
(112,'truongtv','2021-12-02','\0','truongtv','2021-12-02','2021-12-02','','0',2),
(113,'truongtv','2021-12-02','\0','truongtv','2021-12-02','2021-12-02','','0',3),
(114,'truongtv','2021-12-02','\0','truongtv','2021-12-02','2021-12-02','','0',4),
(115,'truongtv','2021-12-02','\0','truongtv','2021-12-02','2021-12-02','','0',5),
(116,'truongtv','2021-12-03','\0','truongtv','2021-12-03','2021-12-03','','0',1),
(117,'truongtv','2021-12-03','\0','truongtv','2021-12-03','2021-12-03','','0',2),
(118,'truongtv','2021-12-03','\0','truongtv','2021-12-03','2021-12-03','','0',3),
(119,'truongtv','2021-12-03','\0','truongtv','2021-12-03','2021-12-03','','0',4),
(120,'truongtv','2021-12-03','\0','truongtv','2021-12-03','2021-12-03','','0',5),
(121,'truongtv','2021-12-04','\0','truongtv','2021-12-04','2021-12-04','','0',1),
(122,'truongtv','2021-12-04','\0','truongtv','2021-12-04','2021-12-04','','0',2),
(123,'truongtv','2021-12-04','\0','truongtv','2021-12-04','2021-12-04','','0',3),
(124,'truongtv','2021-12-04','\0','truongtv','2021-12-04','2021-12-04','','0',4),
(125,'truongtv','2021-12-04','\0','truongtv','2021-12-04','2021-12-04','','0',5),
(126,'truongtv','2021-12-05','\0','truongtv','2021-12-05','2021-12-05','','0',1),
(127,'truongtv','2021-12-05','\0','truongtv','2021-12-05','2021-12-05','','0',2),
(128,'truongtv','2021-12-05','\0','truongtv','2021-12-05','2021-12-05','','0',3),
(129,'truongtv','2021-12-05','\0','truongtv','2021-12-05','2021-12-05','','0',4),
(130,'truongtv','2021-12-05','\0','truongtv','2021-12-05','2021-12-05','','0',5),
(131,'truongtv','2021-12-06','\0','truongtv','2021-12-06','2021-12-06','','0',1),
(132,'truongtv','2021-12-06','\0','truongtv','2021-12-06','2021-12-06','','0',2),
(133,'truongtv','2021-12-06','\0','truongtv','2021-12-06','2021-12-06','','0',3),
(134,'truongtv','2021-12-06','\0','truongtv','2021-12-06','2021-12-06','','0',4),
(135,'truongtv','2021-12-06','\0','truongtv','2021-12-06','2021-12-06','','0',5),
(136,'truongtv','2021-12-07','\0','truongtv','2021-12-07','2021-12-07','','0',1),
(137,'truongtv','2021-12-07','\0','truongtv','2021-12-07','2021-12-07','','0',2),
(138,'truongtv','2021-12-07','\0','truongtv','2021-12-07','2021-12-07','','0',3),
(139,'truongtv','2021-12-07','\0','truongtv','2021-12-07','2021-12-07','','0',4),
(140,'truongtv','2021-12-07','\0','truongtv','2021-12-07','2021-12-07','','0',5),
(141,'truongtv','2021-12-08','\0','truongtv','2021-12-08','2021-12-08','','0',1),
(142,'truongtv','2021-12-08','\0','truongtv','2021-12-08','2021-12-08','','0',2),
(143,'truongtv','2021-12-08','\0','truongtv','2021-12-08','2021-12-08','','0',3),
(144,'truongtv','2021-12-08','\0','truongtv','2021-12-08','2021-12-08','','0',4),
(145,'truongtv','2021-12-08','\0','truongtv','2021-12-08','2021-12-08','','0',5),
(146,'truongtv','2021-12-09','\0','truongtv','2021-12-09','2021-12-09','','0',1),
(147,'truongtv','2021-12-09','\0','truongtv','2021-12-09','2021-12-09','','0',2),
(148,'truongtv','2021-12-09','\0','truongtv','2021-12-09','2021-12-09','','0',3),
(149,'truongtv','2021-12-09','\0','truongtv','2021-12-09','2021-12-09','','0',4),
(150,'truongtv','2021-12-09','\0','truongtv','2021-12-09','2021-12-09','','0',5),
(151,'truongtv','2021-12-10','\0','truongtv','2021-12-10','2021-12-10','','0',1),
(152,'truongtv','2021-12-10','\0','truongtv','2021-12-10','2021-12-10','','0',2),
(153,'truongtv','2021-12-10','\0','truongtv','2021-12-10','2021-12-10','','0',3),
(154,'truongtv','2021-12-10','\0','truongtv','2021-12-10','2021-12-10','','0',4),
(155,'truongtv','2021-12-10','\0','truongtv','2021-12-10','2021-12-10','','0',5);

/*Table structure for table `unit_measure` */

DROP TABLE IF EXISTS `unit_measure`;

CREATE TABLE `unit_measure` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` date DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `unit_measure` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
