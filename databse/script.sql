/*
SQLyog Community v13.1.7 (64 bit)
MySQL - 8.0.26 : Database - acfcapstoneproject_test
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`acfcapstoneproject_test` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `acfcapstoneproject_test`;

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `account` */

insert  into `account`(`id`,`created_by`,`created_date`,`deleted`,`modified_by`,`modified_date`,`password`,`status`,`username`,`employee_id`) values 
(1,'truongtv','2021-12-26','\0','truongtv','2021-12-26','$2a$10$owPeobJk3ivEaur5Vqk8yea9rqzDa9tNyopneRxXvnv4YmyO65TG.','','truongtv',1),
(2,'truongtv','2021-12-26','\0','truongtv','2021-12-26','$2a$10$owPeobJk3ivEaur5Vqk8yea9rqzDa9tNyopneRxXvnv4YmyO65TG.','','ngoclt',2),
(3,'truongtv','2021-12-26','\0','truongtv','2021-12-26','$2a$10$owPeobJk3ivEaur5Vqk8yea9rqzDa9tNyopneRxXvnv4YmyO65TG.','','anhlh',3),
(4,'truongtv','2021-12-26','\0','truongtv','2021-12-26','$2a$10$owPeobJk3ivEaur5Vqk8yea9rqzDa9tNyopneRxXvnv4YmyO65TG.','','minhtb',4),
(5,'truongtv','2021-12-26','\0','truongtv','2021-12-26','$2a$10$owPeobJk3ivEaur5Vqk8yea9rqzDa9tNyopneRxXvnv4YmyO65TG.','','phongnhl',5);

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
(2,3),
(3,3),
(4,3),
(5,3);

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `advace_salary` */

insert  into `advace_salary`(`id`,`created_by`,`created_date`,`deleted`,`modified_by`,`modified_date`,`accept`,`advace_salary`,`comment`,`content`,`date_accept`,`id_employee_accept`,`title`,`employee_id`) values 
(1,'truongtv','2021-12-26','\0','truongtv','2021-12-26','1','300000','được em nhé','Nhà em có việc cần gấp ạ','2021-12-26',1,'Em xin ứng lương ',1),
(2,'truongtv','2021-12-26','\0','truongtv','2021-12-26','-1','3000000',NULL,'Nhà em cần thêm tiền ạ',NULL,NULL,'Em xin ứng lương lần nữa ạ',1);

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `bonus_penalty` */

insert  into `bonus_penalty`(`id`,`created_by`,`created_date`,`deleted`,`modified_by`,`modified_date`,`bonus`,`effective_date`,`money`,`reason`,`status`,`title`) values 
(1,'truongtv','2021-12-26','\0','truongtv','2021-12-26','\0','2021-12-30','500000','Nghỉ quá số buổi quy định','','Nhân viên làm việc không hiệu quả'),
(2,'truongtv','2021-12-26','\0','truongtv','2021-12-26','','2021-12-29','1000000','Các cá nhân có năng suất làm việc cao','','Nhân viên hiệu quả'),
(3,'truongtv','2021-12-26','\0','truongtv','2021-12-26','\0','2021-12-30','500000','Công việc không hiệu quả','\0','Nhân viên không hoàn thành công việc'),
(4,'truongtv','2021-12-26','\0','truongtv','2021-12-26','','2021-12-30','1000000','Nhóm có năng suất làm việc tốt','\0','Hoàn thiện tốt hợp đồng');

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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `company` */

insert  into `company`(`id`,`created_by`,`created_date`,`deleted`,`modified_by`,`modified_date`,`address`,`email`,`name`,`phone`) values 
(1,'truongtv','2021-12-26','\0',NULL,'2021-12-26','702/1K Sư Vạn Hạnh, P.12, Q.10, TP.HCM','ancuong@gmail.com','AN CƯỜNG','(84.28) 38625726'),
(2,'truongtv','2021-12-26','\0',NULL,'2021-12-26','Số 26, Ngõ 1 Phạm Tuấn Tài, Cầu Giấy, Hà Nội','thietke@kientrucvhome.com','CÔNG TY CỔ PHẦN KIẾN TRÚC V-HOME','0242.2823.666 ; 0965 197 222'),
(3,'truongtv','2021-12-26','\0',NULL,'2021-12-26','Số 35 Phan Kế Bính, Ba Đình, Hà Nội','ahome8866@gmail.com','Nội Thất Cao Cấp Ahome','0981 088 866'),
(4,'truongtv','2021-12-26','\0',NULL,'2021-12-26','số 1 ngõ 401 Nguyễn Khang, Cầu Giấy, Hà Nội','info@noithat9x.vn','9X INTERIOR','0904.809.558'),
(5,'truongtv','2021-12-26','\0',NULL,'2021-12-26','Tòa Thăng Long Victory - Nam An Khánh - Hà Nội','noithatfuhome@gmail.com','Nội thất Fuhome','0985 403 985 ; 0981 025 170'),
(6,'truongtv','2021-12-26','\0',NULL,'2021-12-26','Tầng 2A Tòa N09B2 khu đô thị mới Dịch Vọng, Cầu Giấy, Hà Nội','magichome@gmail.com','MagicHome','0902 886 644'),
(7,'truongtv','2021-12-26','\0',NULL,'2021-12-26','170 Phạm Văn Đồng, Phường Mai Dịch, Quận Cầu Giấy, Hà Nội','nthoanmy@gmail.com','Nội Thất Hoàn Mỹ','1800 6971 ; 0922 06 6969'),
(8,'truongtv','2021-12-26','\0',NULL,'2021-12-26','Số 2 Đường 2.5, KĐT Gamuda Gardens, Trần Phú, Hoàng Mai, Hà Nội','landfurniture@gmail.com','LANDCO','024 6666 1155 ; 024 6666 1515'),
(9,'truongtv','2021-12-26','\0',NULL,'2021-12-26','321 Trường Chinh, Thanh Xuân, Hà Nội','xaydungnhaxinh@gmail.com','Nhà Xinh','0987.26.5555'),
(10,'truongtv','2021-12-26','\0',NULL,'2021-12-26','Km18 Đại Lộ Thăng Long, Hà Nội','gonguyenquang@gmail.com','Gỗ Nguyên Quang','0977243763');

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `contact` */

insert  into `contact`(`id`,`created_by`,`created_date`,`deleted`,`modified_by`,`modified_date`,`date_finish`,`name`,`note`,`number_finish`,`status_done`,`total_money`,`company_id`) values 
(1,'truongtv','2021-12-26','\0','truongtv','2021-12-26','2022-01-25','Hợp đồng phòng ngủ( Nhà Xinh )','Ghi Chú: (Ghi chú hợp đồng)','0/2',-2,'3600000',9);

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
(1,'truongtv','2021-12-26','\0','truongtv','2021-12-26','Nam Định','1999-01-13','truongtvhe130302@fpt.edu.vn','Trần Vũ Trường','','Kinh','0382945665',320000,NULL,2),
(2,'truongtv','2021-12-26','\0','truongtv','2021-12-26','Hà Nội','1999-07-14','ngoclthe131028@fpt.edu.vn','Lê Thị Ngọc','\0','Kinh','0961352582',300000,NULL,2),
(3,'truongtv','2021-12-26','\0','truongtv','2021-12-26','Hà Nội','1999-03-12','anhlhhe130300@fpt.edu.vn','Lê Hoàng Anh','','Kinh','0965340219',300000,NULL,3),
(4,'truongtv','2021-12-26','\0','truongtv','2021-12-26','Thái Bình','1999-12-19','minhtbhe130308@fpt.edu.vn','Trần Bình Minh','','Kinh','0342656852',300000,NULL,3),
(5,'truongtv','2021-12-26','\0','truongtv','2021-12-26','Bắc Ninh','1999-12-05','phongnhlhe130650@fpt.edu.vn','Nguyễn Hữu Lâm Phong','','Kinh','0389482335',300000,NULL,3);

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

insert  into `employee_bonus_penalties`(`bonus_penalty_id`,`employee_id`) values 
(1,1),
(1,2),
(2,1),
(2,2),
(3,1),
(3,2),
(4,1),
(4,2);

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `frame_material` */

insert  into `frame_material`(`id`,`created_by`,`created_date`,`deleted`,`modified_by`,`modified_date`,`frame_length`,`frame_width`) values 
(1,'truongtv','2021-12-26','\0','truongtv','2021-12-26','2440','1220'),
(2,'truongtv','2021-12-26','\0','truongtv','2021-12-26','2440','1830'),
(3,'truongtv','2021-12-26','\0','truongtv','2021-12-26','2745','1220'),
(4,'truongtv','2021-12-26','\0','truongtv','2021-12-26','4300','1830'),
(5,'truongtv','2021-12-26','\0','truongtv','2021-12-26','3660','1550'),
(6,'truongtv','2021-12-26','\0','truongtv','2021-12-26','3050','1525');

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `group_material` */

insert  into `group_material`(`id`,`created_by`,`created_date`,`deleted`,`modified_by`,`modified_date`,`check_group_material`,`name`) values 
(1,'truongtv','2021-12-26','\0','truongtv','2021-12-26','\0','LAMINATES'),
(2,'truongtv','2021-12-26','\0','truongtv','2021-12-26','\0','MELAMINES'),
(3,'truongtv','2021-12-26','\0','truongtv','2021-12-26','\0','ACRYLICS'),
(4,'truongtv','2021-12-26','\0','truongtv','2021-12-26','\0','VENEER'),
(5,'truongtv','2021-12-26','\0','truongtv','2021-12-26','','GỖ CÔNG NGHIỆP'),
(6,'truongtv','2021-12-26','\0','truongtv','2021-12-26','','GỖ NGUYÊN KHỐI');

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
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `height_material` */

insert  into `height_material`(`id`,`created_by`,`created_date`,`deleted`,`modified_by`,`modified_date`,`frame_height`) values 
(1,'truongtv','2021-12-26','\0','truongtv','2021-12-26','0.6'),
(2,'truongtv','2021-12-26','\0','truongtv','2021-12-26','0.7'),
(3,'truongtv','2021-12-26','\0','truongtv','2021-12-26','0.8'),
(4,'truongtv','2021-12-26','\0','truongtv','2021-12-26','0.9'),
(5,'truongtv','2021-12-26','\0','truongtv','2021-12-26','1'),
(6,'truongtv','2021-12-26','\0','truongtv','2021-12-26','1.1'),
(7,'truongtv','2021-12-26','\0','truongtv','2021-12-26','1.2'),
(8,'truongtv','2021-12-26','\0','truongtv','2021-12-26','1.3'),
(9,'truongtv','2021-12-26','\0','truongtv','2021-12-26','2'),
(10,'truongtv','2021-12-26','\0','truongtv','2021-12-26','3'),
(11,'truongtv','2021-12-26','\0','truongtv','2021-12-26','4'),
(12,'truongtv','2021-12-26','\0','truongtv','2021-12-26','5'),
(13,'truongtv','2021-12-26','\0','truongtv','2021-12-26','6'),
(14,'truongtv','2021-12-26','\0','truongtv','2021-12-26','7'),
(15,'truongtv','2021-12-26','\0','truongtv','2021-12-26','8'),
(16,'truongtv','2021-12-26','\0','truongtv','2021-12-26','9'),
(17,'truongtv','2021-12-26','\0','truongtv','2021-12-26','10'),
(18,'truongtv','2021-12-26','\0','truongtv','2021-12-26','11'),
(19,'truongtv','2021-12-26','\0','truongtv','2021-12-26','12'),
(20,'truongtv','2021-12-26','\0','truongtv','2021-12-26','13'),
(21,'truongtv','2021-12-26','\0','truongtv','2021-12-26','14'),
(22,'truongtv','2021-12-26','\0','truongtv','2021-12-26','15'),
(23,'truongtv','2021-12-26','\0','truongtv','2021-12-26','16'),
(24,'truongtv','2021-12-26','\0','truongtv','2021-12-26','17'),
(25,'truongtv','2021-12-26','\0','truongtv','2021-12-26','18'),
(26,'truongtv','2021-12-26','\0','truongtv','2021-12-26','19'),
(27,'truongtv','2021-12-26','\0','truongtv','2021-12-26','20'),
(28,'truongtv','2021-12-26','\0','truongtv','2021-12-26','21'),
(29,'truongtv','2021-12-26','\0','truongtv','2021-12-26','22'),
(30,'truongtv','2021-12-26','\0','truongtv','2021-12-26','23'),
(31,'truongtv','2021-12-26','\0','truongtv','2021-12-26','24'),
(32,'truongtv','2021-12-26','\0','truongtv','2021-12-26','25');

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
(1,'JOB_AUTO','2021-11-10','\0','JOB_AUTO','2021-11-10',NULL,'0','0',30,NULL,'0','320000','\0','9600000',1),
(2,'JOB_AUTO','2021-11-10','\0','JOB_AUTO','2021-11-10',NULL,'0','0',30,NULL,'0','300000','\0','9000000',2),
(3,'JOB_AUTO','2021-11-10','\0','JOB_AUTO','2021-11-10',NULL,'0','0',30,NULL,'0','300000','\0','9000000',3),
(4,'JOB_AUTO','2021-11-10','\0','JOB_AUTO','2021-11-10',NULL,'0','0',30,NULL,'0','300000','\0','9000000',4),
(5,'JOB_AUTO','2021-11-10','\0','JOB_AUTO','2021-11-10',NULL,'0','0',30,NULL,'0','300000','\0','9000000',5),
(6,'JOB_AUTO','2021-12-10','\0','truongtv','2021-12-26',NULL,'300000','1000000',18,NULL,'500000','320000','\0','5960000',1),
(7,'JOB_AUTO','2021-12-10','\0','truongtv','2021-12-26',NULL,'0','1000000',18,NULL,'500000','300000','\0','5900000',2),
(8,'JOB_AUTO','2021-12-10','\0','JOB_AUTO','2021-12-10',NULL,'0','0',18,NULL,'0','300000','\0','5400000',3),
(9,'JOB_AUTO','2021-12-10','\0','JOB_AUTO','2021-12-10',NULL,'0','0',18,NULL,'0','300000','\0','5400000',4),
(10,'JOB_AUTO','2021-12-10','\0','JOB_AUTO','2021-12-10',NULL,'0','0',18,NULL,'0','300000','\0','5400000',5);

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `material` */

insert  into `material`(`id`,`created_by`,`created_date`,`deleted`,`modified_by`,`modified_date`,`check_material`,`name`,`percent_choose_in_month`,`percent_choose_in_quarter_of_year`,`percent_choose_in_year`,`company_id`,`group_material_id`,`file_id`) values 
(1,'truongtv','2021-12-26','\0','truongtv','2021-12-26','\0','LAMINATE KINGDOM',0,0,0,1,1,NULL),
(2,'truongtv','2021-12-26','\0','truongtv','2021-12-26','\0','LAMINATE IMO',0,0,0,1,1,NULL),
(3,'truongtv','2021-12-26','\0','truongtv','2021-12-26','\0','FORMICA LAMINATE',0,0,0,1,1,NULL),
(4,'truongtv','2021-12-26','\0','truongtv','2021-12-26','','MFC',0,0,0,1,5,NULL),
(5,'truongtv','2021-12-26','\0','truongtv','2021-12-26','','MDF',0,0,0,1,5,NULL),
(6,'truongtv','2021-12-26','\0','truongtv','2021-12-26','','HDF',0,0,0,1,5,NULL);

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `persona_leave_application` */

insert  into `persona_leave_application`(`id`,`created_by`,`created_date`,`deleted`,`modified_by`,`modified_date`,`accept`,`comment`,`content`,`date_accept`,`date_end`,`date_start`,`file_attach`,`id_employee_accept`,`title`,`employee_id`) values 
(1,'truongtv','2021-12-26','\0','truongtv','2021-12-26','1','Được em nhé. Em nghỉ đi','Nhà em có việc bận ạ','2021-12-26','2021-12-29','2021-12-27','',1,'Cho em xin nghỉ mấy hôm ạ',1),
(2,'truongtv','2021-12-26','\0','truongtv','2021-12-26','-1',NULL,'Nhà em chưa xong việc ạ',NULL,'2022-01-01','2021-12-30','',NULL,'Cho em nghỉ thêm mấy hôm nữa ạ',1);

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
(1,'truongtv','2021-12-26','\0','truongtv','2021-12-26','GD','Giám Đốc'),
(2,'truongtv','2021-12-26','\0','truongtv','2021-12-26','QL','Quản Lý'),
(3,'truongtv','2021-12-26','\0','truongtv','2021-12-26','NV','Nhân Viên');

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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `price_material` */

insert  into `price_material`(`id`,`created_by`,`created_date`,`deleted`,`modified_by`,`modified_date`,`price`,`frame_material_id`,`frame_height_material_id`,`material_id`,`unit_measure_id`) values 
(1,'truongtv','2021-12-26','\0','truongtv','2021-12-26','200000',4,1,1,1),
(2,'truongtv','2021-12-26','\0','truongtv','2021-12-26','200000',4,2,1,1),
(3,'truongtv','2021-12-26','\0','truongtv','2021-12-26','200000',5,4,2,1),
(4,'truongtv','2021-12-26','\0','truongtv','2021-12-26','200000',6,6,3,1),
(5,'truongtv','2021-12-26','\0','truongtv','2021-12-26','500000',4,27,4,1),
(6,'truongtv','2021-12-26','\0','truongtv','2021-12-26','500000',4,32,5,1),
(7,'truongtv','2021-12-26','\0','truongtv','2021-12-26','400000',5,22,6,1);

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `product` */

insert  into `product`(`id`,`created_by`,`created_date`,`deleted`,`modified_by`,`modified_date`,`count`,`height`,`length`,`name`,`note`,`price`,`price_in_contact`,`status`,`width`,`contact_id`) values 
(1,'truongtv','2021-12-26','\0','truongtv','2021-12-26',1,'4000','4880','Tủ gỗ','Thiết kế ôm tường, che chân tủ','1600000','1600000',-2,'2200',1),
(2,'truongtv','2021-12-26','\0','truongtv','2021-12-26',2,'1000','1330','Bàn trang điểm','Thiết kế điện chân bàn','4000000','2000000',-1,'1000',1);

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `product_material` */

insert  into `product_material`(`id`,`created_by`,`created_date`,`deleted`,`modified_by`,`modified_date`,`count`,`note`,`price_at_create_contact`,`price_material_id`,`product_id`) values 
(1,'truongtv','2021-12-26','\0','truongtv','2021-12-26',4,'Phủ chống ẩm acrylic','400000',7,1),
(2,'truongtv','2021-12-26','\0','truongtv','2021-12-26',4,'Phủ chống ẩm','500000',6,2);

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

insert  into `production_oder_employee`(`production_oder_id`,`employee_id`) values 
(1,1),
(1,2);

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `production_order` */

insert  into `production_order`(`id`,`created_by`,`created_date`,`deleted`,`modified_by`,`modified_date`,`date_end`,`date_start`,`name`,`number_finish`,`status`,`product_id`) values 
(1,'truongtv','2021-12-26','\0','truongtv','2021-12-26','2022-01-07','2021-12-30','Tủ trang điểm','0/2',-1,2);

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
(1,'truongtv','2021-12-26','\0','truongtv','2021-12-26','SP_ADMIN','Quản Lý Hệ Thống'),
(2,'truongtv','2021-12-26','\0','truongtv','2021-12-26','ADMIN','Quản Lý'),
(3,'truongtv','2021-12-26','\0','truongtv','2021-12-26','EMPLOYEE','Nhân Viên');

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
) ENGINE=InnoDB AUTO_INCREMENT=241 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `time_keep` */

insert  into `time_keep`(`id`,`created_by`,`created_date`,`deleted`,`modified_by`,`modified_date`,`date`,`note`,`type`,`employee_id`) values 
(1,'truongtv','2021-11-10','\0','truongtv','2021-11-10','2021-11-10','','1',1),
(2,'truongtv','2021-11-10','\0','truongtv','2021-11-10','2021-11-10','','1',2),
(3,'truongtv','2021-11-10','\0','truongtv','2021-11-10','2021-11-10','','1',3),
(4,'truongtv','2021-11-10','\0','truongtv','2021-11-10','2021-11-10','','1',4),
(5,'truongtv','2021-11-10','\0','truongtv','2021-11-10','2021-11-10','','1',5),
(6,'truongtv','2021-11-11','\0','truongtv','2021-11-11','2021-11-11','','1',1),
(7,'truongtv','2021-11-11','\0','truongtv','2021-11-11','2021-11-11','','1',2),
(8,'truongtv','2021-11-11','\0','truongtv','2021-11-11','2021-11-11','','1',3),
(9,'truongtv','2021-11-11','\0','truongtv','2021-11-11','2021-11-11','','1',4),
(10,'truongtv','2021-11-11','\0','truongtv','2021-11-11','2021-11-11','','1',5),
(11,'truongtv','2021-11-12','\0','truongtv','2021-11-12','2021-11-12','','1',1),
(12,'truongtv','2021-11-12','\0','truongtv','2021-11-12','2021-11-12','','1',2),
(13,'truongtv','2021-11-12','\0','truongtv','2021-11-12','2021-11-12','','1',3),
(14,'truongtv','2021-11-12','\0','truongtv','2021-11-12','2021-11-12','','1',4),
(15,'truongtv','2021-11-12','\0','truongtv','2021-11-12','2021-11-12','','1',5),
(16,'truongtv','2021-11-13','\0','truongtv','2021-11-13','2021-11-13','','1',1),
(17,'truongtv','2021-11-13','\0','truongtv','2021-11-13','2021-11-13','','1',2),
(18,'truongtv','2021-11-13','\0','truongtv','2021-11-13','2021-11-13','','1',3),
(19,'truongtv','2021-11-13','\0','truongtv','2021-11-13','2021-11-13','','1',4),
(20,'truongtv','2021-11-13','\0','truongtv','2021-11-13','2021-11-13','','1',5),
(21,'truongtv','2021-11-14','\0','truongtv','2021-11-14','2021-11-14','','1',1),
(22,'truongtv','2021-11-14','\0','truongtv','2021-11-14','2021-11-14','','1',2),
(23,'truongtv','2021-11-14','\0','truongtv','2021-11-14','2021-11-14','','1',3),
(24,'truongtv','2021-11-14','\0','truongtv','2021-11-14','2021-11-14','','1',4),
(25,'truongtv','2021-11-14','\0','truongtv','2021-11-14','2021-11-14','','1',5),
(26,'truongtv','2021-11-15','\0','truongtv','2021-11-15','2021-11-15','','1',1),
(27,'truongtv','2021-11-15','\0','truongtv','2021-11-15','2021-11-15','','1',2),
(28,'truongtv','2021-11-15','\0','truongtv','2021-11-15','2021-11-15','','1',3),
(29,'truongtv','2021-11-15','\0','truongtv','2021-11-15','2021-11-15','','1',4),
(30,'truongtv','2021-11-15','\0','truongtv','2021-11-15','2021-11-15','','1',5),
(31,'truongtv','2021-11-16','\0','truongtv','2021-11-16','2021-11-16','','1',1),
(32,'truongtv','2021-11-16','\0','truongtv','2021-11-16','2021-11-16','','1',2),
(33,'truongtv','2021-11-16','\0','truongtv','2021-11-16','2021-11-16','','1',3),
(34,'truongtv','2021-11-16','\0','truongtv','2021-11-16','2021-11-16','','1',4),
(35,'truongtv','2021-11-16','\0','truongtv','2021-11-16','2021-11-16','','1',5),
(36,'truongtv','2021-11-17','\0','truongtv','2021-11-17','2021-11-17','','1',1),
(37,'truongtv','2021-11-17','\0','truongtv','2021-11-17','2021-11-17','','1',2),
(38,'truongtv','2021-11-17','\0','truongtv','2021-11-17','2021-11-17','','1',3),
(39,'truongtv','2021-11-17','\0','truongtv','2021-11-17','2021-11-17','','1',4),
(40,'truongtv','2021-11-17','\0','truongtv','2021-11-17','2021-11-17','','1',5),
(41,'truongtv','2021-11-18','\0','truongtv','2021-11-18','2021-11-18','','1',1),
(42,'truongtv','2021-11-18','\0','truongtv','2021-11-18','2021-11-18','','1',2),
(43,'truongtv','2021-11-18','\0','truongtv','2021-11-18','2021-11-18','','1',3),
(44,'truongtv','2021-11-18','\0','truongtv','2021-11-18','2021-11-18','','1',4),
(45,'truongtv','2021-11-18','\0','truongtv','2021-11-18','2021-11-18','','1',5),
(46,'truongtv','2021-11-19','\0','truongtv','2021-11-19','2021-11-19','','1',1),
(47,'truongtv','2021-11-19','\0','truongtv','2021-11-19','2021-11-19','','1',2),
(48,'truongtv','2021-11-19','\0','truongtv','2021-11-19','2021-11-19','','1',3),
(49,'truongtv','2021-11-19','\0','truongtv','2021-11-19','2021-11-19','','1',4),
(50,'truongtv','2021-11-19','\0','truongtv','2021-11-19','2021-11-19','','1',5),
(51,'truongtv','2021-11-20','\0','truongtv','2021-11-20','2021-11-20','','1',1),
(52,'truongtv','2021-11-20','\0','truongtv','2021-11-20','2021-11-20','','1',2),
(53,'truongtv','2021-11-20','\0','truongtv','2021-11-20','2021-11-20','','1',3),
(54,'truongtv','2021-11-20','\0','truongtv','2021-11-20','2021-11-20','','1',4),
(55,'truongtv','2021-11-20','\0','truongtv','2021-11-20','2021-11-20','','1',5),
(56,'truongtv','2021-11-21','\0','truongtv','2021-11-21','2021-11-21','','1',1),
(57,'truongtv','2021-11-21','\0','truongtv','2021-11-21','2021-11-21','','1',2),
(58,'truongtv','2021-11-21','\0','truongtv','2021-11-21','2021-11-21','','1',3),
(59,'truongtv','2021-11-21','\0','truongtv','2021-11-21','2021-11-21','','1',4),
(60,'truongtv','2021-11-21','\0','truongtv','2021-11-21','2021-11-21','','1',5),
(61,'truongtv','2021-11-22','\0','truongtv','2021-11-22','2021-11-22','','1',1),
(62,'truongtv','2021-11-22','\0','truongtv','2021-11-22','2021-11-22','','1',2),
(63,'truongtv','2021-11-22','\0','truongtv','2021-11-22','2021-11-22','','1',3),
(64,'truongtv','2021-11-22','\0','truongtv','2021-11-22','2021-11-22','','1',4),
(65,'truongtv','2021-11-22','\0','truongtv','2021-11-22','2021-11-22','','1',5),
(66,'truongtv','2021-11-23','\0','truongtv','2021-11-23','2021-11-23','','1',1),
(67,'truongtv','2021-11-23','\0','truongtv','2021-11-23','2021-11-23','','1',2),
(68,'truongtv','2021-11-23','\0','truongtv','2021-11-23','2021-11-23','','1',3),
(69,'truongtv','2021-11-23','\0','truongtv','2021-11-23','2021-11-23','','1',4),
(70,'truongtv','2021-11-23','\0','truongtv','2021-11-23','2021-11-23','','1',5),
(71,'truongtv','2021-11-24','\0','truongtv','2021-11-24','2021-11-24','','1',1),
(72,'truongtv','2021-11-24','\0','truongtv','2021-11-24','2021-11-24','','1',2),
(73,'truongtv','2021-11-24','\0','truongtv','2021-11-24','2021-11-24','','1',3),
(74,'truongtv','2021-11-24','\0','truongtv','2021-11-24','2021-11-24','','1',4),
(75,'truongtv','2021-11-24','\0','truongtv','2021-11-24','2021-11-24','','1',5),
(76,'truongtv','2021-11-25','\0','truongtv','2021-11-25','2021-11-25','','1',1),
(77,'truongtv','2021-11-25','\0','truongtv','2021-11-25','2021-11-25','','1',2),
(78,'truongtv','2021-11-25','\0','truongtv','2021-11-25','2021-11-25','','1',3),
(79,'truongtv','2021-11-25','\0','truongtv','2021-11-25','2021-11-25','','1',4),
(80,'truongtv','2021-11-25','\0','truongtv','2021-11-25','2021-11-25','','1',5),
(81,'truongtv','2021-11-26','\0','truongtv','2021-11-26','2021-11-26','','1',1),
(82,'truongtv','2021-11-26','\0','truongtv','2021-11-26','2021-11-26','','1',2),
(83,'truongtv','2021-11-26','\0','truongtv','2021-11-26','2021-11-26','','1',3),
(84,'truongtv','2021-11-26','\0','truongtv','2021-11-26','2021-11-26','','1',4),
(85,'truongtv','2021-11-26','\0','truongtv','2021-11-26','2021-11-26','','1',5),
(86,'truongtv','2021-11-27','\0','truongtv','2021-11-27','2021-11-27','','1',1),
(87,'truongtv','2021-11-27','\0','truongtv','2021-11-27','2021-11-27','','1',2),
(88,'truongtv','2021-11-27','\0','truongtv','2021-11-27','2021-11-27','','1',3),
(89,'truongtv','2021-11-27','\0','truongtv','2021-11-27','2021-11-27','','1',4),
(90,'truongtv','2021-11-27','\0','truongtv','2021-11-27','2021-11-27','','1',5),
(91,'truongtv','2021-11-28','\0','truongtv','2021-11-28','2021-11-28','','1',1),
(92,'truongtv','2021-11-28','\0','truongtv','2021-11-28','2021-11-28','','1',2),
(93,'truongtv','2021-11-28','\0','truongtv','2021-11-28','2021-11-28','','1',3),
(94,'truongtv','2021-11-28','\0','truongtv','2021-11-28','2021-11-28','','1',4),
(95,'truongtv','2021-11-28','\0','truongtv','2021-11-28','2021-11-28','','1',5),
(96,'truongtv','2021-11-29','\0','truongtv','2021-11-29','2021-11-29','','1',1),
(97,'truongtv','2021-11-29','\0','truongtv','2021-11-29','2021-11-29','','1',2),
(98,'truongtv','2021-11-29','\0','truongtv','2021-11-29','2021-11-29','','1',3),
(99,'truongtv','2021-11-29','\0','truongtv','2021-11-29','2021-11-29','','1',4),
(100,'truongtv','2021-11-29','\0','truongtv','2021-11-29','2021-11-29','','1',5),
(101,'truongtv','2021-11-30','\0','truongtv','2021-11-30','2021-11-30','','1',1),
(102,'truongtv','2021-11-30','\0','truongtv','2021-11-30','2021-11-30','','1',2),
(103,'truongtv','2021-11-30','\0','truongtv','2021-11-30','2021-11-30','','1',3),
(104,'truongtv','2021-11-30','\0','truongtv','2021-11-30','2021-11-30','','1',4),
(105,'truongtv','2021-11-30','\0','truongtv','2021-11-30','2021-11-30','','1',5),
(106,'truongtv','2021-12-01','\0','truongtv','2021-12-01','2021-12-01','','1',1),
(107,'truongtv','2021-12-01','\0','truongtv','2021-12-01','2021-12-01','','1',2),
(108,'truongtv','2021-12-01','\0','truongtv','2021-12-01','2021-12-01','','1',3),
(109,'truongtv','2021-12-01','\0','truongtv','2021-12-01','2021-12-01','','1',4),
(110,'truongtv','2021-12-01','\0','truongtv','2021-12-01','2021-12-01','','1',5),
(111,'truongtv','2021-12-02','\0','truongtv','2021-12-02','2021-12-02','','1',1),
(112,'truongtv','2021-12-02','\0','truongtv','2021-12-02','2021-12-02','','1',2),
(113,'truongtv','2021-12-02','\0','truongtv','2021-12-02','2021-12-02','','1',3),
(114,'truongtv','2021-12-02','\0','truongtv','2021-12-02','2021-12-02','','1',4),
(115,'truongtv','2021-12-02','\0','truongtv','2021-12-02','2021-12-02','','1',5),
(116,'truongtv','2021-12-03','\0','truongtv','2021-12-03','2021-12-03','','1',1),
(117,'truongtv','2021-12-03','\0','truongtv','2021-12-03','2021-12-03','','1',2),
(118,'truongtv','2021-12-03','\0','truongtv','2021-12-03','2021-12-03','','1',3),
(119,'truongtv','2021-12-03','\0','truongtv','2021-12-03','2021-12-03','','1',4),
(120,'truongtv','2021-12-03','\0','truongtv','2021-12-03','2021-12-03','','1',5),
(121,'truongtv','2021-12-04','\0','truongtv','2021-12-04','2021-12-04','','1',1),
(122,'truongtv','2021-12-04','\0','truongtv','2021-12-04','2021-12-04','','1',2),
(123,'truongtv','2021-12-04','\0','truongtv','2021-12-04','2021-12-04','','1',3),
(124,'truongtv','2021-12-04','\0','truongtv','2021-12-04','2021-12-04','','1',4),
(125,'truongtv','2021-12-04','\0','truongtv','2021-12-04','2021-12-04','','1',5),
(126,'truongtv','2021-12-05','\0','truongtv','2021-12-05','2021-12-05','','1',1),
(127,'truongtv','2021-12-05','\0','truongtv','2021-12-05','2021-12-05','','1',2),
(128,'truongtv','2021-12-05','\0','truongtv','2021-12-05','2021-12-05','','1',3),
(129,'truongtv','2021-12-05','\0','truongtv','2021-12-05','2021-12-05','','1',4),
(130,'truongtv','2021-12-05','\0','truongtv','2021-12-05','2021-12-05','','1',5),
(131,'truongtv','2021-12-06','\0','truongtv','2021-12-06','2021-12-06','','1',1),
(132,'truongtv','2021-12-06','\0','truongtv','2021-12-06','2021-12-06','','1',2),
(133,'truongtv','2021-12-06','\0','truongtv','2021-12-06','2021-12-06','','1',3),
(134,'truongtv','2021-12-06','\0','truongtv','2021-12-06','2021-12-06','','1',4),
(135,'truongtv','2021-12-06','\0','truongtv','2021-12-06','2021-12-06','','1',5),
(136,'truongtv','2021-12-07','\0','truongtv','2021-12-07','2021-12-07','','1',1),
(137,'truongtv','2021-12-07','\0','truongtv','2021-12-07','2021-12-07','','1',2),
(138,'truongtv','2021-12-07','\0','truongtv','2021-12-07','2021-12-07','','1',3),
(139,'truongtv','2021-12-07','\0','truongtv','2021-12-07','2021-12-07','','1',4),
(140,'truongtv','2021-12-07','\0','truongtv','2021-12-07','2021-12-07','','1',5),
(141,'truongtv','2021-12-08','\0','truongtv','2021-12-08','2021-12-08','','1',1),
(142,'truongtv','2021-12-08','\0','truongtv','2021-12-08','2021-12-08','','1',2),
(143,'truongtv','2021-12-08','\0','truongtv','2021-12-08','2021-12-08','','1',3),
(144,'truongtv','2021-12-08','\0','truongtv','2021-12-08','2021-12-08','','1',4),
(145,'truongtv','2021-12-08','\0','truongtv','2021-12-08','2021-12-08','','1',5),
(146,'truongtv','2021-12-09','\0','truongtv','2021-12-09','2021-12-09','','1',1),
(147,'truongtv','2021-12-09','\0','truongtv','2021-12-09','2021-12-09','','1',2),
(148,'truongtv','2021-12-09','\0','truongtv','2021-12-09','2021-12-09','','1',3),
(149,'truongtv','2021-12-09','\0','truongtv','2021-12-09','2021-12-09','','1',4),
(150,'truongtv','2021-12-09','\0','truongtv','2021-12-09','2021-12-09','','1',5),
(151,'truongtv','2021-12-10','\0','truongtv','2021-12-10','2021-12-10','','1',1),
(152,'truongtv','2021-12-10','\0','truongtv','2021-12-10','2021-12-10','','1',2),
(153,'truongtv','2021-12-10','\0','truongtv','2021-12-10','2021-12-10','','1',3),
(154,'truongtv','2021-12-10','\0','truongtv','2021-12-10','2021-12-10','','1',4),
(155,'truongtv','2021-12-10','\0','truongtv','2021-12-10','2021-12-10','','1',5),
(156,'truongtv','2021-12-11','\0','truongtv','2021-12-11','2021-12-11','','1',1),
(157,'truongtv','2021-12-11','\0','truongtv','2021-12-11','2021-12-11','','1',2),
(158,'truongtv','2021-12-11','\0','truongtv','2021-12-11','2021-12-11','','1',3),
(159,'truongtv','2021-12-11','\0','truongtv','2021-12-11','2021-12-11','','1',4),
(160,'truongtv','2021-12-11','\0','truongtv','2021-12-11','2021-12-11','','1',5),
(161,'truongtv','2021-12-12','\0','truongtv','2021-12-12','2021-12-12','','1',1),
(162,'truongtv','2021-12-12','\0','truongtv','2021-12-12','2021-12-12','','1',2),
(163,'truongtv','2021-12-12','\0','truongtv','2021-12-12','2021-12-12','','1',3),
(164,'truongtv','2021-12-12','\0','truongtv','2021-12-12','2021-12-12','','1',4),
(165,'truongtv','2021-12-12','\0','truongtv','2021-12-12','2021-12-12','','1',5),
(166,'truongtv','2021-12-13','\0','truongtv','2021-12-13','2021-12-13','','1',1),
(167,'truongtv','2021-12-13','\0','truongtv','2021-12-13','2021-12-13','','1',2),
(168,'truongtv','2021-12-13','\0','truongtv','2021-12-13','2021-12-13','','1',3),
(169,'truongtv','2021-12-13','\0','truongtv','2021-12-13','2021-12-13','','1',4),
(170,'truongtv','2021-12-13','\0','truongtv','2021-12-13','2021-12-13','','1',5),
(171,'truongtv','2021-12-14','\0','truongtv','2021-12-14','2021-12-14','','1',1),
(172,'truongtv','2021-12-14','\0','truongtv','2021-12-14','2021-12-14','','1',2),
(173,'truongtv','2021-12-14','\0','truongtv','2021-12-14','2021-12-14','','1',3),
(174,'truongtv','2021-12-14','\0','truongtv','2021-12-14','2021-12-14','','1',4),
(175,'truongtv','2021-12-14','\0','truongtv','2021-12-14','2021-12-14','','1',5),
(176,'truongtv','2021-12-15','\0','truongtv','2021-12-15','2021-12-15','','1',1),
(177,'truongtv','2021-12-15','\0','truongtv','2021-12-15','2021-12-15','','1',2),
(178,'truongtv','2021-12-15','\0','truongtv','2021-12-15','2021-12-15','','1',3),
(179,'truongtv','2021-12-15','\0','truongtv','2021-12-15','2021-12-15','','1',4),
(180,'truongtv','2021-12-15','\0','truongtv','2021-12-15','2021-12-15','','1',5),
(181,'truongtv','2021-12-16','\0','truongtv','2021-12-16','2021-12-16','','1',1),
(182,'truongtv','2021-12-16','\0','truongtv','2021-12-16','2021-12-16','','1',2),
(183,'truongtv','2021-12-16','\0','truongtv','2021-12-16','2021-12-16','','1',3),
(184,'truongtv','2021-12-16','\0','truongtv','2021-12-16','2021-12-16','','1',4),
(185,'truongtv','2021-12-16','\0','truongtv','2021-12-16','2021-12-16','','1',5),
(186,'truongtv','2021-12-17','\0','truongtv','2021-12-17','2021-12-17','','1',1),
(187,'truongtv','2021-12-17','\0','truongtv','2021-12-17','2021-12-17','','1',2),
(188,'truongtv','2021-12-17','\0','truongtv','2021-12-17','2021-12-17','','1',3),
(189,'truongtv','2021-12-17','\0','truongtv','2021-12-17','2021-12-17','','1',4),
(190,'truongtv','2021-12-17','\0','truongtv','2021-12-17','2021-12-17','','1',5),
(191,'truongtv','2021-12-18','\0','truongtv','2021-12-18','2021-12-18','','1',1),
(192,'truongtv','2021-12-18','\0','truongtv','2021-12-18','2021-12-18','','1',2),
(193,'truongtv','2021-12-18','\0','truongtv','2021-12-18','2021-12-18','','1',3),
(194,'truongtv','2021-12-18','\0','truongtv','2021-12-18','2021-12-18','','1',4),
(195,'truongtv','2021-12-18','\0','truongtv','2021-12-18','2021-12-18','','1',5),
(196,'truongtv','2021-12-19','\0','truongtv','2021-12-19','2021-12-19','','1',1),
(197,'truongtv','2021-12-19','\0','truongtv','2021-12-19','2021-12-19','','1',2),
(198,'truongtv','2021-12-19','\0','truongtv','2021-12-19','2021-12-19','','1',3),
(199,'truongtv','2021-12-19','\0','truongtv','2021-12-19','2021-12-19','','1',4),
(200,'truongtv','2021-12-19','\0','truongtv','2021-12-19','2021-12-19','','1',5),
(201,'truongtv','2021-12-20','\0','truongtv','2021-12-20','2021-12-20','','1',1),
(202,'truongtv','2021-12-20','\0','truongtv','2021-12-20','2021-12-20','','1',2),
(203,'truongtv','2021-12-20','\0','truongtv','2021-12-20','2021-12-20','','1',3),
(204,'truongtv','2021-12-20','\0','truongtv','2021-12-20','2021-12-20','','1',4),
(205,'truongtv','2021-12-20','\0','truongtv','2021-12-20','2021-12-20','','1',5),
(206,'truongtv','2021-12-21','\0','truongtv','2021-12-21','2021-12-21','','1',1),
(207,'truongtv','2021-12-21','\0','truongtv','2021-12-21','2021-12-21','','1',2),
(208,'truongtv','2021-12-21','\0','truongtv','2021-12-21','2021-12-21','','1',3),
(209,'truongtv','2021-12-21','\0','truongtv','2021-12-21','2021-12-21','','1',4),
(210,'truongtv','2021-12-21','\0','truongtv','2021-12-21','2021-12-21','','1',5),
(211,'truongtv','2021-12-22','\0','truongtv','2021-12-22','2021-12-22','','1',1),
(212,'truongtv','2021-12-22','\0','truongtv','2021-12-22','2021-12-22','','1',2),
(213,'truongtv','2021-12-22','\0','truongtv','2021-12-22','2021-12-22','','1',3),
(214,'truongtv','2021-12-22','\0','truongtv','2021-12-22','2021-12-22','','1',4),
(215,'truongtv','2021-12-22','\0','truongtv','2021-12-22','2021-12-22','','1',5),
(216,'truongtv','2021-12-23','\0','truongtv','2021-12-23','2021-12-23','','1',1),
(217,'truongtv','2021-12-23','\0','truongtv','2021-12-23','2021-12-23','','1',2),
(218,'truongtv','2021-12-23','\0','truongtv','2021-12-23','2021-12-23','','1',3),
(219,'truongtv','2021-12-23','\0','truongtv','2021-12-23','2021-12-23','','1',4),
(220,'truongtv','2021-12-23','\0','truongtv','2021-12-23','2021-12-23','','1',5),
(221,'truongtv','2021-12-24','\0','truongtv','2021-12-24','2021-12-24','','1',1),
(222,'truongtv','2021-12-24','\0','truongtv','2021-12-24','2021-12-24','','1',2),
(223,'truongtv','2021-12-24','\0','truongtv','2021-12-24','2021-12-24','','1',3),
(224,'truongtv','2021-12-24','\0','truongtv','2021-12-24','2021-12-24','','1',4),
(225,'truongtv','2021-12-24','\0','truongtv','2021-12-24','2021-12-24','','1',5),
(226,'truongtv','2021-12-25','\0','truongtv','2021-12-25','2021-12-25','','1',1),
(227,'truongtv','2021-12-25','\0','truongtv','2021-12-25','2021-12-25','','1',2),
(228,'truongtv','2021-12-25','\0','truongtv','2021-12-25','2021-12-25','','1',3),
(229,'truongtv','2021-12-25','\0','truongtv','2021-12-25','2021-12-25','','1',4),
(230,'truongtv','2021-12-25','\0','truongtv','2021-12-25','2021-12-25','','1',5),
(231,'truongtv','2021-12-26','\0','truongtv','2021-12-26','2021-12-26','','1',1),
(232,'truongtv','2021-12-26','\0','truongtv','2021-12-26','2021-12-26','','1',2),
(233,'truongtv','2021-12-26','\0','truongtv','2021-12-26','2021-12-26','','1',3),
(234,'truongtv','2021-12-26','\0','truongtv','2021-12-26','2021-12-26','','1',4),
(235,'truongtv','2021-12-26','\0','truongtv','2021-12-26','2021-12-26','','1',5),
(236,'truongtv','2021-12-27','\0','truongtv','2021-12-27','2021-12-27','','1',1),
(237,'truongtv','2021-12-27','\0','truongtv','2021-12-27','2021-12-27','','1',2),
(238,'truongtv','2021-12-27','\0','truongtv','2021-12-27','2021-12-27','','1',3),
(239,'truongtv','2021-12-27','\0','truongtv','2021-12-27','2021-12-27','','1',4),
(240,'truongtv','2021-12-27','\0','truongtv','2021-12-27','2021-12-27','','1',5);

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `unit_measure` */

insert  into `unit_measure`(`id`,`created_by`,`created_date`,`deleted`,`modified_by`,`modified_date`,`name`) values 
(1,'truongtv','2021-12-26','\0','truongtv','2021-12-26','mm'),
(2,'truongtv','2021-12-26','\0','truongtv','2021-12-26','cm'),
(3,'truongtv','2021-12-26','\0','truongtv','2021-12-26','m'),
(4,'truongtv','2021-12-26','\0','truongtv','2021-12-26','Bộ'),
(5,'truongtv','2021-12-26','\0','truongtv','2021-12-26','Chiếc'),
(6,'truongtv','2021-12-26','\0','truongtv','2021-12-26','Cái');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
