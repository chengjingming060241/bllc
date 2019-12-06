/*
 Navicat Premium Data Transfer

 Source Server         : java
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : brown

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 06/12/2019 16:16:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for device_alarm
-- ----------------------------
DROP TABLE IF EXISTS `device_alarm`;
CREATE TABLE `device_alarm`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键,自增长',
  `ctime` datetime(0) NOT NULL COMMENT '添加时间',
  `utime` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '故障名称',
  `attr` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '故障参数/对应数据点表的identity_name',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '内容',
  `happen_time` datetime(0) DEFAULT NULL COMMENT '故障发生时间',
  `fixed_time` datetime(0) DEFAULT NULL COMMENT '故障修复时间',
  `status` int(1) NOT NULL COMMENT '故障状态,0:未修复 1:已修复',
  `mac` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'MAC地址',
  `longitude` decimal(19, 2) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(19, 2) DEFAULT NULL COMMENT '维度',
  `notify_user_id` int(11) DEFAULT NULL COMMENT '需要通知的人员ID',
  `sno` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '对应设备序列号',
  `alarm_type` int(1) DEFAULT 1 COMMENT '告警类型:0,报警;1,故障',
  `product_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `remark` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_sno`(`sno`) USING BTREE,
  INDEX `idx_mac`(`mac`) USING BTREE,
  INDEX `idx_ctime`(`ctime`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '设备故障(警告)记录表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
