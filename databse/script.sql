

CREATE DATABASE `acfcapstoneproject` ;

USE `acfcapstoneproject`;

/*Table structure for table `position` */

CREATE TABLE `position` (
  `id` BIGINT NOT NULL,
  `created_by` VARCHAR(255) DEFAULT NULL,
  `created_date` DATE DEFAULT NULL,
  `deleted` BIT(1) DEFAULT NULL,
  `modified_by` VARCHAR(255) DEFAULT NULL,
  `modified_date` DATE DEFAULT NULL,
  `code` VARCHAR(255) DEFAULT NULL,
  `name` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `position` */
/*Table structure for table `employee` */



CREATE TABLE `employee` (
  `id` BIGINT NOT NULL,
  `created_by` VARCHAR(255) DEFAULT NULL,
  `created_date` DATE DEFAULT NULL,
  `modified_by` VARCHAR(255) DEFAULT NULL,
  `modified_date` DATE DEFAULT NULL,
  `countryside` VARCHAR(255) DEFAULT NULL,
  `dob` DATE DEFAULT NULL,
  `full_name` VARCHAR(255) DEFAULT NULL,
  `gender` BIT(1) DEFAULT NULL,
  `job` VARCHAR(255) DEFAULT NULL,
  `nation` VARCHAR(255) DEFAULT NULL,
  `phone` VARCHAR(255) DEFAULT NULL,
  `salary` BIGINT DEFAULT NULL,
  `image` VARCHAR(255) DEFAULT NULL,
  `deleted` BIT(1) DEFAULT NULL,
  `position_id` BIGINT DEFAULT NULL,
  `email` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbc8rdko9o9n1ri9bpdyxv3x7i` (`position_id`),
  CONSTRAINT `FKbc8rdko9o9n1ri9bpdyxv3x7i` FOREIGN KEY (`position_id`) REFERENCES `position` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `employee` */

INSERT  INTO `employee`(`id`,`created_by`,`created_date`,`modified_by`,`modified_date`,`countryside`,`dob`,`full_name`,`gender`,`job`,`nation`,`phone`,`salary`,`image`,`deleted`,`position_id`,`email`) VALUES 
(6,'truongtv','2021-10-02','truongtv','2021-10-02','Nam Định','1999-01-13','Trần Vũ Trường','','Giám Đốc','Kinh','0382945665',15000000,NULL,'\0',NULL,'truongtvhe130302@fpt.edu.vn'),
(7,'truongtv','2021-10-02','truongtv','2021-10-02','Hà Nội','1999-07-14','Lê Thị Ngọc','\0','Quản Lý','Kinh','0961352582',10000000,NULL,'\0',NULL,'ngoclthe131028@fpt.edu.vn'),
(8,'truongtv','2021-10-04','truongtv','2021-10-04','Hà Nội','1999-01-01','Lê Hoàng Anh','','Nhân Viên','Kinh','0123456789',10000000,NULL,'\0',NULL,'anhlhhe130300@fpt.edu.vn'),
(9,'truongtv','2021-10-04','truongtv','2021-10-04','Thái Bình','1999-01-01','Trần Bình Minh','','Nhân Viên','Kinh','0123456789',10000000,NULL,'\0',NULL,'minhtbhe130308@fpt.edu.vn'),
(10,'truongtv','2021-10-04','truongtv','2021-10-04','Bắc Ninh','1999-01-01','Nguyễn Hữu Lâm Phong','','Nhân Viên','Kinh','0123456789',10000000,NULL,'\0',NULL,'phongnhlhe130650@fpt.edu.vn'),
(17,'truongtv','2021-10-04','truongtv','2021-10-04','Bắc Ninh','1999-01-01','Tran Van Truong','','Nhân Viên','Kinh','0123456789',10000000,NULL,'\0',NULL,'phongnhlhe130650@fpt.edu.vn'),
(18,'truongtv','2021-10-04','truongtv','2021-10-04','Bắc Ninh','1999-01-01','Tran Van Trung','','Nhân Viên','Kinh','0123456789',10000000,NULL,'\0',NULL,'phongnhlhe130650@fpt.edu.vn'),
(19,'truongtv','2021-10-04','truongtv','2021-10-04','Bắc Ninh','1999-01-01','Tran Binh Manh','','Nhân Viên','Kinh','0123456789',10000000,NULL,'\0',NULL,'phongnhlhe130650@fpt.edu.vn'),
(20,'truongtv','2021-10-04','truongtv','2021-10-04','Bắc Ninh','1999-01-01','Luu Hoang Anh','','Nhân Viên','Kinh','0123456789',10000000,NULL,'\0',NULL,'phongnhlhe130650@fpt.edu.vn'),
(21,'truongtv','2021-10-04','truongtv','2021-10-04','Bắc Ninh','1999-01-01','Tran Vu Trong','','Nhân Viên','Kinh','0123456789',10000000,NULL,'\0',NULL,'phongnhlhe130650@fpt.edu.vn'),
(22,'truongtv','2021-10-04','truongtv','2021-10-04','Bắc Ninh','1999-01-01','Lai Hoang A','','Nhân Viên','Kinh','0123456789',10000000,NULL,'\0',NULL,'phongnhlhe130650@fpt.edu.vn');


/*Table structure for table `account` */

CREATE TABLE `account` (
  `id` BIGINT NOT NULL,
  `created_by` VARCHAR(255) DEFAULT NULL,
  `created_date` DATE DEFAULT NULL,
  `modified_by` VARCHAR(255) DEFAULT NULL,
  `modified_date` DATE DEFAULT NULL,
  `password` VARCHAR(255) DEFAULT NULL,
  `username` VARCHAR(255) DEFAULT NULL,
  `employee_id` BIGINT DEFAULT NULL,
  `deleted` BIT(1) DEFAULT NULL,
  `status` BIT(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1kec5bwba2rl0j8garlarwe3d` (`employee_id`),
  CONSTRAINT `FK1kec5bwba2rl0j8garlarwe3d` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `account` */

INSERT  INTO `account`(`id`,`created_by`,`created_date`,`modified_by`,`modified_date`,`password`,`username`,`employee_id`,`deleted`,`status`) VALUES 
(1,'truongtv','2021-10-02','truongtv','2021-10-02','$2a$10$3KCn7pg6MWSMrWoN30qj1uENjGhHDLYQK1tmjDs1mEg2cV3VtTa.e','truongtv',6,'\0',''),
(4,'truongtv','2021-10-02','truongtv','2021-10-02','$2a$10$3KCn7pg6MWSMrWoN30qj1uENjGhHDLYQK1tmjDs1mEg2cV3VtTa.e','ngoclt',7,'\0',''),
(11,'truongtv','2021-10-08','truongtv','2021-10-08','$2a$10$nQ2ETrwIAjgtSuwNHjFdTuY8PCQpm5L6cIJ198qVAs25oTtOl9rs2','anhlh',8,'\0',''),
(12,'truongtv','2021-10-06','truongtv','2021-10-06','$2a$10$3KCn7pg6MWSMrWoN30qj1uENjGhHDLYQK1tmjDs1mEg2cV3VtTa.e','minhtb',9,'\0',''),
(13,'truongtv','2021-10-08','truongtv','2021-10-08','$2a$10$nQ2ETrwIAjgtSuwNHjFdTuY8PCQpm5L6cIJ198qVAs25oTtOl9rs2','phongnhl',10,'\0','');


/*Table structure for table `role` */
CREATE TABLE `role` (
  `id` BIGINT NOT NULL,
  `created_by` VARCHAR(255) DEFAULT NULL,
  `created_date` DATE DEFAULT NULL,
  `modified_by` VARCHAR(255) DEFAULT NULL,
  `modified_date` DATE DEFAULT NULL,
  `code` VARCHAR(255) DEFAULT NULL,
  `name` VARCHAR(255) DEFAULT NULL,
  `deleted` BIT(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `role` */

INSERT  INTO `role`(`id`,`created_by`,`created_date`,`modified_by`,`modified_date`,`code`,`name`,`deleted`) VALUES 
(2,'truongtv','2021-10-02','truongtv','2021-10-02','SP_ADMIN','Super Admin','\0'),
(3,'truongtv','2021-10-02','truongtv','2021-10-02','ADMIN','Admin','\0'),
(5,'truongtv','2021-10-02','truongtv','2021-10-02','EMPLOYEE','Employee','\0');

/*Table structure for table `account_roles` */



CREATE TABLE `account_roles` (
  `account_id` BIGINT NOT NULL,
  `roles_id` BIGINT NOT NULL,
  KEY `FK70s9enq5d1oywl7v8vis5ke5w` (`roles_id`),
  KEY `FKtp61eta5i06bug3w1qr6286uf` (`account_id`),
  CONSTRAINT `FK70s9enq5d1oywl7v8vis5ke5w` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKtp61eta5i06bug3w1qr6286uf` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `account_roles` */

INSERT  INTO `account_roles`(`account_id`,`roles_id`) VALUES 
(13,2),
(11,5),
(4,5),
(4,3),
(1,3),
(1,2),
(1,5);

/*Table structure for table `advace_salary` */

CREATE TABLE `advace_salary` (
  `id` BIGINT NOT NULL,
  `created_by` VARCHAR(255) DEFAULT NULL,
  `created_date` DATE DEFAULT NULL,
  `deleted` BIT(1) DEFAULT NULL,
  `modified_by` VARCHAR(255) DEFAULT NULL,
  `modified_date` DATE DEFAULT NULL,
  `accept` BIT(1) DEFAULT NULL,
  `advace_salary` VARCHAR(255) DEFAULT NULL,
  `date` DATE DEFAULT NULL,
  `employee_id` BIGINT DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKaei4ytudikyg3mer5oqew7aj6` (`employee_id`),
  CONSTRAINT `FKaei4ytudikyg3mer5oqew7aj6` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `advace_salary` */


/*Table structure for table `file` */
CREATE TABLE `file` (
  `id` VARCHAR(255) NOT NULL,
  `created_by` VARCHAR(255) DEFAULT NULL,
  `created_date` DATE DEFAULT NULL,
  `deleted` BIT(1) DEFAULT NULL,
  `modified_by` VARCHAR(255) DEFAULT NULL,
  `modified_date` DATE DEFAULT NULL,
  `name` VARCHAR(255) DEFAULT NULL,
  `type` VARCHAR(255) DEFAULT NULL,
  `url` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `file` */

INSERT  INTO `file`(`id`,`created_by`,`created_date`,`deleted`,`modified_by`,`modified_date`,`name`,`type`,`url`) VALUES 
('2036bc27-746e-4a17-85c8-5c2b96bcf2f8','truongtv','2021-10-11','\0','truongtv','2021-10-11','videoplayback.mp4','video/mp4','/D:/Capstone_Project_FPT_G13/backend/uploads/'),
('2201ef61-2e2f-4ce9-bba4-30f914def768','truongtv','2021-10-11','\0','truongtv','2021-10-11','ch03.ppt','application/vnd.ms-powerpoint','/D:/Capstone_Project_FPT_G13/backend/uploads/'),
('66262a0b-55d3-4ae3-be2d-bd8eaeed4367','truongtv','2021-10-11','\0','truongtv','2021-10-11','2.jpg','image/jpeg','/D:/Capstone_Project_FPT_G13/backend/uploads'),
('8c0f77ae-9dc4-4f27-a91d-dbb8c63291f0','truongtv','2021-10-11','\0','truongtv','2021-10-11','1.jpg','image/jpeg','/D:/Capstone_Project_FPT_G13/backend/uploads/'),
('bbe9cb4a-258b-455f-a3e0-72ad0dfd8535','truongtv','2021-10-11','\0','truongtv','2021-10-11','ch09.1-09.5.pptx','application/vnd.openxmlformats-officedocument.presentationml.presentation','/D:/Capstone_Project_FPT_G13/backend/uploads/'),
('c04ca32f-0117-4204-92ce-480e0d33ddbb','truongtv','2021-10-11','\0','truongtv','2021-10-11','1.jpg','image/jpeg','/D:/Capstone_Project_FPT_G13/backend/uploads');

/*Table structure for table `hibernate_sequence` */

CREATE TABLE `hibernate_sequence` (
  `next_val` BIGINT DEFAULT NULL
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `hibernate_sequence` */

INSERT  INTO `hibernate_sequence`(`next_val`) VALUES 
(46);

/*Table structure for table `history_salary` */

CREATE TABLE `history_salary` (
  `id` BIGINT NOT NULL,
  `created_by` VARCHAR(255) DEFAULT NULL,
  `created_date` DATE DEFAULT NULL,
  `deleted` BIT(1) DEFAULT NULL,
  `modified_by` VARCHAR(255) DEFAULT NULL,
  `modified_date` DATE DEFAULT NULL,
  `accept` BIT(1) DEFAULT NULL,
  `date` DATE DEFAULT NULL,
  `salary` VARCHAR(255) DEFAULT NULL,
  `employee_id` BIGINT DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKl90fglpjpf6ku3keoivhved8h` (`employee_id`),
  CONSTRAINT `FKl90fglpjpf6ku3keoivhved8h` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `history_salary` */

/*Table structure for table `persona_leave_application` */

CREATE TABLE `persona_leave_application` (
  `id` BIGINT NOT NULL,
  `created_by` VARCHAR(255) DEFAULT NULL,
  `created_date` DATE DEFAULT NULL,
  `deleted` BIT(1) DEFAULT NULL,
  `modified_by` VARCHAR(255) DEFAULT NULL,
  `modified_date` DATE DEFAULT NULL,
  `accept` BIT(1) DEFAULT NULL,
  `date_end` DATE DEFAULT NULL,
  `date_start` DATE DEFAULT NULL,
  `file_attach` VARCHAR(255) DEFAULT NULL,
  `reson` TEXT,
  `employee_id` BIGINT DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe960gyn4br66hktqfakttfrqd` (`employee_id`),
  CONSTRAINT `FKe960gyn4br66hktqfakttfrqd` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `persona_leave_application` */




/*Table structure for table `time_keep` */

CREATE TABLE `time_keep` (
  `id` BIGINT NOT NULL,
  `created_by` VARCHAR(255) DEFAULT NULL,
  `created_date` DATE DEFAULT NULL,
  `deleted` BIT(1) DEFAULT NULL,
  `modified_by` VARCHAR(255) DEFAULT NULL,
  `modified_date` DATE DEFAULT NULL,
  `accept` BIT(1) DEFAULT NULL,
  `date` DATE DEFAULT NULL,
  `employee_id` BIGINT DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa50filqbtluks1279bfks4r4h` (`employee_id`),
  CONSTRAINT `FKa50filqbtluks1279bfks4r4h` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `time_keep` */