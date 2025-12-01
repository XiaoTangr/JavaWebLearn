/*
Navicat MySQL Data Transfer

Source Server         : Raven
Source Server Version : 80023
Source Host           : localhost:3306
Source Database       : car_system

Target Server Type    : MYSQL
Target Server Version : 80023
File Encoding         : 65001

Date: 2025-11-25 15:02:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for commercial_vehicles
-- ----------------------------
DROP TABLE IF EXISTS `commercial_vehicles`;
CREATE TABLE `commercial_vehicles` (
  `vehicle_id` bigint NOT NULL,
  `load_capacity` decimal(10,2) NOT NULL,
  `cargo_volume` decimal(10,2) NOT NULL,
  PRIMARY KEY (`vehicle_id`) USING BTREE,
  CONSTRAINT `commercial_vehicles_ibfk_1` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicles` (`vehicle_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of commercial_vehicles
-- ----------------------------
INSERT INTO `commercial_vehicles` VALUES ('26', '5.00', '12.50');
INSERT INTO `commercial_vehicles` VALUES ('27', '4.50', '11.80');
INSERT INTO `commercial_vehicles` VALUES ('28', '6.00', '13.20');
INSERT INTO `commercial_vehicles` VALUES ('29', '5.50', '12.80');
INSERT INTO `commercial_vehicles` VALUES ('30', '4.00', '10.50');
INSERT INTO `commercial_vehicles` VALUES ('31', '7.00', '14.50');
INSERT INTO `commercial_vehicles` VALUES ('32', '5.20', '12.10');
INSERT INTO `commercial_vehicles` VALUES ('33', '5.80', '13.00');
INSERT INTO `commercial_vehicles` VALUES ('34', '3.50', '9.80');
INSERT INTO `commercial_vehicles` VALUES ('35', '3.80', '10.20');
INSERT INTO `commercial_vehicles` VALUES ('36', '2.50', '8.50');
INSERT INTO `commercial_vehicles` VALUES ('37', '3.00', '9.20');
INSERT INTO `commercial_vehicles` VALUES ('38', '2.80', '8.80');
INSERT INTO `commercial_vehicles` VALUES ('39', '2.20', '7.50');
INSERT INTO `commercial_vehicles` VALUES ('40', '2.00', '7.20');
INSERT INTO `commercial_vehicles` VALUES ('41', '3.20', '9.50');
INSERT INTO `commercial_vehicles` VALUES ('42', '4.80', '11.50');
INSERT INTO `commercial_vehicles` VALUES ('43', '5.30', '12.30');
INSERT INTO `commercial_vehicles` VALUES ('44', '4.20', '10.80');
INSERT INTO `commercial_vehicles` VALUES ('45', '10.00', '25.50');
INSERT INTO `commercial_vehicles` VALUES ('46', '15.00', '38.20');
INSERT INTO `commercial_vehicles` VALUES ('47', '12.00', '32.80');
INSERT INTO `commercial_vehicles` VALUES ('48', '18.00', '45.50');
INSERT INTO `commercial_vehicles` VALUES ('49', '20.00', '50.20');
INSERT INTO `commercial_vehicles` VALUES ('50', '16.00', '42.80');

-- ----------------------------
-- Table structure for customer_service
-- ----------------------------
DROP TABLE IF EXISTS `customer_service`;
CREATE TABLE `customer_service` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `type` enum('consultation','complaint','feedback') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `subject` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` enum('open','processing','resolved','closed') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'open',
  `priority` enum('low','medium','high') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'medium',
  `create_time` bigint NOT NULL,
  `update_time` bigint DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE,
  CONSTRAINT `customer_service_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of customer_service
-- ----------------------------
INSERT INTO `customer_service` VALUES ('1', '5', 'consultation', '1', '1', 'processing', 'low', '1763189246791', '1763189366702');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `order_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `vehicle_id` bigint NOT NULL,
  `buy_count` int NOT NULL,
  `total_price` decimal(10,2) DEFAULT NULL,
  `create_time` bigint DEFAULT NULL,
  PRIMARY KEY (`order_id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `vehicle_id` (`vehicle_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('27', '1', '1', '1', '129800.00', '1718000000000');
INSERT INTO `orders` VALUES ('28', '1', '26', '1', '115800.00', '1718100000000');
INSERT INTO `orders` VALUES ('29', '2', '5', '2', '283600.00', '1718200000000');
INSERT INTO `orders` VALUES ('30', '2', '30', '1', '98800.00', '1718300000000');
INSERT INTO `orders` VALUES ('31', '3', '9', '1', '313900.00', '1718400000000');
INSERT INTO `orders` VALUES ('32', '3', '35', '1', '89800.00', '1718500000000');
INSERT INTO `orders` VALUES ('33', '4', '12', '1', '231900.00', '1718600000000');
INSERT INTO `orders` VALUES ('34', '4', '40', '2', '103800.00', '1718700000000');
INSERT INTO `orders` VALUES ('35', '5', '15', '1', '339800.00', '1718800000000');
INSERT INTO `orders` VALUES ('36', '5', '45', '1', '235800.00', '1718900000000');
INSERT INTO `orders` VALUES ('37', '6', '3', '1', '112900.00', '1719000000000');
INSERT INTO `orders` VALUES ('38', '6', '27', '1', '108900.00', '1719100000000');
INSERT INTO `orders` VALUES ('39', '7', '7', '2', '169800.00', '1719200000000');
INSERT INTO `orders` VALUES ('40', '7', '32', '1', '128900.00', '1719300000000');
INSERT INTO `orders` VALUES ('41', '8', '10', '1', '325200.00', '1719400000000');
INSERT INTO `orders` VALUES ('42', '8', '37', '2', '139800.00', '1719500000000');
INSERT INTO `orders` VALUES ('43', '9', '18', '1', '108800.00', '1719600000000');
INSERT INTO `orders` VALUES ('44', '9', '42', '1', '92900.00', '1719700000000');
INSERT INTO `orders` VALUES ('45', '10', '22', '3', '263700.00', '1719800000000');
INSERT INTO `orders` VALUES ('46', '10', '47', '1', '356800.00', '1719900000000');
INSERT INTO `orders` VALUES ('47', '11', '4', '1', '119900.00', '1720000000000');
INSERT INTO `orders` VALUES ('48', '11', '29', '1', '125900.00', '1720100000000');
INSERT INTO `orders` VALUES ('49', '12', '8', '1', '115900.00', '1720200000000');
INSERT INTO `orders` VALUES ('50', '12', '34', '1', '138900.00', '1720300000000');
INSERT INTO `orders` VALUES ('51', '13', '13', '1', '289800.00', '1720400000000');
INSERT INTO `orders` VALUES ('52', '13', '39', '1', '52900.00', '1720500000000');
INSERT INTO `orders` VALUES ('53', '14', '17', '1', '109900.00', '1720600000000');
INSERT INTO `orders` VALUES ('54', '14', '44', '1', '105900.00', '1720700000000');
INSERT INTO `orders` VALUES ('55', '15', '21', '1', '87900.00', '1720800000000');
INSERT INTO `orders` VALUES ('56', '15', '49', '1', '368800.00', '1720900000000');
INSERT INTO `orders` VALUES ('57', '16', '25', '2', '65600.00', '1721000000000');
INSERT INTO `orders` VALUES ('58', '16', '31', '1', '142900.00', '1721100000000');
INSERT INTO `orders` VALUES ('59', '17', '2', '1', '145900.00', '1721200000000');
INSERT INTO `orders` VALUES ('60', '17', '36', '1', '89800.00', '1721300000000');
INSERT INTO `orders` VALUES ('61', '18', '6', '3', '209700.00', '1721400000000');
INSERT INTO `orders` VALUES ('62', '18', '41', '1', '65800.00', '1721500000000');
INSERT INTO `orders` VALUES ('63', '19', '11', '1', '321800.00', '1721600000000');
INSERT INTO `orders` VALUES ('64', '19', '46', '1', '389800.00', '1721700000000');
INSERT INTO `orders` VALUES ('65', '20', '14', '1', '219900.00', '1721800000000');
INSERT INTO `orders` VALUES ('66', '20', '50', '1', '329800.00', '1721900000000');
INSERT INTO `orders` VALUES ('67', '21', '16', '1', '115900.00', '1722000000000');
INSERT INTO `orders` VALUES ('68', '21', '28', '1', '132800.00', '1722100000000');
INSERT INTO `orders` VALUES ('69', '22', '19', '2', '199600.00', '1722200000000');
INSERT INTO `orders` VALUES ('70', '22', '33', '1', '145800.00', '1722300000000');
INSERT INTO `orders` VALUES ('71', '23', '20', '1', '109800.00', '1722400000000');
INSERT INTO `orders` VALUES ('72', '23', '38', '1', '59800.00', '1722500000000');
INSERT INTO `orders` VALUES ('73', '24', '23', '2', '139800.00', '1722600000000');
INSERT INTO `orders` VALUES ('74', '24', '43', '1', '112800.00', '1722700000000');
INSERT INTO `orders` VALUES ('75', '25', '24', '1', '75800.00', '1722800000000');
INSERT INTO `orders` VALUES ('76', '25', '48', '1', '342800.00', '1722900000000');
INSERT INTO `orders` VALUES ('77', '1', '1', '1', '129800.00', '1718000000000');
INSERT INTO `orders` VALUES ('78', '1', '26', '1', '115800.00', '1718100000000');
INSERT INTO `orders` VALUES ('79', '2', '5', '2', '283600.00', '1718200000000');
INSERT INTO `orders` VALUES ('80', '2', '30', '1', '98800.00', '1718300000000');
INSERT INTO `orders` VALUES ('81', '3', '9', '1', '313900.00', '1718400000000');
INSERT INTO `orders` VALUES ('82', '3', '35', '1', '89800.00', '1718500000000');
INSERT INTO `orders` VALUES ('83', '4', '12', '1', '231900.00', '1718600000000');
INSERT INTO `orders` VALUES ('84', '4', '40', '2', '103800.00', '1718700000000');
INSERT INTO `orders` VALUES ('85', '5', '15', '1', '339800.00', '1718800000000');
INSERT INTO `orders` VALUES ('86', '5', '45', '1', '235800.00', '1718900000000');
INSERT INTO `orders` VALUES ('87', '6', '3', '1', '112900.00', '1719000000000');
INSERT INTO `orders` VALUES ('88', '6', '27', '1', '108900.00', '1719100000000');
INSERT INTO `orders` VALUES ('89', '7', '7', '2', '169800.00', '1719200000000');
INSERT INTO `orders` VALUES ('90', '7', '32', '1', '128900.00', '1719300000000');
INSERT INTO `orders` VALUES ('91', '8', '10', '1', '325200.00', '1719400000000');
INSERT INTO `orders` VALUES ('92', '8', '37', '2', '139800.00', '1719500000000');
INSERT INTO `orders` VALUES ('93', '9', '18', '1', '108800.00', '1719600000000');
INSERT INTO `orders` VALUES ('94', '9', '42', '1', '92900.00', '1719700000000');
INSERT INTO `orders` VALUES ('95', '10', '22', '3', '263700.00', '1719800000000');
INSERT INTO `orders` VALUES ('96', '10', '47', '1', '356800.00', '1719900000000');
INSERT INTO `orders` VALUES ('97', '11', '4', '1', '119900.00', '1720000000000');
INSERT INTO `orders` VALUES ('98', '11', '29', '1', '125900.00', '1720100000000');
INSERT INTO `orders` VALUES ('99', '12', '8', '1', '115900.00', '1720200000000');
INSERT INTO `orders` VALUES ('100', '12', '34', '1', '138900.00', '1720300000000');
INSERT INTO `orders` VALUES ('101', '13', '13', '1', '289800.00', '1720400000000');
INSERT INTO `orders` VALUES ('102', '13', '39', '1', '52900.00', '1720500000000');
INSERT INTO `orders` VALUES ('103', '14', '17', '1', '109900.00', '1720600000000');
INSERT INTO `orders` VALUES ('104', '14', '44', '1', '105900.00', '1720700000000');
INSERT INTO `orders` VALUES ('105', '15', '21', '1', '87900.00', '1720800000000');
INSERT INTO `orders` VALUES ('106', '15', '49', '1', '368800.00', '1720900000000');
INSERT INTO `orders` VALUES ('107', '16', '25', '2', '65600.00', '1721000000000');
INSERT INTO `orders` VALUES ('108', '16', '31', '1', '142900.00', '1721100000000');
INSERT INTO `orders` VALUES ('109', '17', '2', '1', '145900.00', '1721200000000');
INSERT INTO `orders` VALUES ('110', '17', '36', '1', '89800.00', '1721300000000');
INSERT INTO `orders` VALUES ('111', '18', '6', '3', '209700.00', '1721400000000');
INSERT INTO `orders` VALUES ('112', '18', '41', '1', '65800.00', '1721500000000');
INSERT INTO `orders` VALUES ('113', '19', '11', '1', '321800.00', '1721600000000');
INSERT INTO `orders` VALUES ('114', '19', '46', '1', '389800.00', '1721700000000');
INSERT INTO `orders` VALUES ('115', '20', '14', '1', '219900.00', '1721800000000');
INSERT INTO `orders` VALUES ('116', '20', '50', '1', '329800.00', '1721900000000');
INSERT INTO `orders` VALUES ('117', '21', '16', '1', '115900.00', '1722000000000');
INSERT INTO `orders` VALUES ('118', '21', '28', '1', '132800.00', '1722100000000');
INSERT INTO `orders` VALUES ('119', '5', '50', '2', '659600.00', '1763005530710');
INSERT INTO `orders` VALUES ('120', '5', '50', '1', '329800.00', '1763005736409');
INSERT INTO `orders` VALUES ('121', '5', '49', '1', '368800.00', '1763192564670');

-- ----------------------------
-- Table structure for passenger_vehicles
-- ----------------------------
DROP TABLE IF EXISTS `passenger_vehicles`;
CREATE TABLE `passenger_vehicles` (
  `vehicle_id` bigint NOT NULL,
  `seat_count` int NOT NULL,
  `fuel_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`vehicle_id`) USING BTREE,
  CONSTRAINT `passenger_vehicles_ibfk_1` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicles` (`vehicle_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of passenger_vehicles
-- ----------------------------
INSERT INTO `passenger_vehicles` VALUES ('1', '5', '汽油');
INSERT INTO `passenger_vehicles` VALUES ('2', '5', '汽油');
INSERT INTO `passenger_vehicles` VALUES ('3', '5', '汽油');
INSERT INTO `passenger_vehicles` VALUES ('4', '5', '汽油');
INSERT INTO `passenger_vehicles` VALUES ('5', '5', '新能源');
INSERT INTO `passenger_vehicles` VALUES ('6', '5', '汽油');
INSERT INTO `passenger_vehicles` VALUES ('7', '5', '汽油');
INSERT INTO `passenger_vehicles` VALUES ('8', '5', '汽油');
INSERT INTO `passenger_vehicles` VALUES ('9', '5', '汽油');
INSERT INTO `passenger_vehicles` VALUES ('10', '5', '汽油');
INSERT INTO `passenger_vehicles` VALUES ('11', '5', '汽油');
INSERT INTO `passenger_vehicles` VALUES ('12', '5', '新能源');
INSERT INTO `passenger_vehicles` VALUES ('13', '5', '新能源');
INSERT INTO `passenger_vehicles` VALUES ('14', '5', '新能源');
INSERT INTO `passenger_vehicles` VALUES ('15', '6', '新能源');
INSERT INTO `passenger_vehicles` VALUES ('16', '5', '汽油');
INSERT INTO `passenger_vehicles` VALUES ('17', '5', '汽油');
INSERT INTO `passenger_vehicles` VALUES ('18', '5', '汽油');
INSERT INTO `passenger_vehicles` VALUES ('19', '5', '汽油');
INSERT INTO `passenger_vehicles` VALUES ('20', '5', '汽油');
INSERT INTO `passenger_vehicles` VALUES ('21', '5', '汽油');
INSERT INTO `passenger_vehicles` VALUES ('22', '5', '汽油');
INSERT INTO `passenger_vehicles` VALUES ('23', '5', '汽油');
INSERT INTO `passenger_vehicles` VALUES ('24', '5', '汽油');
INSERT INTO `passenger_vehicles` VALUES ('25', '4', '新能源');

-- ----------------------------
-- Table structure for service_ratings
-- ----------------------------
DROP TABLE IF EXISTS `service_ratings`;
CREATE TABLE `service_ratings` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `service_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `rating` int NOT NULL,
  `comment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `create_time` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `service_id` (`service_id`) USING BTREE,
  CONSTRAINT `service_ratings_ibfk_1` FOREIGN KEY (`service_id`) REFERENCES `customer_service` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `service_ratings_chk_1` CHECK ((`rating` between 1 and 5))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of service_ratings
-- ----------------------------
INSERT INTO `service_ratings` VALUES ('1', '1', '5', '1', '差评', '1763189310760');

-- ----------------------------
-- Table structure for shopping_cart
-- ----------------------------
DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE `shopping_cart` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `vehicle_id` bigint NOT NULL,
  `quantity` int NOT NULL DEFAULT '1',
  `create_time` bigint NOT NULL,
  `update_time` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `vehicle_id` (`vehicle_id`) USING BTREE,
  CONSTRAINT `shopping_cart_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `shopping_cart_ibfk_2` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicles` (`vehicle_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of shopping_cart
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `user_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_name` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE KEY `user_email` (`user_email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'test@test.com', '测试', 'test', '1');
INSERT INTO `users` VALUES ('2', 'admin@javat.cn', 'admin', 'admin', '1');
INSERT INTO `users` VALUES ('3', '2@2', '22222', '111', '1');
INSERT INTO `users` VALUES ('4', '1', '1', '222222', '1');
INSERT INTO `users` VALUES ('5', 'test', 'test', 'test', '1');
INSERT INTO `users` VALUES ('6', 'zhangwei@example.com', '张伟', 'password123', '1');
INSERT INTO `users` VALUES ('7', 'liuna@example.com', '李娜', 'mypassword', '1');
INSERT INTO `users` VALUES ('8', 'wangqiang@example.com', '王强', 'securepass', '1');
INSERT INTO `users` VALUES ('9', 'chenjing@example.com', '陈静', 'userpass456', '1');
INSERT INTO `users` VALUES ('10', 'liuming@example.com', '刘明', 'login1234', '1');
INSERT INTO `users` VALUES ('11', 'yangfang@example.com', '杨芳', 'pass7890', '1');
INSERT INTO `users` VALUES ('12', 'zhaojun@example.com', '赵军', 'mysecret1', '1');
INSERT INTO `users` VALUES ('13', 'huangli@example.com', '黄丽', 'password2', '1');
INSERT INTO `users` VALUES ('14', 'zhoupeng@example.com', '周鹏', 'user12345', '1');
INSERT INTO `users` VALUES ('15', 'wuqian@example.com', '吴倩', 'secure567', '1');
INSERT INTO `users` VALUES ('16', 'xugang@example.com', '徐刚', 'loginpass', '1');
INSERT INTO `users` VALUES ('17', 'sunyan@example.com', '孙艳', 'mypwd789', '1');
INSERT INTO `users` VALUES ('18', 'maofei@example.com', '毛飞', 'passkey12', '1');
INSERT INTO `users` VALUES ('19', 'linlin@example.com', '林琳', 'secretpw3', '1');
INSERT INTO `users` VALUES ('20', 'hecheng@example.com', '何成', 'userkey45', '1');
INSERT INTO `users` VALUES ('21', 'gaojie@example.com', '高洁', 'loginkey6', '1');
INSERT INTO `users` VALUES ('22', 'luoxin@example.com', '罗欣', 'mypassword7', '1');
INSERT INTO `users` VALUES ('23', 'xiejun@example.com', '谢军', 'securekey8', '1');
INSERT INTO `users` VALUES ('24', 'songli@example.com', '宋丽', 'passkey99', '1');
INSERT INTO `users` VALUES ('25', 'denghui@example.com', '邓辉', 'userpass00', '1');

-- ----------------------------
-- Table structure for vehicles
-- ----------------------------
DROP TABLE IF EXISTS `vehicles`;
CREATE TABLE `vehicles` (
  `vehicle_id` bigint NOT NULL AUTO_INCREMENT,
  `vehicle_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `vehicle_brand` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `vehicle_model` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `vehicle_price` decimal(10,2) NOT NULL,
  `vehicle_stock` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`vehicle_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of vehicles
-- ----------------------------
INSERT INTO `vehicles` VALUES ('1', 'passenger', '丰田', '卡罗拉', '129800.00', '30');
INSERT INTO `vehicles` VALUES ('2', 'passenger', '本田', '思域', '145900.00', '25');
INSERT INTO `vehicles` VALUES ('3', 'passenger', '大众', '朗逸', '112900.00', '40');
INSERT INTO `vehicles` VALUES ('4', 'passenger', '别克', '英朗', '119900.00', '28');
INSERT INTO `vehicles` VALUES ('5', 'passenger', '比亚迪', '秦PLUS', '141800.00', '35');
INSERT INTO `vehicles` VALUES ('6', 'passenger', '吉利', '帝豪', '69900.00', '50');
INSERT INTO `vehicles` VALUES ('7', 'passenger', '长安', '逸动', '84900.00', '32');
INSERT INTO `vehicles` VALUES ('8', 'passenger', '哈弗', 'H6', '115900.00', '22');
INSERT INTO `vehicles` VALUES ('9', 'passenger', '宝马', '3系', '313900.00', '15');
INSERT INTO `vehicles` VALUES ('10', 'passenger', '奔驰', 'C级', '325200.00', '12');
INSERT INTO `vehicles` VALUES ('11', 'passenger', '奥迪', 'A4L', '321800.00', '18');
INSERT INTO `vehicles` VALUES ('12', 'passenger', '特斯拉', 'Model 3', '231900.00', '20');
INSERT INTO `vehicles` VALUES ('13', 'passenger', '蔚来', 'ET5', '289800.00', '10');
INSERT INTO `vehicles` VALUES ('14', 'passenger', '小鹏', 'P7', '219900.00', '16');
INSERT INTO `vehicles` VALUES ('15', 'passenger', '理想', 'L7', '339800.00', '8');
INSERT INTO `vehicles` VALUES ('16', 'passenger', '马自达', '昂克赛拉', '115900.00', '24');
INSERT INTO `vehicles` VALUES ('17', 'passenger', '雪佛兰', '科鲁兹', '109900.00', '26');
INSERT INTO `vehicles` VALUES ('18', 'passenger', '福特', '福克斯', '108800.00', '23');
INSERT INTO `vehicles` VALUES ('19', 'passenger', '现代', '伊兰特', '99800.00', '36');
INSERT INTO `vehicles` VALUES ('20', 'passenger', '起亚', 'K3', '109800.00', '31');
INSERT INTO `vehicles` VALUES ('21', 'passenger', '荣威', 'i5', '87900.00', '42');
INSERT INTO `vehicles` VALUES ('22', 'passenger', '名爵', 'MG5', '87900.00', '38');
INSERT INTO `vehicles` VALUES ('23', 'passenger', '奇瑞', '艾瑞泽5', '69900.00', '45');
INSERT INTO `vehicles` VALUES ('24', 'passenger', '传祺', 'GA4', '75800.00', '33');
INSERT INTO `vehicles` VALUES ('25', 'passenger', '五菱', '宏光MINI', '32800.00', '60');
INSERT INTO `vehicles` VALUES ('26', 'commercial', '福田', '奥铃CTS', '115800.00', '18');
INSERT INTO `vehicles` VALUES ('27', 'commercial', '东风', '多利卡', '108900.00', '20');
INSERT INTO `vehicles` VALUES ('28', 'commercial', '解放', 'J6F', '132800.00', '15');
INSERT INTO `vehicles` VALUES ('29', 'commercial', '江淮', '帅铃', '125900.00', '16');
INSERT INTO `vehicles` VALUES ('30', 'commercial', '江铃', '顺达', '98800.00', '22');
INSERT INTO `vehicles` VALUES ('31', 'commercial', '庆铃', '五十铃', '142900.00', '10');
INSERT INTO `vehicles` VALUES ('32', 'commercial', '重汽', 'HOWO轻卡', '119900.00', '17');
INSERT INTO `vehicles` VALUES ('33', 'commercial', '陕汽', '德龙K3000', '128900.00', '14');
INSERT INTO `vehicles` VALUES ('34', 'commercial', '上汽大通', 'V80', '145800.00', '12');
INSERT INTO `vehicles` VALUES ('35', 'commercial', '依维柯', '得意', '138900.00', '9');
INSERT INTO `vehicles` VALUES ('36', 'commercial', '金杯', '海狮', '89800.00', '25');
INSERT INTO `vehicles` VALUES ('37', 'commercial', '长安', '跨越王', '69900.00', '30');
INSERT INTO `vehicles` VALUES ('38', 'commercial', '五菱', '荣光新卡', '59800.00', '35');
INSERT INTO `vehicles` VALUES ('39', 'commercial', '北汽昌河', '福瑞达K22', '52900.00', '28');
INSERT INTO `vehicles` VALUES ('40', 'commercial', '东风小康', 'C32', '51900.00', '32');
INSERT INTO `vehicles` VALUES ('41', 'commercial', '福田', '祥菱V', '65800.00', '24');
INSERT INTO `vehicles` VALUES ('42', 'commercial', '江淮', '康铃', '92900.00', '19');
INSERT INTO `vehicles` VALUES ('43', 'commercial', '江铃', '凯运', '112800.00', '13');
INSERT INTO `vehicles` VALUES ('44', 'commercial', '解放', '虎VN', '105900.00', '21');
INSERT INTO `vehicles` VALUES ('45', 'commercial', '东风', '天锦', '235800.00', '8');
INSERT INTO `vehicles` VALUES ('46', 'commercial', '重汽', '汕德卡', '389800.00', '5');
INSERT INTO `vehicles` VALUES ('47', 'commercial', '陕汽', '德龙X3000', '356800.00', '6');
INSERT INTO `vehicles` VALUES ('48', 'commercial', '红岩', '杰狮', '342800.00', '4');
INSERT INTO `vehicles` VALUES ('49', 'commercial', '北奔', 'V3ET', '368800.00', '2');
INSERT INTO `vehicles` VALUES ('50', 'commercial', '联合卡车', 'U+', '329800.00', '4');

-- ----------------------------
-- Table structure for coupons
-- ----------------------------
DROP TABLE IF EXISTS `coupons`;
CREATE TABLE `coupons` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '优惠券代码',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '优惠券名称',
  `discount` decimal(10,2) NOT NULL COMMENT '折扣金额或比例',
  `min_amount` decimal(10,2) DEFAULT NULL COMMENT '最低消费金额',
  `start_time` bigint DEFAULT NULL COMMENT '开始时间',
  `end_time` bigint DEFAULT NULL COMMENT '结束时间',
  `is_active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否激活',
  `total_count` int DEFAULT NULL COMMENT '总数量',
  `used_count` int DEFAULT '0' COMMENT '已使用数量',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for promotions
-- ----------------------------
DROP TABLE IF EXISTS `promotions`;
CREATE TABLE `promotions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '活动名称',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '活动描述',
  `type` int NOT NULL COMMENT '活动类型：1-限时折扣，2-满减活动',
  `discount` decimal(10,2) DEFAULT NULL COMMENT '折扣率(如0.9表示9折)或满减金额',
  `min_purchase` decimal(10,2) DEFAULT NULL COMMENT '最低购买金额（用于满减活动）',
  `start_time` bigint DEFAULT NULL COMMENT '开始时间',
  `end_time` bigint DEFAULT NULL COMMENT '结束时间',
  `is_active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否激活',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for promotion_statistics
-- ----------------------------
DROP TABLE IF EXISTS `promotion_statistics`;
CREATE TABLE `promotion_statistics` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `promotion_id` bigint NOT NULL COMMENT '关联的促销活动ID',
  `coupon_id` bigint DEFAULT NULL COMMENT '关联的优惠券ID（可为空）',
  `participation_count` int DEFAULT '0' COMMENT '参与人数',
  `order_count` int DEFAULT '0'  COMMENT '订单数量',
  `total_discount` decimal(10,2) DEFAULT '0.00' COMMENT '总折扣金额',
  `total_sales` decimal(10,2) DEFAULT '0.00' COMMENT '活动期间总销售额',
  `statistics_date` bigint DEFAULT NULL COMMENT '统计日期',
  PRIMARY KEY (`id`),
  KEY `promotion_id` (`promotion_id`),
  KEY `coupon_id` (`coupon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
